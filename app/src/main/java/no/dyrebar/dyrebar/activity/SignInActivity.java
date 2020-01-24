package no.dyrebar.dyrebar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import no.dyrebar.dyrebar.R;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends AppCompatActivity
{

    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInButton = findViewById(R.id.g_sign_in_btn);

        /**
         * Requesting user ID, email and profile
         */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();



        signInButton.setOnClickListener(view -> signIn());
    }

    /**
     * Her opprettet jeg egen metode "signIn" (ikke i google malen)
     */
    private void signIn()
    {
        Task<GoogleSignInAccount> task GoogleSignIn.getSignedInAccountFromIntent(data);
        handleSignInResult(task);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try
        {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            /**
             * HER SKAL ID TOKEN SENDES TIL SERVER OG VALI
             */

            updateUI(account);
        }
        catch (ApiException e)
        {
            Log.w(TAG, "handleSignInResult:error", e);
            updateUI(null);
        }
    }

    @Override
    protected void onStart()
    {
        /**
         * Check if user is already signed in
         */
        super.onStart();
        mGoogleSignInClient.silentSignIn()
                .addOnCompleteListener(
                        this,
                        task -> handleSignInResult(task)
                );
    }
}
