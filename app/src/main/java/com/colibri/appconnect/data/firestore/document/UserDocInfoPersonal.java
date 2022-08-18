package com.colibri.appconnect.data.firestore.document;

import com.google.firebase.firestore.PropertyName;

public class UserDocInfoPersonal {
    private String PrimaryEmail;
    private String PrimaryPhone;

    UserDocInfoPersonal() {
    }

    public UserDocInfoPersonal(String primaryEmail, String primaryPhone) {
        PrimaryEmail = primaryEmail;
        PrimaryPhone = primaryPhone;
    }

    @PropertyName("primary_email")
    public String getPrimaryEmail() {
        return PrimaryEmail;
    }

    @PropertyName("primary_email")
    public void setPrimaryEmail(String primaryEmail) {
        PrimaryEmail = primaryEmail;
    }

    @PropertyName("primary_phone_number")
    public String getPrimaryPhone() {
        return PrimaryPhone;
    }

    @PropertyName("primary_phone_number")
    public void setPrimaryPhone(String primaryPhone) {
        PrimaryPhone = primaryPhone;
    }

    @Override
    public String toString() {
        return "InfoPersonal{" +
                "PrimaryEmail='" + PrimaryEmail + '\'' +
                ", PrimaryPhone='" + PrimaryPhone + '\'' +
                '}';
    }
}
