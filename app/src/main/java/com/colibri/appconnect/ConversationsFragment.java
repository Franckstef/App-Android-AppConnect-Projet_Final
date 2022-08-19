package com.colibri.appconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.colibri.appconnect.databinding.FragmentConversationsBinding;

public class ConversationsFragment extends Fragment {

    private static final String TAG = "ConversationFragment";
    private static final String USERID = "kYXSPSJ38rVcp9xjVUAj";
    FragmentConversationsBinding binding;

    public ConversationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Message");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_conversations, container, false);
        OnItemClickListener listener = position -> {
            Intent intent = new Intent(container.getContext(), ChatActivity.class);
            startActivity(intent);
        };

//        CustomAdaptor customAdaptor = new CustomAdaptor(new ArrayList<>(), listener);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
//
//        // in below two lines we are setting layoutmanager and adapter to our recycler view.
//        binding.rvConversation.setLayoutManager(linearLayoutManager);
//        binding.rvConversation.setAdapter(customAdaptor);
//
//
//
//
//
//        FirebaseFirestore.getInstance().collection("users").document(USERID).collection("chats")
//                .orderBy("timestamp", Query.Direction.valueOf("desc"))
//                .limit(1)
//                .addSnapshotListener((value, error) -> {
//                    if (error != null) {
//                        Log.w(TAG, "Listen failed.", error);
//                        return;
//                    }
//
//                    assert value != null;
//                    for (QueryDocumentSnapshot doc : value) {
//                        if (doc.get("chat") != null) {
//                            FirebaseFirestore.getInstance().
//                                    collection("chats")
//                                    .document(doc.getString("chats"))
//                                    .collection("messages")
//                                    .orderBy("timestamp", Query.Direction.valueOf("desc"))
//                                    .limit(1)
//                                    .get().addOnCompleteListener(task -> {
//                                        if (task.isSuccessful()) {
//                                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                                Message m = document.toObject(Message.class);
//
//                                                customAdaptor.addConversation(doc.getString(""))
//                                            }
//                                        } else {
//                                            Log.d(TAG, "Error getting documents: ", task.getException());
//                                        }
//                                    });
//
//                        }
//                    }
//
//                });




//        CollectionReference colRef = FirebaseFirestore.getInstance().collection("users");
//        colRef.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                ArrayList<User> users;
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Log.d(TAG, document.getId() + " => " + document.getData());
//
//                }
//                users = new ArrayList<>(task.getResult().toObjects(User.class));
//                CustomAdaptor categoryAdapter = new CustomAdaptor(users, listener);
//
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
//
//                // in below two lines we are setting layoutmanager and adapter to our recycler view.
//                binding.rvConversation.setLayoutManager(linearLayoutManager);
//                binding.rvConversation.setAdapter(categoryAdapter);
//            } else {
//                Log.d(TAG, "Error getting documents: ", task.getException());
//            }
//        });





        return binding.getRoot();
    }
}