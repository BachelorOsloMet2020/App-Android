package no.dyrebar.dyrebar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import no.dyrebar.dyrebar.R;

public class SignInEmailActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_email);
    }

    private void attachListeners()
    {
        findViewById(R.id.sign_in_email_button).setOnClickListener(v -> {

        });
        findViewById(R.id.register_email_button).setOnClickListener(v -> {

        });
    }


}
