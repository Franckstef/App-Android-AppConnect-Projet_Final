package com.colibri.appconnect.userprofile;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.colibri.appconnect.data.repository;

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

    public void onClick(View view){
        repository.getInstance().SignOut();
    }
}
