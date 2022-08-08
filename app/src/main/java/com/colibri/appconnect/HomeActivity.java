package com.colibri.appconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    int[] images = {R.drawable.tunnel,  R.drawable.structure_verre, R.drawable.collegues, R.drawable.casques, R.drawable.employe};
    private final ArrayList<Integer> ImagesArray = new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, images);
        viewPager.setAdapter(viewPagerAdapter);

        ArrayList<News> list= new ArrayList();
        list.add(new News("camion", "S'approvisionner en produits locaux par l'intermédiaire d'un circuit court", "Face aux problèmes d’approvisionnement provoqués par la COVID-19, Matériaux Direct, un détaillant en matériaux de construction..."));
        list.add(new News("acceptabilite_sociale", "L'importance de mettre en place une démarche d’acceptabilité sociale dans vos projets", "En 1967, une importante infrastructure québécoise est inaugurée : le pont-tunnel Louis-Hippolyte-La Fontaine. Réputée pour sa fiabilité sur le plan de la mécanique et pour sa durabilité..."));
        list.add(new News("pont", "Aluminium : un choix judicieux dans le domaine des ponts et des passerelles", "Plus de 70 ans après la construction du premier pont routier en aluminium au Québec, ce métal peine toujours à s’imposer au sein de ces grandes structures. Or, la situation serait sur le point de changer..."));
        list.add(new News("camion", "S'approvisionner en produits locaux par l'intermédiaire d'un circuit court", "Face aux problèmes d’approvisionnement provoqués par la COVID-19, Matériaux Direct, un détaillant en matériaux de construction..."));
        list.add(new News("acceptabilite_sociale", "L'importance de mettre en place une démarche d’acceptabilité sociale dans vos projets", "En 1967, une importante infrastructure québécoise est inaugurée : le pont-tunnel Louis-Hippolyte-La Fontaine. Réputée pour sa fiabilité sur le plan de la mécanique et pour sa durabilité..."));
        list.add(new News("pont", "Aluminium : un choix judicieux dans le domaine des ponts et des passerelles", "Plus de 70 ans après la construction du premier pont routier en aluminium au Québec, ce métal peine toujours à s’imposer au sein de ces grandes structures. Or, la situation serait sur le point de changer..."));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        NewsFeedAdapter adapter = new NewsFeedAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        init();
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    private void init() {

        for (int image : images) ImagesArray.add(image);
        NUM_PAGES =images.length;

        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

    }

}