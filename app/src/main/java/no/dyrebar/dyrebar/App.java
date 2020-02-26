package no.dyrebar.dyrebar;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.Map;

import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.handler.SettingsHandler;

public class App extends Application implements Application.ActivityLifecycleCallbacks
{

    public static Profile profile;
    public static AuthSession authSession;

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


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState)
    {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity)
    {
        Map<String, ?> map = new SettingsHandler(getApplicationContext()).getMultilineSetting(S.Dyrebar_Auth);
        if (map.size() > 0)
        {
            AuthSession authSession = new AuthSession(map);
            if (authSession.objVal())
            {
                App.authSession = authSession;
            }
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity)
    {

    }
}
