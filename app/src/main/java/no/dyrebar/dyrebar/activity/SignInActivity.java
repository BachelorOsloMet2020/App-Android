package no.dyrebar.dyrebar.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;
import no.dyrebar.dyrebar.classes.SiginInChallenge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity
{
    private final String TAG = this.getClass().getName();

    private final int AR_ID_GSO = 666; /* Google sign in id for activity result*/


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        load_Google_SingIn();
        load_Facebook_SignIn();
    }

    private void load_Google_SingIn()
    {
        /* Retrieving button that represents google sign in  */
        Button login = findViewById(R.id.login_google);

        /* Creates sign in options*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(S.Google_Sign_In_Client_Id)
                .requestEmail()
                .requestProfile()
                .build();
        /* Builds the sign in client with GSO*/
        GoogleSignInClient gsic = GoogleSignIn.getClient(this, gso);

        /* Gets the logged inn account
        * returns null if none */
        GoogleSignInAccount gsia = GoogleSignIn.getLastSignedInAccount(this);
        if (gsia != null)
        {
            /* Needs to provide data for backend challenge
             * This i needed in order to verify the authenticity of the user and session*/
            runLoginChallenge(null);
        }
        else
        {
            // Enable login button
            login.setOnClickListener(v ->
            {
                Intent gsii = gsic.getSignInIntent();
                startActivityForResult(gsii, AR_ID_GSO);
            });
        }

    }

    CallbackManager facebook_CallBackManager;
    private void load_Facebook_SignIn()
    {

        facebook_CallBackManager = CallbackManager.Factory.create();
        Button login = findViewById(R.id.login_facebook);
        login.setOnClickListener(v ->
        {
            LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this, Arrays.asList("email", "user_photos", "public_profile", "user_location"));
            LoginManager.getInstance().registerCallback(facebook_CallBackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult)
                {

                    runLoginChallenge(null);
                }

                @Override
                public void onCancel()
                {

                }

                @Override
                public void onError(FacebookException error)
                {

                }
            });
        });

    }



    private void runLoginChallenge(SiginInChallenge sic)
    {

    }

    private void GSO_SignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try
        {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

        }
        catch (ApiException e)
        {
            Log.w(TAG, "handleSignInResult:error", e);
        }
    }


    /** Standard android impmentasjon for å sjekke resultatet fra aktiviteten som ble startet
     * Request_Code er koden som separerer de forskjellige foresøprselene
     * Eks Facebook sin er forskjellig fra Google sin
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AR_ID_GSO)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GSO_SignInResult(task);
        }
        else if (FacebookSdk.isFacebookRequestCode(requestCode))
            facebook_CallBackManager.onActivityResult(requestCode, resultCode, data);

    }
}
