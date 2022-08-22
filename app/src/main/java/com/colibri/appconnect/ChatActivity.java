package com.colibri.appconnect;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.colibri.appconnect.data.entity.ChatRoom;
import com.colibri.appconnect.data.firestore.document.ChatDoc;
import com.colibri.appconnect.data.firestore.document.MessageDoc;
import com.colibri.appconnect.data.repository;
import com.colibri.appconnect.databinding.ActivityChatBinding;
import com.colibri.appconnect.util.QueryStatus;

import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    //public static final String CHATROOMID = "4O4t9hpFwDRyonsEGO4JJo8pfZI3_9D1GRUQrxhaZlPGI15N1UVQ1WyB2";
    public static final String USERTO = "userTo";
    private static final String TAG = "ChatActivity";
   // private final String USERID = "9D1GRUQrxhaZlPGI15N1UVQ1WyB2";

    private boolean exist = false;
    private ActivityChatBinding binding;


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
            LiveData<QueryStatus<ChatRoom>> chatroom = repository.getInstance().getChatroom(buildChatChannel(getIntent().getStringExtra(USERTO)));
            chatroom.observe(this, chatRoomQueryStatus -> {
                if (chatRoomQueryStatus.isSuccessful()) {
                    ChatRoom room = chatRoomQueryStatus.getData();
                    if(!exist){
                        repository.getInstance().addChatroom(new ChatDoc(buildChatChannel(getIntent().getStringExtra(USERTO))),
                                new MessageDoc(binding.etChatMessage.getText().toString(), "userToId")
                                ,()->{
                            setAdapter(buildChatChannel(getIntent().getStringExtra(USERTO)));
                        });
                    }
                    else{
                        room.sendMessage(new MessageDoc(binding.etChatMessage.getText().toString(), "userToId"), null);
                        Log.e(TAG, "onCreate: else" );
                    }
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
                        exist = true;
                    }
                });
            }
        });
    }

    private void checkIfExistChatRoom() {
        String chatRoomId = buildChatChannel(getIntent().getStringExtra(USERTO));
        repository.getInstance().getChatroom(chatRoomId).observe(this, chatRoom ->{
            if(chatRoom.getData() != null && chatRoom.isSuccessful()){
                setAdapter(buildChatChannel(getIntent().getStringExtra(USERTO)));
            }
        });
    }

    private String buildChatChannel(String userTo){

        String chatChannel = "";


        String user = "9D1GRUQrxhaZlPGI15N1UVQ1WyB2";
        int result = userTo.compareTo(user);

        if(result < 0){
            chatChannel = userTo + "_" + user;
        }else{
            chatChannel = user + "_" + userTo;
        }
        return chatChannel;
    }
}