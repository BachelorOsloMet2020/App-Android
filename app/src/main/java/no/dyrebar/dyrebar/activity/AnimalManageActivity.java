package no.dyrebar.dyrebar.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;
import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.AnimalExt;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.classes.ProfileAnimal;
import no.dyrebar.dyrebar.dialog.IndicatorDialog;
import no.dyrebar.dyrebar.handler.ImageHandler;
import no.dyrebar.dyrebar.json.jProfileAnimal;
import no.dyrebar.dyrebar.json.jStatus;
import no.dyrebar.dyrebar.steps.AnimalBasicStep;
import no.dyrebar.dyrebar.steps.AnimalDescriptionStep;
import no.dyrebar.dyrebar.steps.AnimalExtStep;
import no.dyrebar.dyrebar.steps.AnimalFurStep;
import no.dyrebar.dyrebar.steps.AnimalImageStep;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class AnimalManageActivity extends AppCompatActivity implements StepperFormListener
{
    private final String TAG = getClass().getName();
    public static final int ARC_Camera = 483;
    public static final int ARC_Gallery = 583;

    private ProfileAnimal animal;

    //https://github.com/ernestoyaquello/VerticalStepperForm

    private AnimalBasicStep animalBasicStep;
    private AnimalExtStep animalExtStep;
    private AnimalFurStep animalFurStep;
    private AnimalImageStep animalImageStep;
    private AnimalDescriptionStep animalDescriptionStep;

    private VerticalStepperFormView verticalStepperFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        animalBasicStep = new AnimalBasicStep(this, getString(R.string.create_animal_profile_step_basic));
        animalExtStep = new AnimalExtStep(this, getString(R.string.create_animal_profile_step_extended));
        animalFurStep = new AnimalFurStep(this, getString(R.string.create_animal_profile_step_appearance));
        animalImageStep = new AnimalImageStep(this, getString(R.string.create_animal_profile_step_image));
        animalDescriptionStep = new AnimalDescriptionStep(this, getString(R.string.create_animal_profile_step_desc));



        verticalStepperFormView = findViewById(R.id.stepper_form);
        verticalStepperFormView.setup(this, animalBasicStep, animalExtStep, animalFurStep, animalImageStep, animalDescriptionStep)
                .stepNextButtonText(getString(R.string.continue_))
                .confirmationStepTitle(getString(R.string.complete_))
                .lastStepNextButtonText(getString(R.string.complete_))
                .displayBottomNavigation(false)
                .init();

        if (getIntent().getExtras() != null && getIntent().hasExtra("animal_profile"))
        {
            animal = (ProfileAnimal) getIntent().getExtras().getSerializable("animal_profile");
            animalBasicStep.restoreStepData(animal.getAnimalBasic());
            animalExtStep.restoreStepData(animal.getAnimalExt());
            animalFurStep.restoreStepData(animal.getAnimalFur());
            animalImageStep.restoreStepData(animal.getImage());
            animalDescriptionStep.restoreStepData(animal.getDescription());

        }

    }

    private IndicatorDialog id;

    @Override
    public void onCompletedForm()
    {
        id = new IndicatorDialog(this, getString(R.string.please_wait), getString(R.string.upload_animal_profile));
        id.Show();
        ImageHandler imageHandler = new ImageHandler();
        if (animal == null)
            animal = new ProfileAnimal();
        animal.fromAnimalBasic(animalBasicStep.getStepData());
        animal.fromAnimalExt(animalExtStep.getStepData());
        animal.fromAnimalFur(animalFurStep.getStepData());
        animal.setDescription(animalDescriptionStep.getStepData());

        if (animalImageStep.getStepData() instanceof Uri)
        {
            Bitmap bitmap = imageHandler.getBitmap(this, (Uri) animalImageStep.getStepData());
            if (bitmap != null)
            {
                String bitbase64 = imageHandler.getImageAsString(bitmap);
                animal.setImage(bitbase64);
                animal.setImageType("base64");
            }
        }
        else if (animalImageStep.getStepData() instanceof String)
        {
            animal.setImage(animalImageStep.getStepData().toString());
            animal.setImageType("url");
        }





        AsyncTask.execute(() -> {
            String jAnimal = new jProfileAnimal().encode(App.profile.getId(), animal);
            String resp = new Api().Post(Source.Api, new ArrayList<Pair<String, ?>>() {{
                add(new Pair<>("request", "myAnimal"));
                add(new Pair<>("data", jAnimal));
                add(new Pair<>("token", App.authSession.getToken()));
            }});
            Log.d(getClass().getName(), resp);
            boolean success = new jStatus().getStatus(resp);
            runOnUiThread(() -> id.Hide());
            if (!success)
            {
                runOnUiThread(() -> {
                    verticalStepperFormView.cancelFormCompletionOrCancellationAttempt();
                });
            }
            else
                runOnUiThread(this::finish);

        });




    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (id != null)
            id.Destroy();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (id != null)
            id.Destroy();
    }

    @Override
    public void onCancelledForm()
    {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ARC_Camera)
            if (resultCode == RESULT_OK)
            {
                animalImageStep.onCameraCapture();
            }
            else
                Log.e(TAG, "Image Capture failed");
        else if (requestCode == ARC_Gallery)
            if (resultCode == RESULT_OK)
            {
                if (data != null && data.getExtras() != null)
                {
                    Log.d(getClass().getName(), data.getExtras().toString());
                }
                Log.d(getClass().getName(), data.getData().toString());
                animalImageStep.onGalleryCapture(data.getData());
            }
            else
                Log.e(TAG, "Image Gallery failed");
        else if (requestCode == UCrop.REQUEST_CROP)
        {
            if (resultCode == RESULT_OK)
                animalImageStep.onImageCrop(UCrop.getOutput(data));
        }
    }
}
