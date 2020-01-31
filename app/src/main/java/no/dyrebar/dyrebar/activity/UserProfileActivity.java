package no.dyrebar.dyrebar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import no.dyrebar.dyrebar.R;

public class UserProfileActivity extends AppCompatActivity
{
        ImageView profilePicture;
        ImageView editProfile;

        TextView userName;
        TextView userAddress;
        TextView userPhone;
        TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profilePicture = (ImageView) findViewById(R.id.user_profile_picture);
        editProfile = (ImageView) findViewById(R.id.edit_user_profile);
        userName = (TextView) findViewById(R.id.user_profile_name);
        userAddress = (TextView) findViewById(R.id.user_profile_address);
        userPhone = (TextView) findViewById(R.id.user_profile_phone);
        userEmail = (TextView) findViewById(R.id.user_profile_email);

        startKeyListener();
    }

    public void startKeyListener()
    {
        findViewById(R.id.add_animal_fab).setOnClickListener(view -> addAnimal());
        findViewById(R.id.edit_user_profile).setOnClickListener(view -> editProfile());
    }

    public void loadUserProfile()
    {

    }

    public void addAnimal()
    {
        Intent intent = new Intent(this, AddAnimalActivity.class);
    }

    public void editProfile()
    {

    }
}
