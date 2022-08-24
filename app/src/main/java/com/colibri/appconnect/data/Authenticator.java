package com.colibri.appconnect.data;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.colibri.appconnect.R;
import com.colibri.appconnect.util.QueryStatus;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.firebase.ui.auth.data.model.User;

import java.util.Arrays;
import java.util.List;


public class Authenticator {
    private final MutableLiveData<QueryStatus<Boolean>> authStatus = new MutableLiveData<>(new QueryStatus.Loading<>());
    private final ActivityResultLauncher<Intent> signInLauncher;

    public Authenticator(AppCompatActivity callerActivity){
        signInLauncher = callerActivity.registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                result -> OnAuthResult(result,callerActivity)
        );
    }

    private void OnAuthResult(FirebaseAuthUIAuthenticationResult result, AppCompatActivity callerActivity){
        IdpResponse idpResponse = result.getIdpResponse();

        if(result.getResultCode() == RESULT_OK){
            Log.d(TAG, "OnAuthResult: RESULT_OK");
            if (idpResponse != null) {
                authStatus.postValue(new QueryStatus.Success<>(idpResponse.isNewUser()));
            }

        } else {
            if(idpResponse == null){
                Log.d(TAG, "OnAuthResult: Error: User Cancel");
                authStatus.postValue(
                        new QueryStatus.Error<>(
                                callerActivity.getString(R.string.AuthError_UserCancel)
                        )
                );
            }
            else if(idpResponse.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                Log.d(TAG, "OnAuthResult: Error: No Network");
                authStatus.postValue(
                        new QueryStatus.Error<>(
                                callerActivity.getString(R.string.AuthError_NoNetwork)
                        )
                );
            }
            else {
                Log.d(TAG, "OnAuthResult: Unknow Error");
                authStatus.postValue(
                        new QueryStatus.Error<>(
                                callerActivity.getString(R.string.AuthError_Unknow)
                        )
                );
            }
        }
    }

    LiveData<QueryStatus<Boolean>> signIn(){
        authStatus.setValue(new QueryStatus.Loading<>());
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.logo)
                .build();
        signInLauncher.launch(signInIntent);
        return authStatus;
    }

    private static final String TAG = "AP::Authenticator";
}
