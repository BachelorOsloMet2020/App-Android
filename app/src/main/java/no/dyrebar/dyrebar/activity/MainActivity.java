package no.dyrebar.dyrebar.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import no.dyrebar.dyrebar.interfaces.FragmentInterface;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class MainActivity extends AppCompatActivity implements FragmentInterface.FragmentListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (prevFragment == null && fragmentTransaction.isEmpty() && !hasFragments())
        {
            fragmentTransaction.add(R.id.container_fragments, fragment, tag);
        }
        else
        {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.replace(R.id.container_fragments, fragment, tag);
        }
        fragmentTransaction.commitNow();
        if (fragment instanceof HomeFragment)
            ((HomeFragment)fragment).setOnMainActivityListener(this);
        else if (fragment instanceof FoundFragment)
            ((FoundFragment)fragment).setOnMainActivityListener(this);
        else if (fragment instanceof MissingFragment)
            ((MissingFragment)fragment).setOnMainActivityListener(this);
        else if (fragment instanceof BloggFragment)
            ((BloggFragment)fragment).setOnMainActivityListener(this);
        prevFragment = fragment;
    }

    /**
     * Method for checking if fragment already excist in backstack
     * Preventing NPE
     * @return true if it has fragments
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

    @Override
    public void onSetToolbar(Toolbar toolbar)
    {
        if (toolbar != null)
            setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
