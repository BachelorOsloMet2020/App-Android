package no.dyrebar.dyrebar;

import android.app.Application;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        if (no.dyrebar.dyrebar.web.Certificate.sslContext == null)
        {
            no.dyrebar.dyrebar.web.Certificate cert = new no.dyrebar.dyrebar.web.Certificate();
            cert.InitializeCetificate(getApplicationContext());
        }
    }
}
