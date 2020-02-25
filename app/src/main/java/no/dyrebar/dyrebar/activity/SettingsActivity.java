package no.dyrebar.dyrebar.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.BuildConfig;
import no.dyrebar.dyrebar.CustomControls.SettingClickView;
import no.dyrebar.dyrebar.CustomControls.SettingSwitchView;
import no.dyrebar.dyrebar.CustomControls.SettingTextView;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;
import no.dyrebar.dyrebar.json.jStatus;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

import static no.dyrebar.dyrebar.web.Source.Api;

public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ((SettingClickView)findViewById(R.id.settingClickView_License)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //startBrowser(getString(R.string.link_terms_of_use));
            }
        });
        ((SettingClickView)findViewById(R.id.settingClickView_privacy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startBrowser(getString(R.string.link_privacy_policy));
            }
        });
        ((SettingClickView)findViewById(R.id.settingClickView_TOU)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startBrowser(getString(R.string.link_terms_of_use));
            }
        });

        ((SettingSwitchView)findViewById(R.id.settingSwitchView_notifications)).RegisterSetting(S.Dyrebar_Notification);
        ((SettingTextView)findViewById(R.id.settingTextView_appVersion)).setTextBottom(BuildConfig.VERSION_NAME);

        findViewById(R.id.log_out_btn).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.settings_sign_out_title));
            builder.setMessage(getString(R.string.settings_sign_out_message));
            builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
            builder.setPositiveButton(getString(R.string.yes), (dialog, which) ->
            {
                signOut();
                dialog.dismiss();
            });
            builder.create().show();
        });
    }

    private void signOut()
    {
        AsyncTask.execute(() -> {
            String result = new Api().Post(Api, new ArrayList<Pair<String, ?>>()
            {{
                add(new Pair<>("auth", "endSession"));
                add(new Pair<>("authId", App.authSession.getAuthId()));
                add(new Pair<>("token", App.authSession.getToken()));
            }});
            boolean success = new jStatus().getStatus(result);
            runOnUiThread(() -> {
                if (!success)
                {
                    Log.e(getClass().getName(), "Api -> " + result);
                    Toast.makeText(getApplicationContext(), getString(R.string.settings_sign_out_error_message), Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(this, SplashActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            });
        });
    }

    private void startBrowser(String url)
    {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(webIntent);
    }
}
