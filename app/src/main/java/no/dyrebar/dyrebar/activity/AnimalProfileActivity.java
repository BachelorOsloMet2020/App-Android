package no.dyrebar.dyrebar.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import no.dyrebar.dyrebar.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_profile);

        animalName = findViewById(R.id.animal_name);
        idTag = findViewById(R.id.animal_id);
        animalImage = findViewById(R.id.animal_profile_image);
        animalType = findViewById(R.id.animal_type);
        animalSex = findViewById(R.id.animal_sex);
        animalSterilized = findViewById(R.id.animal_sterilized);
        animalColor = findViewById(R.id.animal_color);
        animalFurLength = findViewById(R.id.animal_fur_length);
        animalFurPattern = findViewById(R.id.animal_fur_pattern);
        animalDescription = findViewById(R.id.animal_description);

    }
}
