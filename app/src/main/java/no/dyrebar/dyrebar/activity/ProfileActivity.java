package no.dyrebar.dyrebar.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.S;
import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.extlib.PicassoCircleTransform;
import no.dyrebar.dyrebar.handler.SettingsHandler;
import no.dyrebar.dyrebar.json.jProfile;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class ProfileActivity extends AppCompatActivity
{
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadUserProfile();
    }

    public void startKeyListener()
    {
        findViewById(R.id.add_animal_fab).setOnClickListener(view -> addAnimal());
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
            runOnUiThread(() -> {
                profile = new jProfile().decode(presp);
                Picasso.get().load(profile.getImage()).transform(new PicassoCircleTransform()).into((ImageView) findViewById(R.id.create_profile_image));
                ((TextView)findViewById(R.id.user_profile_name)).setText(profile.getFirstName() + ", " + profile.getLastName());
                ((TextView)findViewById(R.id.user_profile_email)).setText(profile.getEmail());
                ((TextView)findViewById(R.id.user_profile_address)).setText(profile.getAddress() + ", " + profile.getPostNumber());
                ((TextView)findViewById(R.id.user_profile_phone)).setText(profile.getTlf());

                startKeyListener();
            });

        });
    }

    public void addAnimal()
    {
        Intent intent = new Intent(this, AnimalAddActivity.class);
        startActivity(intent);
    }

    public void editProfile()
    {
        Bundle b = new Bundle();
        b.putSerializable("profile", profile);
        b.putString("mode", ProfileManageActivity.Mode.UPDATE.toString());
        Intent intent = new Intent(this, ProfileManageActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
