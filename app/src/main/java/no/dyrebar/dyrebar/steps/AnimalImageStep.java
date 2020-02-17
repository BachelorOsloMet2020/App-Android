package no.dyrebar.dyrebar.steps;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.util.UUID;

import ernestoyaquello.com.verticalstepperform.Step;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.activity.AnimalManageActivity;
import no.dyrebar.dyrebar.dialog.ImageSourceDialog;
import no.dyrebar.dyrebar.handler.FileHandler;
import no.dyrebar.dyrebar.interfaces.DialogImageSourceInterface;

public class AnimalImageStep extends Step<Object> implements DialogImageSourceInterface.DialogImageSourceListener
{
    private Activity activity;
    private View v;
    private String fileName;

    Object capture = null;

    public AnimalImageStep(Activity activity, String stepTitle)
    {
        super(stepTitle);
        this.activity = activity;
    }


    @Override
    protected View createStepContentLayout()
    {
        v = LayoutInflater.from(activity).inflate(R.layout.stepper_animal_image, null);
        FileHandler fh = new FileHandler();
        v.findViewById(R.id.stepper_animal_image_image).setOnClickListener(v -> {
            fileName = UUID.randomUUID().toString() + ".png";
            Uri out = fh.getUriFromFile(activity, fh.getImageFile(fh.getExternalImagesFolder(), fileName));
            capture = out;
            ImageSourceDialog isd = new ImageSourceDialog(activity, activity.getString(R.string.dialog_image_source_title), AnimalManageActivity.ARC_Camera, AnimalManageActivity.ARC_Gallery, out);
            isd.Show();
        });
        return v;
    }



    @Override
    public Object getStepData()
    {
        return capture;
    }

    @Override
    public String getStepDataAsHumanReadableString()
    {
        return null;
    }

    @Override
    public void restoreStepData(Object data)
    {
        if (data instanceof String && data.toString().substring(0, 4).equals("http"))
        {
            Picasso.get().load(data.toString()).into((ImageView)v.findViewById(R.id.stepper_animal_image_image));
            capture = data;
        }
        else if (data instanceof Uri)
        {
            ((ImageView)v.findViewById(R.id.stepper_animal_image_image)).setImageURI((Uri) data);
            capture = (Uri) data;
        }
        markAsCompletedOrUncompleted(true);
    }

    @Override
    protected IsDataValid isStepDataValid(Object stepData)
    {
        if (capture == null)
        {
            return new IsDataValid(false, activity.getString(R.string.create_animal_profile_error_image));
        }
        return new IsDataValid(true, "");
    }

    @Override
    protected void onStepOpened(boolean animated)
    {
        markAsCompletedOrUncompleted(true);
    }

    @Override
    protected void onStepClosed(boolean animated)
    {

    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated)
    {

    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated)
    {

    }

    private void requestCrop(Uri inUri)
    {
        FileHandler fh = new FileHandler();
        Uri outUri = Uri.fromFile(fh.getImageFile(fh.getExternalImagesFolder(), "s_"+ fileName));
        //Uri outUri = fh.getUriFromFile(activity, fh.getImageFile(fh.getExternalImagesFolder(), "s_"+ fileName));
        //https://github.com/Yalantis/uCrop
        UCrop.of(inUri, outUri)
                .withAspectRatio(16,10)
                .withMaxResultSize(1920, 1200)
                .start((AppCompatActivity) activity);
    }


    @Override
    public void onCameraCapture()
    {
        if (capture != null)
            requestCrop((Uri) capture);
    }

    @Override
    public void onGalleryCapture(Uri uri)
    {
        if (uri != null)
        {
            capture = uri;
            requestCrop((Uri) capture);
        }
    }

    @Override
    public void onImageCrop(Uri uri)
    {
        if (uri != null)
        {
            capture = uri;
            ((ImageView)v.findViewById(R.id.stepper_animal_image_image)).setImageURI((Uri) capture);
        }
        markAsCompletedOrUncompleted(true);
    }
}
