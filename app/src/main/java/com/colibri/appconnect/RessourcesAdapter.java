package com.colibri.appconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RessourcesAdapter extends ArrayAdapter<MenuItem> {
    public RessourcesAdapter(Context context, ArrayList<MenuItem> list) {
        super(context, 0, list);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView;
        listitemView = LayoutInflater.from(getContext()).inflate(R.layout.ressources_item, parent, false);
        MenuItem item = getItem(position);
        TextView titre = listitemView.findViewById(R.id.textTitre);
        ImageView image = listitemView.findViewById(R.id.imageRessources);
        image.setClipToOutline(true);
        titre.setText(item.getName());
        image.setImageResource(item.getImg());

        return listitemView;
    }
}
