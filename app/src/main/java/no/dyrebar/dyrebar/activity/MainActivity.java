package no.dyrebar.dyrebar.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;
import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.extlib.PicassoCircleTransform;
import no.dyrebar.dyrebar.fragment.BloggFragment;
import no.dyrebar.dyrebar.fragment.FoundFragment;
import no.dyrebar.dyrebar.fragment.HomeFragment;
import no.dyrebar.dyrebar.fragment.MissingFragment;
import no.dyrebar.dyrebar.handler.SettingsHandler;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;
import no.dyrebar.dyrebar.json.jProfile;
import no.dyrebar.dyrebar.json.jStatus;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class MainActivity extends AppCompatActivity implements FragmentInterface.FragmentListener
{

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getMyProfile();
        onCreateCompleted();
    }

    private void getMyProfile()
    {
        if (getIntent().hasExtra("profile"))
        {
            profile = (Profile) getIntent().getSerializableExtra("profile");
            App.profile = profile;
            setToolbarProfile();
        }
        else if (App.authSession != null)
        {
           AsyncTask.execute(() -> {
               String presp = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
               {{
                   add(new Pair<>("request", "myProfile"));
                   add(new Pair<>("token", App.authSession.getToken()));
                   add(new Pair<>("authId", App.authSession.getAuthId()));
               }});
               boolean success = new jStatus().getStatus(presp);
               if (!success)
               {
                   runOnUiThread(this::setGuestMode);
                   return;
               }
               Profile _profile = new jProfile().decode(presp);
               runOnUiThread(() -> {
                   profile = _profile;
                   App.profile = profile;
                   setToolbarProfile();
               });
           });
        }
        else
        {
            setGuestMode();
        }
    }

    private void setGuestMode()
    {
        profile = new Profile();
        profile.setGuest(true);
        App.profile = profile;
        setToolbarProfile();
    }


    private void setToolbarProfile()
    {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (profile.isGuest())
        {
            ((TextView)toolbar.findViewById(R.id.toolbar_profile_name)).setText(getString(R.string.signin));
            ((ImageView)toolbar.findViewById(R.id.toolbar_profile_image)).setImageResource(R.drawable.ic_account_circle_black_24dp);
            toolbar.findViewById(R.id.toolbar_profile).setOnClickListener(v -> {
                startActivity(new Intent(this, SignInActivity.class));
                finish();
            });
        }
        else
        {
            if (toolbar.findViewById(R.id.toolbar_profile_image) != null && profile != null)
            {
                ImageView profileImage = toolbar.findViewById(R.id.toolbar_profile_image);
                Picasso.get().load(profile.getImage()).transform(new PicassoCircleTransform()).memoryPolicy(MemoryPolicy.NO_CACHE).into(profileImage);
            }
            if (toolbar.findViewById(R.id.toolbar_profile_name) != null && profile != null)
                ((TextView)toolbar.findViewById(R.id.toolbar_profile_name)).setText(profile.getFirstName());
            toolbar.findViewById(R.id.toolbar_profile).setOnClickListener(v -> {
                startActivity(new Intent(this, ProfileActivity.class));
            });
        }
    }



    private void onCreateCompleted()
    {
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
        if (toolbar == null)
            return;
        setSupportActionBar(toolbar);
    }

    @Override
    public Profile getProfile()
    {
        return profile;
    }

    @Override
    public void launchActivity(Intent i)
    {
        startActivity(i);
    }


}
