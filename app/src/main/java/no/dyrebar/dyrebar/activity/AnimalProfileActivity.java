package no.dyrebar.dyrebar.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.AuthSession;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.classes.ProfileAnimal;
import no.dyrebar.dyrebar.handler.MissingHandler;
import no.dyrebar.dyrebar.handler.PermissionHandler;
import no.dyrebar.dyrebar.handler.TypeHandler;
import no.dyrebar.dyrebar.interfaces.PermissionInterface;
import no.dyrebar.dyrebar.json.jAuthSession;
import no.dyrebar.dyrebar.json.jMissing;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class AnimalProfileActivity extends AppCompatActivity implements MissingHandler.MissingListener
{
    private ProfileAnimal animal;
    private MissingHandler missingHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_profile);
        missingHandler = new MissingHandler(this, this);
        if (getIntent().getExtras() != null && getIntent().hasExtra("animal"))
        {
            Bundle b = getIntent().getExtras();
            animal = (ProfileAnimal) b.getSerializable("animal");
            loadAnimal();
        }

    }
    private void loadAnimal()
    {
        ((TextView)findViewById(R.id.animal_name)).setText(animal.getName());
        ((TextView)findViewById(R.id.animal_idTag_text)).setText((animal.getTag_ID() == null || animal.getTag_ID().equals("null") || animal.getTag_ID().length() == 0) ? getString(R.string.not_given) : animal.getTag_ID());
        Picasso.get().load(animal.getImage()).placeholder(R.drawable.ic_dyrebarlogo).memoryPolicy(MemoryPolicy.NO_CACHE).into((ImageView) findViewById(R.id.animal_profile_image), new Callback() {
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
        ((TextView)findViewById(R.id.animal_type)).setText(types.getAnimalType(this, animal.getAnimalType()));
        ((TextView)findViewById(R.id.animal_sex)).setText(types.getSex(this, animal.getSex()));
        ((TextView)findViewById(R.id.animal_sterilized)).setText(types.getSterilized(this, animal.getSterilized()));
        ((TextView)findViewById(R.id.animal_fur_length)).setText(types.getFurLength(this, animal.getFurLength()));
        ((TextView)findViewById(R.id.animal_fur_pattern)).setText(types.getFurPattern(this, animal.getFurPattern()));
        ((TextView)findViewById(R.id.animal_color)).setText(animal.getColor());
        ((TextView)findViewById(R.id.animal_description)).setText(animal.getDescription());
        if (animal.getExtras() == null || animal.getExtras().equals("null") || animal.getExtras().length() == 0)
            ((TextView)findViewById(R.id.animal_typeExtras)).setVisibility(View.GONE);
        else
            ((TextView)findViewById(R.id.animal_typeExtras)).setText(animal.getExtras());

        findViewById(R.id.animal_report_missing).setOnClickListener(v -> {
            if (App.authSession != null && animal != null)
            {
                missingHandler.RequestMissing(animal, "");
            }
        });
    }

    private void publishMissing(AuthSession authSession, Missing missing)
    {

        String data = new jMissing().encode(missing);
        if (data == null)
        {
            // TODO: Show error message
            return;
        }
        AsyncTask.execute(() -> {
            String res = new Api().Post(Source.Api, new ArrayList<Pair<String, ?>>()
            {{
                add(new Pair<>("request", "missing"));
                add(new Pair<>("authId", authSession.getAuthId()));
                add(new Pair<>("token", authSession.getToken()));
                add(new Pair<>("uid", App.profile.getId()));
                add(new Pair<>("data", data));
            }});
            Log.d(getClass().getName(), "API -> " + res);
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        missingHandler.onPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMissingRequestCreated(Missing missing)
    {
        publishMissing(App.authSession, missing);
    }

    @Override
    public void onMissingRequestCreatedNoLocation(Missing missing)
    {

    }
}
