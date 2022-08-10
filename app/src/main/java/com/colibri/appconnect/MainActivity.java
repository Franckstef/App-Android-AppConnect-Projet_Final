package com.colibri.appconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.colibri.appconnect.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();


        Button connexion = findViewById(R.id.buttonConnexion);

        connexion.setOnClickListener(v -> {

            mAuth.signInWithEmailAndPassword(binding.editTextPseudo.getText().toString(), binding.editTextPassword.getText().toString())
                    .addOnCompleteListener(this, task -> {

                        if (task.isSuccessful()) {
                            Intent HomeIntent = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(HomeIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            if (v != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(this, "authenticated already", Toast.LENGTH_SHORT).show();

            Intent HomeIntent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(HomeIntent);

        }
    }

}