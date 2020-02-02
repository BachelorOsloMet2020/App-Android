package no.dyrebar.dyrebar.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;
import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.classes.AuthChallenge;
import no.dyrebar.dyrebar.dialog.IndicatorDialog;
import no.dyrebar.dyrebar.handler.SettingsHandler;
import no.dyrebar.dyrebar.json.jAuthSession;
import no.dyrebar.dyrebar.json.jProfile;
import no.dyrebar.dyrebar.json.jAuthChallenge;
import no.dyrebar.dyrebar.json.jStatus;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SignInActivity extends AppCompatActivity
{
    private final String TAG = this.getClass().getName();

    private final int AR_ID_GSO = 666; /* Google sign in id for activity result*/
    private String Device_ID = "";

    private AuthSession authSession;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Device_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        SettingsHandler sh = new SettingsHandler(getApplicationContext());
        Map<String, ?> map = sh.getMultilineSetting(S.Dyrebar_Auth);
        IndicatorDialog id = new IndicatorDialog(this, getString(R.string.challenge_sign_in_title), getString(R.string.challenge_sing_in_message));
        if (map.size() > 0)
            authSession = new AuthSession(map);
        if (authSession != null && authSession.objVal())
        {
            setLoginButtonEnabled(false);
            id.Show();

            // Request Token validation
            AsyncTask.execute(() -> {
                try
                {
                    String json = new jAuthSession().encode(authSession);
                    String resp = new Api().Post(Source.Api, new ArrayList<Pair<String, ?>>(){{
                        add(new Pair<>("auth", "validate"));
                        add(new Pair<>("data", json));
                    }});
                    boolean success = new jStatus().getStatus(resp);
                    if (success && new JSONObject(resp).getBoolean("isValid"))
                    {
                        Log.d(TAG, "User is valid and has a session");
                        boolean profileExists = hasProfile(new Api(), authSession);

                        runOnUiThread(() -> {
                            id.Hide();
                            if (profileExists)
                            {
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            }
                            else
                            {
                                setLoginButtonEnabled(true);
                            }
                        });
                    }
                    else
                    {
                        Log.e(TAG, "Request failed");
                        runOnUiThread(() -> {
                            id.Hide();
                            loadSingInOptions();
                        });

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            });
        }
        if (id.isVisible())
            id.Hide();
        loadSingInOptions();

    }


    private void loadSingInOptions()
    {
        load_Google_SingIn();
        load_Facebook_SignIn();
        load_Email_SignIn();


        findViewById(R.id.login_skip).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void load_Email_SignIn()
    {
        findViewById(R.id.login_email).setOnClickListener(v -> {
            Intent email = new Intent(this, SignInEmailActivity.class);
            startActivity(email);
        });
    }

    private void setLoginButtonEnabled(boolean enabled)
    {
        findViewById(R.id.login_google).setEnabled(enabled);
        findViewById(R.id.login_facebook).setEnabled(enabled);
        findViewById(R.id.login_email).setEnabled(enabled);
        findViewById(R.id.login_skip).setEnabled(enabled);

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
        if (gsia != null && !Source.suppressGoogleAutoResignIn)
        {
            /* Needs to provide data for backend challenge
             * This i needed in order to verify the authenticity of the user and session*/
            runLoginChallenge(new AuthChallenge(
                    gsia.getId(),
                    gsia.getEmail(),
                    gsia.getIdToken(),
                    AuthChallenge.oAuthProvider.GOOGLE,
                    new Profile(
                            gsia.getGivenName(),
                            gsia.getFamilyName(),
                            gsia.getEmail(),
                            ((gsia.getPhotoUrl() != null) ? gsia.getPhotoUrl().toString() : "")
                    ),
                    Device_ID
            ));
        }

        // Enable login button
        login.setOnClickListener(v ->
        {
            Intent gsii = gsic.getSignInIntent();
            startActivityForResult(gsii, AR_ID_GSO);
        });

    }

    CallbackManager facebook_CallBackManager;

    private void load_Facebook_SignIn()
    {

        facebook_CallBackManager = CallbackManager.Factory.create();
        Button login = findViewById(R.id.login_facebook);
        login.setOnClickListener(v ->
        {
            LoginManager.getInstance().setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
            LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this, Arrays.asList("email", "user_photos", "public_profile", "user_location"));
            LoginManager.getInstance().registerCallback(facebook_CallBackManager, new FacebookCallback<LoginResult>()
            {
                @Override
                public void onSuccess(LoginResult loginResult)
                {
                    GraphRequest gr = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                            (object, response) ->
                            {
                                try
                                {
                                    Profile graphProfile = new jProfile().jGraphProfile(object);
                                    runLoginChallenge(new AuthChallenge(
                                            object.getString("id"),
                                            object.getString("email"),
                                            loginResult.getAccessToken().getToken(),
                                            AuthChallenge.oAuthProvider.FACEBOOK,
                                            graphProfile,
                                            Device_ID
                                    ));
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                                Log.d(TAG + " Facebook Grap: ", object.toString());
                            });
                    Bundle b = new Bundle();
                    b.putString("fields", "id,first_name,last_name,email,location,address");
                    gr.setParameters(b);
                    gr.executeAsync();
                    //runLoginChallenge(null);
                    Log.d(TAG + " Facebook AT-Str: ", loginResult.getAccessToken().toString());
                    Log.d(TAG + " Facebook AT-TOK: ", loginResult.getAccessToken().getToken());
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


    private void runLoginChallenge(AuthChallenge sic)
    {
        setLoginButtonEnabled(false);
        IndicatorDialog id = new IndicatorDialog(this, getString(R.string.challenge_sign_in_title), getString(R.string.challenge_sing_in_message));
        id.Show();

        Log.d(TAG, sic.toString());
        try
        {
            String jSic = new jAuthChallenge().encode(sic);
            Api api = new Api();
            AsyncTask.execute(() ->
            {
                String resp = api.Post(Source.Api, new ArrayList<Pair<String, ?>>(){{
                    add(new Pair<>("auth", sic.getProvider().toString()));
                    add(new Pair<>("data", jSic));
                }});
                boolean success = new jStatus().getStatus(resp);
                if (success && sic.getProfile().getEmail() != null && sic.getProfile().getEmail().length() > 0)
                {
                    try
                    {
                        AuthSession authSession = new jAuthSession().decode(resp);
                        new SettingsHandler(getApplicationContext()).setMultilineSetting(S.Dyrebar_Auth, authSession.asList());
                        boolean profileExists = hasProfile(api, authSession);

                        runOnUiThread(() -> {
                            id.Hide();
                            if (profileExists)
                            {
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            }
                            else
                            {
                                Intent createProfile = new Intent(this, ProfileManageActivity.class);
                                Bundle bunde = new Bundle();
                                bunde.putSerializable("profile", sic.getProfile());
                                bunde.putString("mode", ProfileManageActivity.Mode.CREATE.toString());
                                bunde.putString("token", authSession.getToken());
                                createProfile.putExtras(bunde);
                                startActivity(createProfile);
                            }
                        });
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {

                    if (sic.getProfile().getEmail() == null || sic.getProfile().getEmail().length() == 0)
                    {
                        // Notify user that email is required
                    }

                    Log.e(TAG, "Request failed");
                }
            });

            Log.d(TAG, jSic);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Run within AsyncTask
     * @param authSession
     * @return true if profile exists
     */
    private boolean hasProfile(Api api, AuthSession authSession)
    {
        String param = api.getData(new ArrayList<Pair<String, ?>>() {{
            add(new Pair<>("request", "myProfileId"));
            add(new Pair<>("authId", authSession.getAuthId()));
        }});
        String url = Source.Api + "?" + param;
        String resp = api.Get(url);
        boolean success = new jStatus().getStatus(resp);
        return success;
    }


    private void GSO_SignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try
        {
            GoogleSignInAccount gsia = completedTask.getResult(ApiException.class);
            runLoginChallenge(new AuthChallenge(
                    gsia.getId(),
                    gsia.getEmail(),
                    gsia.getIdToken(),
                    AuthChallenge.oAuthProvider.GOOGLE,
                    new Profile(
                            gsia.getGivenName(),
                            gsia.getFamilyName(),
                            gsia.getEmail(),
                            ((gsia.getPhotoUrl() != null) ? gsia.getPhotoUrl().toString() : "")
                    ),
                    Device_ID
            ));


        }
        catch (ApiException e)
        {
            Log.w(TAG, "handleSignInResult:error", e);
        }
        catch (Exception e)
        {
            Log.w(TAG, "Error for GSO", e);
        }
    }


    /**
     * Standard android impmentasjon for å sjekke resultatet fra aktiviteten som ble startet
     * Request_Code er koden som separerer de forskjellige foresøprselene
     * Eks Facebook sin er forskjellig fra Google sin
     */
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
}
