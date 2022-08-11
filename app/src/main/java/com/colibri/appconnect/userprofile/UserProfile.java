package com.colibri.appconnect.userprofile;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.util.Objects;

public class UserProfile implements Parcelable {

    private String userId;
    private String displayName;
    private String primaryEmail;
    private String primaryPhoneNumber;
    private String avatar;
    private String title;
    private String location;

    public final static Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        public UserProfile[] newArray(int size) {
            return (new UserProfile[size]);
        }

    };

    protected UserProfile(@NonNull Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.displayName = ((String) in.readValue((String.class.getClassLoader())));
        this.primaryEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.primaryPhoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.avatar = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
    }

    public static UserProfile MockUserProfile(){
        return new UserProfile(
                "-1",
                "George Laraque",
                "george@laraque.com",
                "(514) 555-5555",
                "",
                "Contre-Maitre",
                "Chantier du CHUM"
        );
    }

    // TODO: 2022-08-10 Implement Constructor with UserDocument

    /**
     * No args constructor for use in serialization
     *
     */
    public UserProfile() {
    }

    public UserProfile(String userId,String displayName, String primaryEmail, String primaryPhoneNumber, String avatar, String title, String location) {
        super();
        this.userId = userId;
        this.displayName = displayName;
        this.primaryEmail = primaryEmail;
        this.primaryPhoneNumber = primaryPhoneNumber;
        this.avatar = avatar;
        this.title = title;
        this.location = location;
    }

    public UserProfile(UserProfile another){
        this.userId = another.getUserId();
        this.displayName = another.getDisplayName();
        this.primaryEmail = another.getPrimaryEmail();
        this.primaryPhoneNumber = another.getPrimaryPhoneNumber();
        this.avatar = another.getAvatar();
        this.title = another.getTitle();
        this.location = another.getLocation();
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserProfile withDisplayName(String displayName) {
        UserProfile newUserProfile = new UserProfile(this);
        newUserProfile.setDisplayName(displayName);
        return newUserProfile;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        // TODO: 2022-08-10 Validate Email
        this.primaryEmail = primaryEmail;
    }

    public UserProfile withPrimaryEmail(String primaryEmail) {
        UserProfile newUserProfile = new UserProfile(this);
        newUserProfile.setPrimaryEmail(primaryEmail);
        return newUserProfile;
    }
    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public UserProfile withPrimaryPhoneNumber(String primaryPhoneNumber){
        UserProfile newUserProfile = new UserProfile(this);
        newUserProfile.setPrimaryPhoneNumber(primaryPhoneNumber);
        return newUserProfile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserProfile withAvatar(String avatar){
        UserProfile newUserProfile = new UserProfile(this);
        newUserProfile.setAvatar(avatar);
        return newUserProfile;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserProfile withTitle(String title){
        UserProfile newUserProfile = new UserProfile(this);
        newUserProfile.setTitle(title);
        return newUserProfile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserProfile withLocation(String location){
        UserProfile newUserProfile = new UserProfile(this);
        newUserProfile.setLocation(location);
        return newUserProfile;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(UserProfile.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("displayName");
        sb.append('=');
        sb.append(((this.displayName == null)?"<null>":this.displayName));
        sb.append(',');
        sb.append("primaryEmail");
        sb.append('=');
        sb.append(((this.primaryEmail == null)?"<null>":this.primaryEmail));
        sb.append(',');
        sb.append("primaryPhoneNumber");
        sb.append('=');
        sb.append(((this.primaryPhoneNumber == null)?"<null>":this.primaryPhoneNumber));
        sb.append(',');
        sb.append("avatar");
        sb.append('=');
        sb.append(((this.avatar == null)?"<null>":this.avatar));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.userId == null)? 0 :this.userId.hashCode()));
        result = ((result* 31)+((this.displayName == null)? 0 :this.displayName.hashCode()));
        result = ((result* 31)+((this.primaryPhoneNumber == null)? 0 :this.primaryPhoneNumber.hashCode()));
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        result = ((result* 31)+((this.avatar == null)? 0 :this.avatar.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.primaryEmail == null)? 0 :this.primaryEmail.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserProfile)) {
            return false;
        }
        UserProfile rhs = ((UserProfile) other);
        return  Objects.equals(this.userId, rhs.userId)
                && Objects.equals(this.displayName, rhs.displayName)
                && Objects.equals(this.primaryPhoneNumber, rhs.primaryPhoneNumber)
                && Objects.equals(this.location, rhs.location)
                && Objects.equals(this.avatar, rhs.avatar)
                && Objects.equals(this.title, rhs.title)
                && Objects.equals(this.primaryEmail, rhs.primaryEmail);
    }
    //For Parcel
    @Override
    public int describeContents() {
        return 0;
    }

    //For Parcel
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flag) {
        dest.writeValue(userId);
        dest.writeValue(displayName);
        dest.writeValue(primaryEmail);
        dest.writeValue(primaryPhoneNumber);
        dest.writeValue(avatar);
        dest.writeValue(title);
        dest.writeValue(location);
    }
}
