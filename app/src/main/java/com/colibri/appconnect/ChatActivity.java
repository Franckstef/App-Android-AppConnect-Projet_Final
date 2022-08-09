package com.colibri.appconnect;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.colibri.appconnect.databinding.ActivityChatBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    ActivityChatBinding binding;

    CollectionReference colRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ChatAdaptor chatAdaptor = new ChatAdaptor(new ArrayList<>(), null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        binding.rvChatMessageHistory.setLayoutManager(linearLayoutManager);
        binding.rvChatMessageHistory.setAdapter(chatAdaptor);

        colRef = FirebaseFirestore.getInstance().collection("chats").document("zbgMUv6Zd30cZfqU9FF5").collection("messages");


                colRef.whereEqualTo("status", "2").addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    Map<String, Object> newStatus = new HashMap<>();
                    newStatus.put("status", "3");

                    assert value != null;
                    for (QueryDocumentSnapshot doc : value) {
                        if (doc.get("textMessage") != null) {
                            Message m = new Message();
                            m.setTextMessage(doc.getString("textMessage"));
                            chatAdaptor.addMessage(m);
                        }

                        colRef.document(doc.getId()).update(newStatus);
                    }
                });


        binding.btnChatSend.setOnClickListener( view -> {

            Map<String, Object> msg = new HashMap<>();
            msg.put("textMessage", binding.etChatMessage.getText().toString());
            msg.put("status", "2");

            colRef.add(msg).addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
        });
    }

}