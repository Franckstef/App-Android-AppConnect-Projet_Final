package com.colibri.appconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.colibri.appconnect.databinding.FragmentChatBinding;

public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";
    FragmentChatBinding binding;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    //        FirebaseFirestore.getInstance().collection("users").orderBy("attrib").limit(1)
    //        .addSnapshotListener((value, e) -> {
    //            if (e != null) {
    //                Log.w(TAG, "listen:error", e);
    //                return;
    //            }
    //
    //            assert value != null;
    //            for (DocumentChange dc : value.getDocumentChanges()) {
    //                switch (dc.getType()) {
    //                    case ADDED:
    //                        Log.d(TAG, "New city: " + dc.getDocument().getData());
    //                        break;
    //                    case MODIFIED:
    //                        Log.d(TAG, "Modified city: " + dc.getDocument().getData());
    //                        break;
    //                    case REMOVED:
    //                        Log.d(TAG, "Removed city: " + dc.getDocument().getData());
    //                        break;
    //                }
    //            }
    //        });



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_chat, container, false);
        return binding.getRoot();
    }
}