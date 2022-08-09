package com.colibri.appconnect;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable{

    private String image;
    private String titre;
    private String date;

    public News(String image, String titre, String description) {
        this.image = image;
        this.titre = titre;
        this.date = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitre() { return titre; }

    public void setTitre(String titre) { this.titre = titre; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private News(Parcel in) {
        image = in.readString();
        titre = in.readString();
        date = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(image);
        out.writeString(titre);
        out.writeString(date);
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }
        public News[] newArray(int size) { return new News[size]; }
    };

}
