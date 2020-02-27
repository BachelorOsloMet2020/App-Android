package no.dyrebar.dyrebar.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;
import no.dyrebar.dyrebar.adapter.MyAnimalAdapter;
import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.classes.ProfileAnimal;
import no.dyrebar.dyrebar.extlib.PicassoCircleTransform;
import no.dyrebar.dyrebar.fragment.BloggFragment;
import no.dyrebar.dyrebar.fragment.FoundFragment;
import no.dyrebar.dyrebar.fragment.HomeFragment;
import no.dyrebar.dyrebar.fragment.MissingFragment;
import no.dyrebar.dyrebar.fragment.MyAnimalsFragment;
import no.dyrebar.dyrebar.fragment.MyPostersFragment;
import no.dyrebar.dyrebar.handler.SettingsHandler;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;
import no.dyrebar.dyrebar.json.jProfile;
import no.dyrebar.dyrebar.json.jProfileAnimal;
import no.dyrebar.dyrebar.json.jStatus;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class ProfileActivity extends AppCompatActivity implements FragmentInterface.FragmentListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        catch (NullPointerException npe)
        {
            npe.printStackTrace();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        LaunchFragment(new MyAnimalsFragment(), "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        if (item.getItemId() == R.id.profile_toolbar_menu_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadUserProfile();
    }

    public void startKeyListener()
    {
        findViewById(R.id.edit_user_profile).setOnClickListener(view -> editProfile());
    }

    public void loadUserProfile()
    {
        AuthSession authSession = new AuthSession(new SettingsHandler(getApplicationContext()).getMultilineSetting(S.Dyrebar_Auth));
        AsyncTask.execute(() -> {

            String presp = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
            {{
                add(new Pair<>("request", "myProfile"));
                add(new Pair<>("token", authSession.getToken()));
                add(new Pair<>("authId", authSession.getAuthId()));
            }});
            boolean success = new jStatus().getStatus(presp);
            if (!success)
            {
                Log.e(getClass().getName(), "API:" + presp);
                // TODO: Show critical dialog here
                return;
            }
            App.profile = new jProfile().decode(presp);
            runOnUiThread(() -> {
                Picasso.get().load(App.profile.getImage()).transform(new PicassoCircleTransform()).into((ImageView) findViewById(R.id.create_profile_image));
                ((TextView)findViewById(R.id.user_profile_name)).setText(App.profile.getFirstName() + ", " + App.profile.getLastName());
                ((TextView)findViewById(R.id.user_profile_email)).setText(App.profile.getEmail());
                ((TextView)findViewById(R.id.user_profile_address)).setText(App.profile.getAddress() + ", " + App.profile.getPostNumber());
                ((TextView)findViewById(R.id.user_profile_phone)).setText(App.profile.getTlf());

                startKeyListener();
            });

        });
    }



    public void editProfile()
    {
        Bundle b = new Bundle();
        b.putSerializable("profile", App.profile);
        b.putString("mode", ProfileManageActivity.Mode.UPDATE.toString());
        Intent intent = new Intent(this, ProfileManageActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem ->
    {
        switch (menuItem.getItemId())
        {
            case R.id.nav_my_animals:
                LaunchFragment(new MyAnimalsFragment(), "");
                break;
            case R.id.nav_my_posters:
                LaunchFragment(new MyPostersFragment(), "");
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

    }

    @Override
    public Profile getProfile()
    {
        return null;
    }

    @Override
    public void launchActivity(Intent i)
    {

    }
}
