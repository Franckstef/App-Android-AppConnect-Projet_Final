package com.colibri.appconnect.data;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.colibri.appconnect.R;
import com.colibri.appconnect.util.QueryStates;
import com.colibri.appconnect.util.QueryStatus;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

enum AuthenticationStatus {
    Unknown,
    Connected,
    Disconnected,
    Authenticating
}

public class Authentication {
    static private Authentication _instance;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final MutableLiveData<AuthenticationStatus> authStatus = new MutableLiveData<>(AuthenticationStatus.Unknown);
    final MutableLiveData<Boolean> isSigningIn =  new MutableLiveData<>(false);
    private final MutableLiveData<FirebaseUser> currentUser = new MutableLiveData<>(null);

    Authentication(){
        Log.d(TAG, "Authentication: ");
        currentUser.setValue(firebaseAuth.getCurrentUser());
        listenToAuthStateChange();

    }

    protected void signout(){
        firebaseAuth.signOut();
    }
    private void listenToAuthStateChange(){
        firebaseAuth.addAuthStateListener(this::onAuthStateChanged);
    }

    private void stopListenToAuthStateChange(){
        firebaseAuth.removeAuthStateListener(this::onAuthStateChanged);
    }

    private void onAuthStateChanged(FirebaseAuth auth){
        Log.d(TAG, "onAuthStateChanged: " +auth.getCurrentUser());
        currentUser.postValue(auth.getCurrentUser());
        if (auth.getCurrentUser() == null) {
            isSigningIn.postValue(false);
        } else {
            isSigningIn.postValue(true);
        }
    }
    protected String getCurrentUserId(){
        FirebaseUser currentUserValue = currentUser.getValue();
        Log.d(TAG, "getCurrentUserId: " + currentUserValue);
        if (currentUserValue != null) {
            return currentUserValue.getUid();
        }
        return null;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    protected LiveData<QueryStatus<String>> getLiveCurrentUserId(){

        return Transformations.map(currentUser, user -> {
            Log.d(TAG, "getLiveCurrentUserId: Transform Map " + user);
            if (user == null) {
                Log.d(TAG, "getLiveCurrentUserId: Transform user == null");
                return new QueryStatus.Error<>("Aucun utilisateur n'est authentifié");
            }
            Log.d(TAG, "getLiveCurrentUserId: Transform Map UserId:" + user.getUid());
            return new QueryStatus.Success<>(user.getUid());
        });

    }

    public Authenticator getAuthenticator(AppCompatActivity callerActivity){
        return new Authenticator(callerActivity);
    }


    public LiveData<QueryStatus<FirebaseUser>> AuthenticateUser(Authenticator authenticator){

        stopListenToAuthStateChange();
        LiveData<QueryStatus<Boolean>> authStatus = authenticator.signIn();
        return Transformations.map(authStatus, result ->{
            if (!result.isLoading()){
                Log.d(TAG, "AuthenticateUser: Auth finished");
                listenToAuthStateChange();
            }

            QueryStates state = result.getState();
            switch (state){
                case Success:
                    Log.d(TAG, "AuthenticateUser: Success");
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    this.currentUser.postValue(currentUser);
                    if(result.getData()){
                        return new QueryStatus.Success<>(currentUser);
                    }
                    return new QueryStatus.Success<>();
                case Error:
                    Log.d(TAG, "AuthenticateUser: Error: " + result.getMessage());
                    return new QueryStatus.Error<>(result.getMessage());

                case Loading:
                    Log.d(TAG, "AuthenticateUser: Loading");
                    return new QueryStatus.Loading<>();

                default:
                    Log.d(TAG, "AuthenticateUser: Unknown Error");
                    return new QueryStatus.Error<FirebaseUser>("Unknown Error");
            }
        });


    }

    @Override
    protected void finalize() {
        stopListenToAuthStateChange();
    }



    static public Authentication getInstance(){
        if (_instance == null) {
            _instance = new Authentication();
        }
        return _instance;
    }

    private static final String TAG = "AP::Authentication";
}
