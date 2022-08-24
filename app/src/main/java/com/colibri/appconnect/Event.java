package com.colibri.appconnect;

public class Event {

    private String image;
    private String titre;
    private String date;
    private String lieux;

    public Event(String date, String image, String titre, String lieux) {
        this.date = date;
        this.image = image;
        this.titre = titre;
        this.lieux = lieux;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLieux() {
        return lieux;
    }

    public void setLieux(String lieux) {
        this.lieux = lieux;
    }
}
