package com.colibri.appconnect.userprofile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SharedUserProfileViewModel extends ViewModel {

    public SharedUserProfileViewModel(){
        super();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        public Factory(){

        }
        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SharedUserProfileViewModel();
        }
    }
}

