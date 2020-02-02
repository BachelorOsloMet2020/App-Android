package no.dyrebar.dyrebar.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.List;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.fragment.BloggFragment;
import no.dyrebar.dyrebar.fragment.FoundFragment;
import no.dyrebar.dyrebar.fragment.HomeFragment;
import no.dyrebar.dyrebar.fragment.MissingFragment;
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


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        LaunchFragment(new HomeFragment(), "");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem ->
    {
        switch (menuItem.getItemId())
        {
            case R.id.nav_home:
                LaunchFragment(new HomeFragment(), "");
                break;
            case R.id.nav_missing:
                LaunchFragment(new MissingFragment(), "");
                break;
            case R.id.nav_found:
                LaunchFragment(new FoundFragment(), "");
                break;
            case R.id.nav_blogg:
                LaunchFragment(new BloggFragment(), "");
                break;
        }
        return true;
    };

    /**
     * Method for replacing of existing and launching new fragments
     */
    private Fragment prevFragment = null;
    private void LaunchFragment(Fragment fragment, final String tag)
    {
        final Fragment finalFragment = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (prevFragment == null && fragmentTransaction.isEmpty() && !hasFragments())
        {
            fragmentTransaction.add(R.id.container_fragments, finalFragment, tag);
        }
        else
        {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.replace(R.id.container_fragments, finalFragment, tag);
        }
        fragmentTransaction.commitAllowingStateLoss();
        prevFragment = finalFragment;
    }

    /**
     * Method for checking if fragment already excist in backstack
     * Preventing NPE
     * @return
     */
    public boolean hasFragments()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fragments = fragmentManager.getFragments();
        }
        else
            return true;
        if (fragmentManager.getBackStackEntryCount() > 0 || fragments.size() > 0)
            return true;
        else
            return false;
    }
}
