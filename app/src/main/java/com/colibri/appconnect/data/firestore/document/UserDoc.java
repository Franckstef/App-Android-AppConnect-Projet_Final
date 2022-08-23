package com.colibri.appconnect.data.firestore.document;

import com.colibri.appconnect.data.GenerateRandomData;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.PropertyName;

import java.util.Objects;

public class UserDoc extends FirestoreDocument{

    private String displayName;
    private String avatar;
    private UserDocInfoPersonal infoPersonal;
    private UserDocInfoHr infoHr;
    private Timestamp createdOn;
    private Timestamp lastConnection;

    UserDoc(){}


    UserDoc(DocumentSnapshot snapshot) {
        super(snapshot);
        if(snapshot.exists()){
            final UserDoc other = snapshot.toObject(UserDoc.class);
            this.displayName = other.getDisplayName();
            this.avatar = other.getAvatar();
            this.infoPersonal = other.getInfoPersonal();
            this.infoHr = other.getInfoHr();
            this.createdOn = other.getCreatedOn();
            this.lastConnection = other.getLastConnection();
        } else {
            this.displayName = GenerateRandomData.getFakeName();
            this.avatar = GenerateRandomData.getFakeAvatar();
            this.infoPersonal = GenerateRandomData.getFakeInfoPersonal();
            this.infoHr = GenerateRandomData.getFakeHrInfo();
            this.createdOn = Timestamp.now();
            this.lastConnection = Timestamp.now();
        }
    }

    public UserDoc(String displayName, String avatar, UserDocInfoPersonal infoPersonal, UserDocInfoHr infoHr) {
        this.displayName = displayName;
        this.avatar = avatar;
        this.infoPersonal = infoPersonal;
        this.infoHr = infoHr;
        this.createdOn = Timestamp.now();
        this.lastConnection = Timestamp.now();
    }

    public UserDoc(String displayName, String primaryEmail, String primaryPhone) {
        this.displayName = displayName;
        this.infoPersonal = new UserDocInfoPersonal(primaryEmail,primaryPhone);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @PropertyName("info_personal")
    public UserDocInfoPersonal getInfoPersonal() {
        return infoPersonal;
    }
    @PropertyName("info_personal")
    public void setInfoPersonal(UserDocInfoPersonal infoPersonal) {
        this.infoPersonal = infoPersonal;
    }

    @PropertyName("info_hr")
    public UserDocInfoHr getInfoHr() {
        return infoHr;
    }
    @PropertyName("info_hr")
    public void setInfoHr(UserDocInfoHr infoHr) {
        this.infoHr = infoHr;
    }

    @PropertyName("created_on")
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    @PropertyName("created_on")
    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @PropertyName("last_connection")
    public Timestamp getLastConnection() {
        return lastConnection;
    }

    @PropertyName("last_connection")
    public void setLastConnection(Timestamp lastConnection) {
        this.lastConnection = lastConnection;
    }

    @Override
    public String toString() {
        return "UserDoc{" +
                "docId='" + getDocId() + '\'' +
                ", name='" + displayName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDoc userDoc = (UserDoc) o;
        return Objects.equals(displayName, userDoc.displayName) && Objects.equals(avatar, userDoc.avatar) && Objects.equals(infoPersonal, userDoc.infoPersonal) && Objects.equals(infoHr, userDoc.infoHr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), displayName, avatar, infoPersonal, infoHr);
    }
}
