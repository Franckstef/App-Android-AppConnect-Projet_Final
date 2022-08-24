package com.colibri.appconnect.userprofile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import com.colibri.appconnect.R;

import com.colibri.appconnect.userprofile.ui.main.ProfileFragment2;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

//    public static final String USER = "User";
    public static final String USERID = "UserId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.toolbar);
        final ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        actionBar.setTitle("Profile");
        final Intent i = getIntent();
        String userId = null;
        if(i.hasExtra(USERID)){
            userId = i.getStringExtra(USERID);
            actionBar.setTitle("Contact");
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProfilFragment.newInstance(userId))
                    .commitNow();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}