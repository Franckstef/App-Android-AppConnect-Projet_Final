package com.colibri.appconnect;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    View view;

    public MenuFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        GridView menu = view.findViewById(R.id.gridMenu);

        ArrayList<MenuItem> list = new ArrayList<>();
        list.add(new MenuItem("Histoire", R.drawable.ic_histoire));
        list.add(new MenuItem("Ressources", R.drawable.ic_ressources24));
        list.add(new MenuItem("Formulaires", R.drawable.ic_document));
        list.add(new MenuItem("Evenements", R.drawable.ic_event));
        list.add(new MenuItem("Réglages", R.drawable.ic_settings));
        list.add(new MenuItem("FAQ", R.drawable.ic_question));

        MenuItemAdapter adapter = new MenuItemAdapter(getContext(), list);
        menu.setAdapter(adapter);

        menu.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    Intent intent4 = new Intent(getContext(), RessourcesActivity.class);
                    startActivity(intent4);
                    break;
                default:
                    break;
            }
        });

        return view;
    }

}