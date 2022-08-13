package com.colibri.appconnect;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.colibri.appconnect.databinding.FragmentContactBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Objects;


public class ContactFragment extends Fragment {

    private static final String TAG = "ContactFragment";

    FragmentContactBinding binding;

    public ContactFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        OnItemClickListener listener = position -> {
            /*Intent intent = new Intent(container.getContext(), ChatActivity.class);
            User user = ((CustomAdaptor) Objects.requireNonNull(binding.rvConversation.getAdapter())).getUser(position);
            intent.putExtra(ChatActivity.USERTO, user.userId);
            startActivity(intent);*/

            Fragment fr = new ContactDetailsFragment();
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.flFragment, fr);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();
        };



        CollectionReference colRef = FirebaseFirestore.getInstance().collection("users");
        colRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> users;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());

                }
                users = new ArrayList<>(task.getResult().toObjects(User.class));
                CustomAdaptor categoryAdapter = new CustomAdaptor(users, listener);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);

                // in below two lines we are setting layoutmanager and adapter to our recycler view.
                binding.rvConversation.setLayoutManager(linearLayoutManager);
                binding.rvConversation.setAdapter(categoryAdapter);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvConversation.getContext(),
                        linearLayoutManager.getOrientation());
                binding.rvConversation.addItemDecoration(dividerItemDecoration);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });

        return binding.getRoot();
    }
}