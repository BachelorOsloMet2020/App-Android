package no.dyrebar.dyrebar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCenter.start(getApplication(), "43cb4531-7666-41d7-b4d7-b6d62ab299e3",
                Analytics.class, Crashes.class);
        setContentView(R.layout.activity_main);

        if (no.dyrebar.dyrebar.web.Certificate.sslContext == null)
        {
            no.dyrebar.dyrebar.web.Certificate cert = new no.dyrebar.dyrebar.web.Certificate();
            cert.InitializeCetificate(getApplicationContext());
        }


        AsyncTask.execute(() -> {
            Api api = new Api();
            String data = api.Get(Source.Api + "?request=heartbeat");
            Log.d("TEST", data);
        });


    }
}