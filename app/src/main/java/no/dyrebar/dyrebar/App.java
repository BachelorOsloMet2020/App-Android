package no.dyrebar.dyrebar;

import android.app.Application;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        AppCenter.start(this, "43cb4531-7666-41d7-b4d7-b6d62ab299e3",
                Analytics.class, Crashes.class);

        if (no.dyrebar.dyrebar.web.Certificate.sslContext == null)
        {
            no.dyrebar.dyrebar.web.Certificate cert = new no.dyrebar.dyrebar.web.Certificate();
            cert.InitializeCetificate(getApplicationContext());
        }
    }
}
