package com.colibri.appconnect.util;







import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.colibri.appconnect.R;

import com.colibri.appconnect.userprofile.ActionButtonBinding;
import com.squareup.picasso.Picasso;


public class BindAdapter {
    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean visible) {
        if(visible){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("android:userPicture")
    public static void setUserPicture(ImageView view, String url){
        if (url != null && !url.isEmpty())
            Picasso.get().load(url).placeholder(R.drawable.user_placeholder).error(R.drawable.user).into(view);
    }



}


