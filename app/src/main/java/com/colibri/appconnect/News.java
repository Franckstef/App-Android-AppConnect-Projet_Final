package com.colibri.appconnect;

public class News {

    private String image;
    private String titre;
    private String description;

    public News(String image, String titre, String description) {
        this.image = image;
        this.titre = titre;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitre() { return titre; }

    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
