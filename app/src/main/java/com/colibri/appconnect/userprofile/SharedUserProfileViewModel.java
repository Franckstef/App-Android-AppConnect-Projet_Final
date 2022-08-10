package com.colibri.appconnect.userprofile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.colibri.appconnect.util.Resource;

public class SharedUserProfileViewModel extends ViewModel {


    private final String userId;
    private final String placeholderRepo;
    private final MutableLiveData<Resource<UserProfile>> userProfile = new MutableLiveData<>(new Resource.Loading<>());

    public SharedUserProfileViewModel(String placeholderRepo, String userId)
    {
        super();
        this.userId = userId;
        this.placeholderRepo = placeholderRepo;
        userProfile.postValue(new Resource.Success<>(UserProfile.MockUserProfile()));
    }

    public LiveData<Resource<UserProfile>> getUserProfile(){
        return userProfile;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
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
}

