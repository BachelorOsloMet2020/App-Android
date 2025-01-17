package no.dyrebar.dyrebar.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;
import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.dialog.IndicatorDialog;
import no.dyrebar.dyrebar.handler.PermissionHandler;
import no.dyrebar.dyrebar.handler.SettingsHandler;
import no.dyrebar.dyrebar.json.jAuthSession;
import no.dyrebar.dyrebar.json.jProfile;
import no.dyrebar.dyrebar.json.jStatus;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkApi();

    }

    private void checkApi()
    {
        AsyncTask.execute(() -> {
            Api api = new Api();
            String data = api.Get(Source.Api + "?request=heartbeat");
            boolean success = new jStatus().getStatus(data);
            if (success)
                runOnUiThread(() -> validateSession(readAuthSession()));
            else
            {
                runOnUiThread(() -> {
                    AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.DialogAlert);
                    adb.setTitle("Error");
                    adb.setMessage("Data from api indicated an issue");
                    adb.setNeutralButton("Ok", (dialog, which) -> dialog.dismiss());
                    adb.create().show();
                });
            }
        });


    }


    private AuthSession readAuthSession()
    {
        Map<String, ?> map = new SettingsHandler(getApplicationContext()).getMultilineSetting(S.Dyrebar_Auth);
        if (map.size() > 0)
        {
            AuthSession authSession = new AuthSession(map);
            if (!authSession.objVal())
            {
                // Clear data, inclomplete or corrupted
                new SettingsHandler(getApplicationContext()).deleteSetting(S.Dyrebar_Auth);
                return null;
            }
            else
                return authSession;

        }
        else
            return null;
    }

    private Profile profile;
    private void validateSession(AuthSession authSession)
    {
        if (authSession != null && authSession.objVal())
        {
            App.authSession = authSession;
            AsyncTask.execute(() -> {
               try
               {
                   String json = new jAuthSession().encode(authSession);
                   String resp = new Api().Post(Source.Api, new ArrayList<Pair<String, ?>>(){{
                       add(new Pair<>("auth", "validate"));
                       add(new Pair<>("data", json));
                   }});
                   boolean success = new jStatus().getStatus(resp);
                   boolean profileExists = hasProfile(new Api(), authSession);

                   if (success && new JSONObject(resp).getBoolean("isValid") && profileExists)
                   {
                       String presp = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
                       {{
                           add(new Pair<>("request", "myProfile"));
                           add(new Pair<>("token", authSession.getToken()));
                           add(new Pair<>("authId", authSession.getAuthId()));
                       }});
                       profile = new jProfile().decode(presp);
                       prepareForMainActivity();
                       //loadMainActivity(profile);
                   }
                   else
                   {
                       runOnUiThread(this::loadSignIn);
                   }
               }
               catch (JSONException e)
               {
                   e.printStackTrace();
                   runOnUiThread(this::loadSignIn);
               }
            });
        }
        else
            loadSignIn();
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

    private void loadSignIn()
    {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void prepareForMainActivity()
    {
        PermissionHandler pems = new PermissionHandler(this);
        if (!pems.hasRequiredPermissions())
        {
            Intent intent = new Intent(this, PermissionsActivity.class);
            startActivityForResult(intent, AR_ID_PEMS);
        }
        else
            loadMainActivity(profile);
    }



    private void loadMainActivity(Profile profile)
    {
        Intent intent = new Intent(this, MainActivity.class);
        if (profile != null)
        {
            Bundle b = new Bundle();
            b.putSerializable("profile", profile);
            intent.putExtras(b);
        }
        startActivity(intent);
        finish();
    }

    private final int AR_ID_PEMS = 849;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AR_ID_PEMS)
        {
            if (resultCode == RESULT_OK)
                loadMainActivity(profile);
            else
            {
                Log.e(getClass().getName(), "Not success");
            }
        }
    }
}
