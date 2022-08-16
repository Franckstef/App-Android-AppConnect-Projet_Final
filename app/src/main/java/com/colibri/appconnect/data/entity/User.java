package com.colibri.appconnect.data.entity;


import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.colibri.appconnect.R;
import com.colibri.appconnect.data.firestore.document.UserDoc;
import com.colibri.appconnect.userprofile.ActionButtonBinding;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.Objects;


public class User {
    private final UserDoc document;
    private final Boolean isCurrentUser;

    public User(UserDoc document, Boolean isCurrentUser) {
        this.document = document;
        this.isCurrentUser = isCurrentUser;
    }

    public String getId(){
        return document.getDocId();
    }

    public DocumentReference getDocumentReference(){
        return document.getDocumentReference();
    }

    public String getDisplayName(){
        return document.getDisplayName();
    }

    public String getAvatar(){
        return document.getAvatar();
    }

    public void setAvatarToImageView(ImageView imageView){
        Picasso.get().load(R.drawable.user).into(imageView);
    }

    public String getPrimaryEmail(){
        return document.getInfoPersonal().getPrimaryEmail();
    }

    public String getPrimaryPhone(){
        return document.getInfoPersonal().getPrimaryPhone();
    }

    public String getTitle(){
        return document.getInfoHr().getTitle();
    }

    public String getLocation(){
        return document.getInfoHr().getLocation();
    }

    public Boolean getIsCurrentUser() {
        return isCurrentUser;
    }

    @NonNull
    @Override
    public String toString() {
        return document.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return document.equals(user.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document);
    }

    public static DiffUtil.ItemCallback<User> diffCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            boolean b = Objects.equals(oldItem.document.getDocId(), newItem.document.getDocId());
            Log.d(TAG, "areItemsTheSame: " + b + " - Old: " + oldItem + " - New: " + newItem);
            return b;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            Log.d(TAG, "areItemsTheSame: " + oldItem.equals(newItem) + " - Old: " + oldItem + " - New: " + newItem);
            return oldItem.equals(newItem);
        }
    };
    private static final String TAG = "AP::Entity.User";
}
