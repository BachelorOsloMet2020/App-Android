package no.dyrebar.dyrebar.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.ArrayList;

import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;
import no.dyrebar.dyrebar.classes.AuthChallenge;
import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.dialog.IndicatorDialog;
import no.dyrebar.dyrebar.handler.PermissionHandler;
import no.dyrebar.dyrebar.handler.SettingsHandler;
import no.dyrebar.dyrebar.json.jAuthChallenge;
import no.dyrebar.dyrebar.json.jAuthSession;
import no.dyrebar.dyrebar.json.jStatus;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class SignInEmailActivity extends AppCompatActivity
{
    private final int AR_ID_PEMS = 849;

    private AuthSession authSession;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_email);
        attachListeners();
    }

    private void attachListeners()
    {
        findViewById(R.id.sign_in_email_button).setOnClickListener(v -> {
            signIn();
        });
        findViewById(R.id.register_email_button).setOnClickListener(v -> {
            register();
        });
    }

    private void signIn()
    {
        AuthChallenge ac = getAuthChallenge();
        id = new IndicatorDialog(this, getString(R.string.please_wait), getString(R.string.challenge_sign_in_message));
        id.Show();

        AsyncTask.execute(() -> {
            try
            {
                String jac = new jAuthChallenge().encode(ac);
                String resp = new Api().Post(Source.Api, new ArrayList<Pair<String, ?>>() {{
                    add(new Pair<>("auth", AuthChallenge.oAuthProvider.DYREBAR));
                    add(new Pair<>("data", jac));
                }});
                boolean success = new jStatus().getStatus(resp);
                if (success && ac.getEmail() != null && ac.getEmail().length() > 0)
                {
                    authSession = new jAuthSession().decode(resp);
                    App.authSession = authSession;
                    new SettingsHandler(getApplicationContext()).setMultilineSetting(S.Dyrebar_Auth, authSession.asList());
                    prepareForNewActivity();
                }
                else
                {
                    // Show error
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        });


    }

    private void register()
    {
        AuthChallenge ac = getAuthChallenge();
        requestRegister(ac);
    }

    private AuthChallenge getAuthChallenge()
    {
        boolean valid = true;

        String email = ((TextInputEditText)findViewById(R.id.sign_in_email_text)).getText().toString();
        String password = ((TextInputEditText)findViewById(R.id.sign_in_password_text)).getText().toString();

        if (password.length() == 0)
        {
            ((TextInputEditText)findViewById(R.id.sign_in_password_text)).setError(getString(R.string.register_account_password_missing));
            valid = false;
        }
        else if (password.length() < 6)
        {
            ((TextInputEditText)findViewById(R.id.sign_in_password_text)).setError(getString(R.string.register_account_password_short));
            valid = false;
        }
        else
        {
            ((TextInputEditText)findViewById(R.id.sign_in_password_text)).setError(null);
        }

        if (email.length() == 0)
        {
            ((TextInputEditText)findViewById(R.id.sign_in_email_text)).setError(getString(R.string.register_account_email_missing));
            valid = false;

        }
        else if (!email.contains("@"))
        {
            ((TextInputEditText)findViewById(R.id.sign_in_email_text)).setError(getString(R.string.register_account_email_notValid));
            valid = false;
        }
        else
        {
            ((TextInputEditText)findViewById(R.id.sign_in_email_text)).setError(null);
        }

        if (valid)
            return new AuthChallenge(email, password, AuthChallenge.oAuthProvider.DYREBAR);
        else
            return null;
    }

    private IndicatorDialog id;
    private void requestRegister(AuthChallenge ac)
    {
        id = new IndicatorDialog(this, getString(R.string.challenge_register_title), getString(R.string.challenge_register_message));
        id.Show();

        AsyncTask.execute(() -> {
            try
            {
                String jac = new jAuthChallenge().encode(ac);
                String resp = new Api().Post(Source.Api, new ArrayList<Pair<String, ?>>(){{
                    add(new Pair<>("auth", "REGISTER"));
                    add(new Pair<>("data", jac));
                }});
                runOnUiThread(() -> {
                    id.Hide();
                });
                boolean success = new jStatus().getStatus(resp);
                if (!success)
                {
                    // Show error
                }
                else
                {
                    // User registered
                    runOnUiThread(this::signIn);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        });
    }

    private void prepareForNewActivity()
    {
        PermissionHandler pems = new PermissionHandler(this);
        if (!pems.hasRequiredPermissions())
        {
            Intent intent = new Intent(this, PermissionsActivity.class);
            startActivityForResult(intent, AR_ID_PEMS);
        }
        else
            continueToNewActivity();
    }

    private void continueToNewActivity()
    {
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
                Intent createProfile = new Intent(this, ProfileManageActivity.class);
                Bundle bunde = new Bundle();
                bunde.putString("mode", ProfileManageActivity.Mode.CREATE.toString());
                bunde.putString("token", authSession.getToken());
                createProfile.putExtras(bunde);
                startActivity(createProfile);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AR_ID_PEMS)
        {
            if (resultCode == RESULT_OK)
                AsyncTask.execute(this::continueToNewActivity); //Gets on main thread, async to prevent Network on Main Thread
            else
            {
                Log.e(getClass().getName(), "Not success");
            }
        }

    }


}
