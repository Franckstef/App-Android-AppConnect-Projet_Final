package com.colibri.appconnect.userprofile;

import android.graphics.drawable.Drawable;

public class ActionButtonBinding {
    private Drawable icon;
    private String label;
    private Boolean isHighlighted = true;

    public ActionButtonBinding(Drawable icon, String label){
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public Boolean getHighlighted() {
        return isHighlighted;
    }
}
