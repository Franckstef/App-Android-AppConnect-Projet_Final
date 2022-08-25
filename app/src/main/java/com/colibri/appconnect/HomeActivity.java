package com.colibri.appconnect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.colibri.appconnect.contactList.ContactFragment;
import com.colibri.appconnect.data.Authenticator;
import com.colibri.appconnect.data.entity.ChatRoom;
import com.colibri.appconnect.data.firestore.document.MessageDoc;
import com.colibri.appconnect.data.repository;
import com.colibri.appconnect.databinding.ActivityHomeBinding;
import com.colibri.appconnect.userprofile.ProfilFragment;
import com.colibri.appconnect.userprofile.ProfileActivity;
import com.colibri.appconnect.util.QueryStates;
import com.colibri.appconnect.util.QueryStatus;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnButtonClickedListener {

    ActivityHomeBinding binding;
    repository repo;
    Authenticator authenticator;
    Fragment frag = null;

    private void TestChatRoom(){
        String cr = "safsef";
        repo.getChatroom(cr).observe(this,rep->{
            Log.d(TAG, "TestChatRoom: " + rep);
            final ChatRoom data = rep.getData();
            if (data != null) {
                data.setName(Timestamp.now().toString());
                data.pushToFirebase().observe(this, val->{
                    Log.d(TAG, "TestChatRoom: " + val);
                });
            }
        });
    }

    private void onListChatQueryChanged(QueryStatus<List<ChatRoom>> listQueryStatus) {
        Log.d(TAG, "TestChatRoom: " + listQueryStatus);
        if (listQueryStatus.isSuccessful()) {

            for (ChatRoom room :
                    listQueryStatus.getData()) {
                room.getLiveMessages().observe(this,this::OnChatroomMessageChanged);
            }
        }
    }

    private void OnChatroomMessageChanged(QueryStatus<List<MessageDoc>> listMessageQuery){

            Log.d(TAG, "OnChatroomMessageChanged: "+listMessageQuery);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = repository.getInstance();
        authenticator = repo.getAuthenticator(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        repo.isSignIn().observe(this, isSignIn -> binding.setIsUserConnected(isSignIn));

        binding.connectionScreen.setIsAuthenticating(false);
        binding.connectionScreen.buttonConnection.setOnClickListener(
                v-> Authenticate());


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.toolbar);

        setBottomNavigation();

//        TestChatRoom();
    }



    private void Authenticate(){
        repo.AuthenticateUser(authenticator)
                .observe(this, this::onAuthResult);
    }

    private void onAuthResult(QueryStatus<Void> authResult){
        QueryStates state = authResult.getState();
        switch (state){
            case Success:
                Log.d(TAG, "onAuthResult: Success");
                Toast.makeText(this, "Connection Réussi", Toast.LENGTH_SHORT).show();
                binding.connectionScreen.setIsAuthenticating(false);
                break;
            case Error:
                Log.d(TAG, "onAuthResult: Error: " + authResult.getMessage());
                Toast.makeText(this, authResult.getMessage(), Toast.LENGTH_LONG).show();
                binding.connectionScreen.setIsAuthenticating(false);
                break;
            case Loading:
                Log.d(TAG, "onAuthResult: Loading");
                binding.connectionScreen.setIsAuthenticating(true);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.profil) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @SuppressLint("NonConstantResourceId")
    public void setBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setOnItemSelectedListener(item -> {

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
                /*case R.id.navigation_forum:
                    frag = new ForumFragment();
                    break;*/
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

    private static final String TAG = "AP::HomeActivity";

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.flFragment);

        if (f instanceof MenuFragment || f instanceof ContactFragment || f instanceof ConversationsFragment ) {
            frag = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, frag).commit();
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_alert)
                    .setTitle("Fermer l'application ?")
                    .setMessage("Êtes-vous sur de vouloir quitter l'application?")
                    .setPositiveButton("Oui", (dialog, which) -> finish())
                    .setNegativeButton("Non", null)
                    .show();
        }
    }
}