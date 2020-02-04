package no.dyrebar.dyrebar.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;
import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.handler.FileHandler;
import no.dyrebar.dyrebar.handler.SettingsHandler;
import no.dyrebar.dyrebar.json.jProfile;
import no.dyrebar.dyrebar.json.jStatus;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class ProfileManageActivity extends AppCompatActivity
{
    private Profile profile;
    private AuthSession authSession;

    private final int AR_ID_GET_IMAGE = 1001;

    private Mode currentMode = Mode.UNDEFINED;
    enum Mode
    {
        UNDEFINED,
        CREATE,
        UPDATE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);


        currentMode = Mode.valueOf(getIntent().getStringExtra("mode"));
        if (currentMode == Mode.CREATE)
        {
            findViewById(R.id.create_profile_email_text).setVisibility(View.GONE);
        }

        authSession = new AuthSession(new SettingsHandler(getApplicationContext()).getMultilineSetting(S.Dyrebar_Auth));

        if (getIntent().hasExtra("profile"))
        {
            profile = (Profile) getIntent().getExtras().getSerializable("profile");
            applyProfile(profile);
        }
        else
            profile = new Profile();

        profile.setAuthId(authSession.getAuthId());

        findViewById(R.id.completeProfile).setOnClickListener(v -> {
            if (onCheck())
            {
                String profileJson = new jProfile().encode(profile, authSession.getToken());
                AsyncTask.execute(() -> {
                    Api api = new Api();
                    String resp = api.Post(Source.Api, new ArrayList<Pair<String, ?>>(){{
                        add(new Pair<>("request", "myProfile"));
                        add(new Pair<>("data", profileJson));
                        add(new Pair<>("token", authSession.getToken()));
                    }});
                    boolean success = new jStatus().getStatus(resp);
                    if(success)
                    {
                        runOnUiThread(() -> {
                            Intent homeIntent = new Intent(this, MainActivity.class);
                            startActivity(homeIntent);
                        });
                    }
                });
            }
        });
        attachListeners();
    }

    private void attachListeners()
    {
        findViewById(R.id.create_profile_image).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, AR_ID_GET_IMAGE);
        });
    }

    private void requestCropping(Uri inUri)
    {
        FileHandler fh = new FileHandler();
        Uri outUri = Uri.fromFile(fh.getImage(getApplicationContext(), fh.getImagesFolder(getApplicationContext()), "profilePicture.png"));
        //https://github.com/Yalantis/uCrop
        UCrop.of(inUri, outUri)
                .withAspectRatio(1,1)
                .withMaxResultSize(500, 500)
                .start(this);
    }

    private void applyProfile(Profile p)
    {
        if (p.getFirstName() != null)
            ((TextInputEditText)findViewById(R.id.create_profile_firstName_text)).setText(p.getFirstName());
        if (p.getLastName() != null)
            ((TextInputEditText)findViewById(R.id.create_profile_lastName_text)).setText(p.getLastName());
        if (p.getTlf() != null)
            ((TextInputEditText)findViewById(R.id.create_profile_phone_text)).setText(p.getTlf());
        if (p.getAddress() != null)
            ((TextInputEditText)findViewById(R.id.create_profile_address_text)).setText(p.getAddress());
        if (p.getpostNumber() > 0)
            ((TextInputEditText)findViewById(R.id.create_profile_postCode_text)).setText(p.getpostNumber());

        if (p.getImage().length() > 0)
            Picasso.get().load(p.getImage()).into((ImageView) findViewById(R.id.create_profile_image));

    }

    private boolean onCheck()
    {
        boolean validInfo = true;

        String firstname =  ((TextInputEditText)findViewById(R.id.create_profile_firstName_text)).getText().toString();
        String lastname = ((TextInputEditText)findViewById(R.id.create_profile_lastName_text)).getText().toString();
        String tlf = ((TextInputEditText)findViewById(R.id.create_profile_phone_text)).getText().toString();
        String address = ((TextInputEditText)findViewById(R.id.create_profile_address_text)).getText().toString();
        String postNumber = ((TextInputEditText)findViewById(R.id.create_profile_postCode_text)).getText().toString();
        String email = ((TextInputEditText)findViewById(R.id.create_profile_email)).getText().toString();



        if (firstname.length() == 0)
        {
            ((TextInputEditText)findViewById(R.id.create_profile_firstName_text)).setError(getString(R.string.create_profile_error_firstName));
            validInfo = false;
        }
        else
            ((TextInputEditText)findViewById(R.id.create_profile_firstName_text)).setError(null);
        if (lastname.length() == 0)
        {
            ((TextInputEditText)findViewById(R.id.create_profile_lastName_text)).setError(getString(R.string.create_profile_error_lastName));
            validInfo = false;
        }
        else
            ((TextInputEditText)findViewById(R.id.create_profile_lastName_text)).setError(null);
        if (tlf.length() == 0)
        {
            ((TextInputEditText)findViewById(R.id.create_profile_phone_text)).setError(getString(R.string.create_profile_error_tlf));
            validInfo = false;
        }
        else
            ((TextInputEditText)findViewById(R.id.create_profile_phone_text)).setError(null);

        if (address.length() == 0)
        {
            ((TextInputEditText)findViewById(R.id.create_profile_address_text)).setError(getString(R.string.create_profile_error_address));
            validInfo = false;
        }
        else
            ((TextInputEditText)findViewById(R.id.create_profile_address_text)).setError(null);
        if (postNumber.length() == 0)
        {
            ((TextInputEditText)findViewById(R.id.create_profile_postCode_text)).setError(getString(R.string.create_profile_error_postCode));
            validInfo = false;
        }
        else
            ((TextInputEditText)findViewById(R.id.create_profile_postCode_text)).setError(null);
        if (currentMode == Mode.UPDATE)
        {
            if (postNumber.length() == 0)
            {
                ((TextInputEditText)findViewById(R.id.create_profile_email_text)).setError(getString(R.string.create_profile_error_email));
                validInfo = false;
            }
            else
                ((TextInputEditText)findViewById(R.id.create_profile_email_text)).setError(null);
        }

        if (validInfo)
        {
            profile.setFirstName(firstname);
            profile.setLastName(lastname);
            profile.setTlf(tlf);
            profile.setAddress(address);
            profile.setpostNumber(Integer.valueOf(postNumber));
            if (currentMode == Mode.UPDATE)
                profile.setEmail(email);
        }

        return validInfo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == AR_ID_GET_IMAGE)
        {
            // Apply image
            Uri selectedImage = data.getData();
            //((ImageView)findViewById(R.id.create_profile_image)).setImageURI(selectedImage);
            requestCropping(selectedImage);
        }
        else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP)
        {
            Uri result = UCrop.getOutput(data);
            Bitmap bitmap = getBitmap(result);
            if (bitmap != null)
            {
                profile.setImage(getImageAsString(bitmap));
                profile.setImageType("base64");
                ((ImageView)findViewById(R.id.create_profile_image)).setImageURI(result);
            }
        }
    }

    private Bitmap getBitmap(Uri uri)
    {
        Bitmap bitmap = null;
        try
        {
            InputStream is = this.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

    private String getImageAsString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encImg = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encImg;
    }
}
