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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.data.repository;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements NewsFeedAdapter.OnItemClicked {

    public HomeFragment() {}

    ViewPager viewPager;
    int[] images = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5, R.drawable.img_6, R.drawable.img_7, R.drawable.img_8, R.drawable.img_9, R.drawable.img_10, R.drawable.img_11, R.drawable.img_12};
    private final ArrayList<Integer> ImagesArray = new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private View view;
    private OnButtonClickedListener mCallback;
    HomeActivity homeActivity;

    public interface OnButtonClickedListener {
        void onButtonClicked(View view, Bundle bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("AppConnect");
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), images);
        viewPager.setAdapter(viewPagerAdapter);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(homeActivity));

        repository.getInstance().getNewsFeed().observe(getViewLifecycleOwner(), listQueryStatus -> {
            if(listQueryStatus.isSuccessful()){
                ArrayList<News> list= new ArrayList(listQueryStatus.getData());

                NewsFeedAdapter adapter = new NewsFeedAdapter(list, homeActivity);
                recyclerView.setAdapter(adapter);
                adapter.setOnClick(this);
            }
        });


        init();

        return view;
    }

    @Override
    public void onItemClick(News news) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        bundle.putString("image", news.getImage());
        bundle.putString("titre", news.getTitre());
        bundle.putString("intro", news.getIntro());
        bundle.putString("article", news.getArticle());
        bundle.putString("date", news.getDate());
        intent.putExtras(bundle);
        mCallback.onButtonClicked(view, bundle);
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