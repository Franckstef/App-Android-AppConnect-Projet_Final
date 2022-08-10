package com.colibri.appconnect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.colibri.appconnect.databinding.ActivityHomeBinding;
import com.google.firebase.firestore.CollectionReference;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    ActivityHomeBinding binding;
    CollectionReference testRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }



}