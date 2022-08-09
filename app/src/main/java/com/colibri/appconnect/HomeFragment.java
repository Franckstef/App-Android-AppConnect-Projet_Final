package com.colibri.appconnect;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements NewsFeedAdapter.OnItemClicked {

    public HomeFragment() {}

    private ViewPager viewPager;
    int[] images = {R.drawable.tunnel,  R.drawable.structure_verre, R.drawable.collegues, R.drawable.casques, R.drawable.employe, R.drawable.stop, R.drawable.ouvriers, R.drawable.poutres};
    private final ArrayList<Integer> ImagesArray = new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private View view;
    private OnButtonClickedListener mCallback;
    HomeActivity homeActivity;

    public interface OnButtonClickedListener {
        void onButtonClicked(View view);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        /*viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(homeActivity, images);
        viewPager.setAdapter(viewPagerAdapter);*/

        ArrayList<News> list= new ArrayList();
        list.add(new News("camion", "S'approvisionner en produits locaux par l'intermédiaire d'un circuit court.", "08/08/22"));
        list.add(new News("acceptabilite_sociale", "L'importance de mettre en place une démarche d’acceptabilité sociale dans vos projets.", "08/08/22"));
        list.add(new News("pont", "Aluminium : un choix judicieux dans le domaine des ponts et des passerelles.", "08/08/22"));
        list.add(new News("camion", "S'approvisionner en produits locaux par l'intermédiaire d'un circuit court.", "08/08/22"));
        list.add(new News("acceptabilite_sociale", "L'importance de mettre en place une démarche d’acceptabilité sociale dans vos projets.", "08/08/22"));
        list.add(new News("pont", "Aluminium : un choix judicieux dans le domaine des ponts et des passerelles.", "08/08/22"));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        NewsFeedAdapter adapter = new NewsFeedAdapter(list, homeActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(homeActivity));
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);

        //init();

        return view;
    }

    @Override
    public void onItemClick(News news) {
        mCallback.onButtonClicked(view);
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        bundle.putString("titre", news.getTitre());
        bundle.putString("image", news.getImage());
        intent.putExtras(bundle);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }


    private void createCallbackToParentActivity(){
        try {
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e + " must implement OnButtonClickedListener");
        }
    }
}