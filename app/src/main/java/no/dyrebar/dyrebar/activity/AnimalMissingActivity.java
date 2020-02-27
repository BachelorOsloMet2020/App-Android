package no.dyrebar.dyrebar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.classes.ProfileAnimal;
import no.dyrebar.dyrebar.classes.PublicProfile;
import no.dyrebar.dyrebar.handler.PermissionHandler;
import no.dyrebar.dyrebar.handler.TypeHandler;
import no.dyrebar.dyrebar.interfaces.PermissionInterface;
import no.dyrebar.dyrebar.json.jMissing;
import no.dyrebar.dyrebar.json.jProfile;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class AnimalMissingActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionInterface.PermissionListener
{
    private GoogleMap gmap;
    private ScrollView mainScrollView;
    private PermissionHandler pems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_missing);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pems = new PermissionHandler(this);
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        catch (NullPointerException npe)
        {
            npe.printStackTrace();
        }

        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("missing"))
        {
            Missing m = (Missing) b.getSerializable("missing");
            loadData(m.getAnimalId());
        }
        else
            finish();
    }

    private void loadData(int id)
    {
        AsyncTask.execute(() ->
                          {
                              String res = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
                              {{
                                  add(new Pair<>("request", "missing"));
                                  add(new Pair<>("id", id));
                              }});
                              publicProfile = new jProfile().decodePublic(res);
                              Missing m = new jMissing().decode(res);

                              runOnUiThread(() -> setProfileData(publicProfile));
                              runOnUiThread(() -> setMissingData(m));

                          });
    }

    PublicProfile publicProfile;
    private void setProfileData(PublicProfile profileData)
    {
        String name = profileData.getFirstName() + " " + profileData.getLastName();
        ((TextView)findViewById(R.id.contact_name)).setText(name);
        findViewById(R.id.contact_phone).setOnClickListener(v -> {
            if (pems.isPhonePermitted())
                call();
            else
                pems.getPhonePermission();
        });
        findViewById(R.id.contact_mail).setOnClickListener(v -> {
            mail();
        });
    }


    private ProfileAnimal animal;
    private void setMissingData(Missing missing)
    {
        animal = missing;
        ((TextView) findViewById(R.id.animal_name)).setText(missing.getName());
        ((TextView) findViewById(R.id.animal_idTag_text)).setText((animal.getTag_ID() == null || animal.getTag_ID().equals("null") || animal.getTag_ID().length() == 0) ? getString(R.string.not_given) : animal.getTag_ID());
        ((TextView) findViewById(R.id.missing_title)).setText(getString(R.string.missing) + " " + missing.getColor() + " " + new TypeHandler().getAnimalType(getApplicationContext(), missing.getAnimalType()));
        Picasso.get().load(missing.getImage()).placeholder(R.drawable.ic_dyrebarlogo).into((ImageView) findViewById(R.id.animal_profile_image), new Callback()
        {
            @Override
            public void onSuccess()
            {

            }

            @Override
            public void onError(Exception e)
            {
                e.printStackTrace();
            }
        });

        TypeHandler types = new TypeHandler();
        ((TextView) findViewById(R.id.animal_type)).setText(types.getAnimalType(this, missing.getAnimalType()));
        ((TextView) findViewById(R.id.animal_sex)).setText(types.getSex(this, missing.getSex()));
        ((TextView) findViewById(R.id.animal_sterilized)).setText(types.getSterilized(this, missing.getSterilized()));
        ((TextView) findViewById(R.id.animal_fur_length)).setText(types.getFurLength(this, missing.getFurLength()));
        ((TextView) findViewById(R.id.animal_fur_pattern)).setText(types.getFurPattern(this, missing.getFurPattern()));
        ((TextView) findViewById(R.id.animal_color)).setText(missing.getColor());
        ((TextView) findViewById(R.id.animal_description)).setText(missing.getDescription());
        TextView description = findViewById(R.id.animal_description);
        Log.d(getClass().getName(), "Line count: " + description.getLineCount());
        if (description.getLineCount() > 7)
        {
            description.setMaxLines(7);
            findViewById(R.id.animal_description_show_more).setVisibility(View.VISIBLE);
            findViewById(R.id.animal_description_show_more).setOnClickListener(showMore);
        }


        if (animal.getExtras() == null || animal.getExtras().equals("null") || animal.getExtras().length() == 0)
            ((TextView) findViewById(R.id.animal_typeExtras)).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.animal_typeExtras)).setText(missing.getExtras());

        mainScrollView = findViewById(R.id.animal_missing_scrollview);

        MapView mapView = (MapView)findViewById(R.id.mapView);

        mapView.onCreate(null);
        mapView.onResume();
        try
        {
            MapsInitializer.initialize(getApplicationContext());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);

        View v = findViewById(R.id.mapView_scrollHelper);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });

    }


    View.OnClickListener showMore = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            AlertDialog ad = new AlertDialog.Builder(AnimalMissingActivity.this)
                    .setTitle(animal.getName())
                    .setMessage(animal.getDescription())
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> dialog.dismiss())
                    .show();
            TextView tv = ad.findViewById(android.R.id.message);
            tv.setScroller(new Scroller(getBaseContext()));
            tv.setVerticalScrollBarEnabled(true);
            tv.setMovementMethod(new ScrollingMovementMethod());
        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        gmap = googleMap;
        LatLng missingLoc = new LatLng(((Missing)animal).getLat(), ((Missing)animal).getLng());
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(missingLoc, 10);
        //gMap.addMarker(new MarkerOptions().position(meth).title("Maker in Ass"));
        gmap.animateCamera(cu);
        gmap.addMarker(new MarkerOptions().position(missingLoc).title(animal.getName()));
    }

    @SuppressLint("MissingPermission")
    private void call()
    {
        if (pems.isPhonePermitted())
        {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + publicProfile.getTlf()));
            startActivity(call);
        }
    }

    private void mail()
    {
        String subject = animal.getName() + ", " + getString(R.string.missing) + " " + animal.getColor() + " " + new TypeHandler().getSex(getApplicationContext(), animal.getSex()) + " " + new TypeHandler().getAnimalType(getApplicationContext(), animal.getAnimalType());
        Intent mail = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + publicProfile.getEmail() + "?subject=" + subject);
        mail.setData(data);
        startActivity(mail);
    }


    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestFilePermission()
    {

    }

    @Override
    public void onRequestGpsPermission()
    {

    }

    @Override
    public void onRequestCameraPermission()
    {

    }

    @Override
    public void onRequestPhonePermission()
    {
        pems.getPhonePermission();
    }

    @Override
    public void onStoragePermitted(boolean permitted)
    {

    }

    @Override
    public void onCameraPermitted(boolean permitted)
    {

    }

    @Override
    public void onGpsPermitted(boolean permitted)
    {

    }

    @Override
    public void onPhonePermitted(boolean permitted)
    {
        if (permitted)
            call();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pems.onPermissionsResult(requestCode, permissions, grantResults);
    }
}