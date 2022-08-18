package com.colibri.appconnect;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ListAdapter;

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

        setGridViewHeightBasedOnChildren(menu, 2);

        return view;
    }

    private void setGridViewHeightBasedOnChildren(GridView gridView, int noOfColumns) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // adapter is not set yet
            return;
        }

        int totalHeight; //total height to set on grid view
        int items = gridViewAdapter.getCount(); //no. of items in the grid
        int rows; //no. of rows in grid

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x;
        if( items > noOfColumns ){
            x = items/noOfColumns;

            //Check if exact no. of rows of rows are available, if not adding 1 extra row
            if(items%noOfColumns != 0) {
                rows = (int) (x + 1);
            }else {
                rows = (int) (x);
            }
            totalHeight *= rows;

            //Adding any vertical space set on grid view
            totalHeight += gridView.getVerticalSpacing() * rows;
        }

        //Setting height on grid view
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }

}