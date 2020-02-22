package no.dyrebar.dyrebar.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import no.dyrebar.dyrebar.BuildConfig;
import no.dyrebar.dyrebar.CustomControls.SettingClickView;
import no.dyrebar.dyrebar.CustomControls.SettingSwitchView;
import no.dyrebar.dyrebar.CustomControls.SettingTextView;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;

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

    }

    private void startBrowser(String url)
    {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(webIntent);
    }
}
