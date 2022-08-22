package com.colibri.appconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EvenementsActivity extends AppCompatActivity {

    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenements);

        Toolbar toolbar = findViewById(R.id.toolbarEvenement);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Evenements");
        toolbar.setTitleTextAppearance(this, R.style.toolbar);

        ArrayList<Event> list = new ArrayList<>();
        list.add(new Event("03 sept","golf", "Tournoi annuel de golf !", "5 Rue Riverside, Saint-Lambert, QC J4S 1B7"));
        list.add(new Event("21 sept","barbecue", "Barbecue de fin d'été !", "23, Chemin du bonheur, Montréal, QC H5K 8H6"));
        list.add(new Event("01 oct","velo", "Défi vélo: Soutenez la cause Parkinson !", "560 rue Ontario Est, Montréal, QC H2L 0B6"));
        list.add(new Event("28 oct","escape_game", "Team building: Escape game !", "1744 Rue William #109, Montréal, QC H3J 1R4"));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventAdapter adapter = new EventAdapter(list, this);
        recyclerView.setAdapter(adapter);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}