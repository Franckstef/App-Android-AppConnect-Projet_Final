package com.colibri.appconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connexion = findViewById(R.id.buttonConnexion);

        connexion.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
        });
    }


}