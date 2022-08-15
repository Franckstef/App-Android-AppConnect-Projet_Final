package com.colibri.appconnect.userprofile;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.colibri.appconnect.data.entity.User;
import com.colibri.appconnect.data.repository;
import com.colibri.appconnect.util.QueryStates;
import com.colibri.appconnect.util.QueryStatus;

public class SharedUserProfileViewModel extends ViewModel {


    private final String userId;
    private final String placeholderRepo;
    private final repository repo = repository.getInstance();
//    private final MutableLiveData<QueryStatus<UserProfile>> userProfile = new MutableLiveData<>(new QueryStatus.Loading<>());
    private final LiveData<QueryStatus<User>> userProfile = repo.getCurrentUser();


    public SharedUserProfileViewModel(String placeholderRepo, String userId)
    {
        super();
        this.userId = userId;
        this.placeholderRepo = placeholderRepo;

    }

    public LiveData<Boolean> getIsLoading(){
        return Transformations.map(userProfile, resUserProfile->{
            return (resUserProfile instanceof QueryStatus.Loading);
        });
    }

    public LiveData<UserProfile> getUserProfile(){
        return Transformations.map(userProfile, resUserProfile->{
                    if (resUserProfile.isSuccessful()){
                        Log.d(TAG, "getUserProfile: " + resUserProfile);
                        return new UserProfile(resUserProfile.getData());
                    }
                    return null;
                }
                );
    }

    public static class Factory implements ViewModelProvider.Factory{
        private final String mPlaceholderRepo;
        private final String mUserId;

        public Factory(String placeholderRepo, String userId){
            mPlaceholderRepo = placeholderRepo;
            mUserId = userId;
        }
        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SharedUserProfileViewModel(mPlaceholderRepo, mUserId);
        }
    }

    private static final String TAG = "ProfileViewModel";
}

