package no.dyrebar.dyrebar.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.ProfileAnimal;

public class AnimalProfileActivity extends AppCompatActivity
{
    TextView animalName;
    TextView idTag;
    ImageView animalImage;
    TextView animalType;
    TextView animalSex;
    TextView animalSterilized;
    TextView animalColor;
    TextView animalFurLength;
    TextView animalFurPattern;
    TextView animalDescription;

    private ProfileAnimal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_profile);


        animalType = findViewById(R.id.animal_type);
        animalSex = findViewById(R.id.animal_sex);
        animalSterilized = findViewById(R.id.animal_sterilized);
        animalFurLength = findViewById(R.id.animal_fur_length);
        animalFurPattern = findViewById(R.id.animal_fur_pattern);

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
        ((TextView)findViewById(R.id.animal_id)).setText(animal.getTag_ID());
        Picasso.get().load(animal.getImage()).placeholder(R.drawable.ic_dyrebarlogo).into((ImageView) findViewById(R.id.animal_profile_image), new Callback() {
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
        // [...] handle ints
        ((TextView)findViewById(R.id.animal_color)).setText(animal.getColor());
        // [...] handle ints
        ((TextView)findViewById(R.id.animal_description)).setText(animal.getDescription());
    }
}
