package com.colibri.appconnect.data.entity;


import android.widget.ImageView;

import com.colibri.appconnect.R;
import com.colibri.appconnect.data.firestore.document.UserDoc;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;


public class User {
    private UserDoc document;

    public User(UserDoc document) {
        this.document = document;
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

    @Override
    public String toString() {
        return document.toString();
    }
}
