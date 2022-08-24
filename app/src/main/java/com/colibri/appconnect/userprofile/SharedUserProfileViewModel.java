package com.colibri.appconnect.userprofile;


import android.util.Log;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.colibri.appconnect.R;
import com.colibri.appconnect.ProfileAction;
import com.colibri.appconnect.data.entity.User;
import com.colibri.appconnect.data.repository;
import com.colibri.appconnect.util.QueryStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedUserProfileViewModel extends ViewModel {

    private final ActionButtonAdapter adapter = new ActionButtonAdapter();

    private final String placeholderRepo;
    private View.OnClickListener signOutClickListener = null;

    private ProfileAction phoneClickListener = null;
    private ProfileAction emailClickListener = null;

    private ProfileAction chatClickAction = null;
    private final repository repo = repository.getInstance();
    private final LiveData<QueryStatus<User>> userQuery;
    private final LiveData<Boolean> isLoading;

//    private final MutableLiveData<List<ActionButtonBinding>> actionButtons = new MutableLiveData<>(new ArrayList<>());



    public ActionButtonAdapter getAdapter() {
        return adapter;
    }

    public SharedUserProfileViewModel(String placeholderRepo, String userId)
    {
        super();

        if (userId == null) {
            userQuery = repo.getCurrentUser();
        } else {
            userQuery = repo.getUser(userId);
        }

        this.placeholderRepo = placeholderRepo;
        isLoading = Transformations.map(userQuery, resUserProfile-> (resUserProfile.isLoading()));

        userProfile = Transformations.map(userQuery, resUserProfile->{
                    if (resUserProfile.isSuccessful()){
                        Log.d(TAG, "getUserProfile: " + resUserProfile);
                        adapter.submitList(getActionButtons(resUserProfile.getData()));
                        return new UserProfile(resUserProfile.getData());
                    }
                    return null;
                }
        );
    }



    private final LiveData<UserProfile> userProfile;

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<UserProfile> getUserProfile() {
        return userProfile;
    }

    public void setPhoneClickListener(ProfileAction profileAction){
        phoneClickListener = profileAction;
    }

    public void setEmailClickListener(ProfileAction profileAction) {
        emailClickListener = profileAction;
    }

    public void setSignOutClickListener(View.OnClickListener signOutClickListener) {
        this.signOutClickListener = signOutClickListener;
    }

    public void setChatAction(ProfileAction profileAction) {
        chatClickAction = profileAction;
    }

    private List<ActionButtonBinding> getActionButtons(User user){
        if (user == null) {
            return null;
        }
        List<ActionButtonBinding> buttonList = Arrays.asList(
                getChatActionButton(user),
                getPhoneActionButton(user),
                getEmailActionButton(user),
                getSignOutActionButton(user)
        );

        List<ActionButtonBinding> returnList = new ArrayList<>();

        for (ActionButtonBinding abb :
                buttonList) {
            if (abb != null) {
                returnList.add(abb);
            }
        }
        Log.d(TAG, "getActionButtons: " + returnList);
        return returnList;
    }

    private ActionButtonBinding getSignOutActionButton(User user){
        if(signOutClickListener != null && user.getIsCurrentUser()) {
            ActionButtonBinding abb = new ActionButtonBinding(R.drawable.ic_close, "Se déconnecter");
            abb.setOnClickListener(v->{
                repo.SignOut();
                signOutClickListener.onClick(v);
            });
            abb.setIsHighlighted(true);
            return  abb;
        }
        return null;
    }

    private ActionButtonBinding getChatActionButton(User user){
        if(user.getIsCurrentUser() && chatClickAction != null){
            return null;
        }
        ActionButtonBinding chatButton = new ActionButtonBinding(R.drawable.ic_message, "Envoyer un message");

        chatButton.setOnClickListener(view -> {
            chatClickAction.execute(user.getId());
        });
        chatButton.setIsHighlighted(true);
        return chatButton;
    }
    
    private ActionButtonBinding getPhoneActionButton(User user){
        String primaryPhone = user.getPrimaryPhone();
        return getContactActionButton(
                R.drawable.ic_phone,
                primaryPhone,
                user,
                phoneClickListener,
                primaryPhone
        );
    }

    private ActionButtonBinding getEmailActionButton(User user){
        String primaryEmail = user.getPrimaryEmail();
        return getContactActionButton(
                R.drawable.ic_email,
                primaryEmail,
                user,
                emailClickListener,
                primaryEmail
        );
    }

    private ActionButtonBinding getContactActionButton(
            @DrawableRes int icon,
            String label,
            User user,
            ProfileAction action,
            String actionParameter)
    {
        if(label == null) {
            return null;
        }

        ActionButtonBinding abb = new ActionButtonBinding(icon, label);
        if (action != null && !user.getIsCurrentUser()) {
            abb.setIsHighlighted(true);
            abb.setOnClickListener(view -> {
                action.execute(actionParameter);
            });
        }
        return abb;
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

    private static final String TAG = "AP::ProfileViewModel";
}

