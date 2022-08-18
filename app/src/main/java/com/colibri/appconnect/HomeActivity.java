package com.colibri.appconnect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnButtonClickedListener {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.toolbar);

        setBottomNavigation();
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;

        if (item.getItemId() == R.id.profil) {
            fragmentClass = ProfilFragment.class;
        } else {
            fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        assert fragment != null;
        fragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit();
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public void setBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setOnItemSelectedListener(item -> {
            Fragment frag =null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    frag = new HomeFragment();
                    break;
                case R.id.navigation_contact:
                    frag = new ContactFragment();
                    break;
                case R.id.navigation_chat:
                    frag = new ConversationsFragment();
                    break;
                case R.id.navigation_forum:
                    frag = new ForumFragment();
                    break;
                case R.id.navigation_menu:
                    frag = new MenuFragment();
                    break;
                default:
            }
            assert frag != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, frag).commit();
            return true;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,new HomeFragment()).commit();
    }

    @Override
    public void onButtonClicked(View view, Bundle bundle) {
        Log.e(getClass().getSimpleName(),"Button clicked !");
        Activity activity = this;
        Intent intent = new Intent(activity, DetailsNewsActivity.class);
        intent.putExtras(bundle);
        Bundle options = ActivityOptionsCompat.makeClipRevealAnimation(
                view, 0, 0, view.getWidth(), view.getHeight()).toBundle();
        ActivityCompat.startActivity(this, intent, options);
    }

}