package com.colibri.appconnect;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.colibri.appconnect.contactList.ContactListAdapter;
import com.colibri.appconnect.data.entity.ChatRoom;
import com.colibri.appconnect.data.entity.User;
import com.colibri.appconnect.data.firestore.document.ChatDoc;
import com.colibri.appconnect.data.firestore.document.MessageDoc;
import com.colibri.appconnect.data.repository;
import com.colibri.appconnect.databinding.ActivityChatBinding;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    public static final String CHATROOMID = "4O4t9hpFwDRyonsEGO4JJo8pfZI3_9D1GRUQrxhaZlPGI15N1UVQ1WyB2";
    public static final String USERTO = "userTo";
    private static final String TAG = "ChatActivity";
    private final String USERID = "9D1GRUQrxhaZlPGI15N1UVQ1WyB2";


    private ActivityChatBinding binding;
    private CollectionReference colRef;

    private Timestamp timestamp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!getIntent().hasExtra(USERTO)) {
            finish();
        }

        ChatRoomListAdapter chatRoomListAdapter = new ChatRoomListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvChatMessageHistory.setLayoutManager(linearLayoutManager);
        binding.rvChatMessageHistory.setAdapter(chatRoomListAdapter);

        checkIfExistChatRoom();
        binding.btnChatSend.setOnClickListener(v -> {
            Log.d(TAG, "onCreate: clicker sur send : " +binding.etChatMessage.getText().toString());
            LiveData<QueryStatus<ChatRoom>> chatroom = repository.getInstance().getChatroom(CHATROOMID);
            chatroom.observe(this, chatRoomQueryStatus -> {
                if (chatRoomQueryStatus.isSuccessful()) {
                    ChatRoom room = chatRoomQueryStatus.getData();
                    room.sendMessage(new MessageDoc(binding.etChatMessage.getText().toString(), "userToId"));
                }
            });

        });
    }

    private void setAdapter(String chatRoomid) {
        LiveData<QueryStatus<ChatRoom>> chatroom = repository.getInstance().getChatroom(chatRoomid);
        chatroom.observe(this, chatRoomQueryStatus -> {
            if (chatRoomQueryStatus.isSuccessful()) {
                chatRoomQueryStatus.getData().getLiveMessages().observe(this, messages -> {
                    if (messages.isSuccessful()) {
                        ((ChatRoomListAdapter) binding.rvChatMessageHistory.getAdapter()).submitList(Objects.requireNonNull(messages.getData()));
                    }
                });
//                chatRoomListAdapter.submitList(Objects.requireNonNull(chatRoomQueryStatus.getData().getLiveMessages());
            }
        });
    }

    private void checkIfExistChatRoom() {
        LiveData<QueryStatus<List<ChatRoom>>> chatRoomList = repository.getInstance().getChatroomList();
        chatRoomList.observe(this, chatRoomListQueryStatus -> {
            if (chatRoomListQueryStatus.isSuccessful()){
                String chatRoomId = buildChatChannel(getIntent().getStringExtra(USERTO));
//                if (!chatRoomListQueryStatus.getData().contains(chatRoomId)){
//                    repository.getInstance().addChatroom(new ChatDoc(chatRoomId));
//                }
                // TODO: trouver comment creer un chatroom avec le chatRoomId
                setAdapter("4O4t9hpFwDRyonsEGO4JJo8pfZI3_9D1GRUQrxhaZlPGI15N1UVQ1WyB2");
            }
        });
    }

    private String buildChatChannel(String userTo){

        String chatChannel = "";

        int result = userTo.compareTo(USERID);

        if(result < 0){
            chatChannel = userTo + "_" + USERID;
        }else{
            chatChannel = USERID + "_" + userTo;
        }
        return chatChannel;
    }
}
//
//
//

//
//        setCollRef();
//        colRef.orderBy("timestamp")
//                .limit(10)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        QueryDocumentSnapshot lastDoc = null;
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Message m = new Message();
//                            m.setTextMessage(document.getString("textMessage"));
//                            chatAdaptor.addMessage(m);
//                            lastDoc = document;
//                        }
//                        timestamp = (lastDoc != null) ? lastDoc.getTimestamp("timestamp") : timestamp;
//                        if(timestamp != null)
//                            Log.d(TAG, timestamp.toString());
//                    } else {
//                        Log.d(TAG, "Error getting documents: ", task.getException());
//                    }
//                });
//
//        colRef.addSnapshotListener((value, e) -> {
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e);
//            }
//            colRef.whereGreaterThan("timestamp" ,timestamp)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            QueryDocumentSnapshot lastDoc = null;
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Message m = new Message();
//                                m.setTextMessage(document.getString("textMessage"));
//                                chatAdaptor.addMessage(m);
//                                lastDoc = document;
//                            }
//                            timestamp = (lastDoc != null) ? lastDoc.getTimestamp("timestamp") : timestamp;
//
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    });
//        });
//
//        binding.btnChatSend.setOnClickListener( view -> {
//
//            Map<String, Object> msg = new HashMap<>();
//            msg.put("textMessage", binding.etChatMessage.getText().toString());
//            msg.put("timestamp", Timestamp.now());
//
//            colRef.add(msg).addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId()))
//                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
//
//            binding.etChatMessage.setText("");
//        });
//    }
//
//
//    void  setCollRef(){
//
//        colRef = FirebaseFirestore.getInstance().collection("chats")
//                .document(buildChatChannel())
//                .collection("messages");
//    }
//

//    }