package com.colibri.appconnect.userprofile;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.colibri.appconnect.data.repository;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ActionButtonBinding implements View.OnClickListener {
    @DrawableRes private int icon;
    private String label;
    private Boolean isHighlighted = false;
    private View.OnClickListener onClickListener;
    public ActionButtonBinding(@DrawableRes int resId, String label){
        this.label = label;
        this.icon = resId;
    }

    @Override
    public String toString() {
        return "ActionButtonBinding{" +
                "icon=" + icon +
                ", label='" + label + '\'' +
                ", isHighlighted=" + isHighlighted +
                ", onClickListener=" + onClickListener +
                '}';
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public String getLabel() {
        return label;
    }

    public int getIcon() {
        return icon;
    }

    public void setIsHighlighted(Boolean highlighted) {
        isHighlighted = highlighted;
    }

    public Boolean getIsHighlighted() {
        return isHighlighted;
    }

    public void onClick(View view){
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    @BindingAdapter("android:cardBackgroundColor")
    public static void setCardBackgroundColor(CardView view, ActionButtonBinding abb) {
        Log.d(TAG, "setCardBackgroundColor: " + abb);
        if (abb == null) {
            return;
        }
        if(abb.getIsHighlighted()){
            Log.d(TAG, "setCardBackgroundColor: abb: Hightlight" + abb.label);
            view.setCardBackgroundColor(view.getContext().getResources()
                    .getColor(com.google.android.material.R.color.design_default_color_secondary));
        } else {
            view.setCardBackgroundColor(view.getContext().getResources()
                    .getColor(com.google.android.material.R.color.design_default_color_surface));
        }
    }

    @BindingAdapter("android:actionIcon")
    public static void setActionIcon(ImageView view, ActionButtonBinding abb) {
        Log.d(TAG, "setImageDrawable: " + abb);
        if (abb == null) {
            return;
        }
        view.setImageResource(abb.icon);
//        Picasso.get().load(abb.icon).into(view);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionButtonBinding that = (ActionButtonBinding) o;
        return icon == that.icon && label.equals(that.label) && isHighlighted.equals(that.isHighlighted) && Objects.equals(onClickListener, that.onClickListener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon, label, isHighlighted, onClickListener);
    }

    static DiffUtil.ItemCallback<ActionButtonBinding> diffCallback = new DiffUtil.ItemCallback<ActionButtonBinding>() {
        @Override
        public boolean areItemsTheSame(@NonNull ActionButtonBinding oldItem, @NonNull ActionButtonBinding newItem) {
            boolean b = Objects.equals(oldItem.icon, newItem.icon) &&
                    Objects.equals(oldItem.label, newItem.label);
            Log.d(TAG, "areItemsTheSame: " + b + " - Old: " + oldItem + " - New: " + newItem);
            return b;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ActionButtonBinding oldItem, @NonNull ActionButtonBinding newItem) {
            Log.d(TAG, "areItemsTheSame: " + oldItem.equals(newItem) + " - Old: " + oldItem + " - New: " + newItem);
            return oldItem.equals(newItem);
        }
    };
    private static final String TAG = "AP::ActionButtonBinding";
}


