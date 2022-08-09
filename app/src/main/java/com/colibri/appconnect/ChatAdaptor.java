package com.colibri.appconnect;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.colibri.appconnect.databinding.MessageItemBinding;

import java.util.ArrayList;

public class ChatAdaptor extends RecyclerView.Adapter<ChatAdaptor.CategoryViewHolder> {


    private ArrayList<Message> messageList;
    private OnItemClickListener listener;


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
         MessageItemBinding binding;

        public CategoryViewHolder(@NonNull MessageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind( int position, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(position));
        }

        public void setMessage(Message message) {
            binding.setMessage(message);
        }
    }

    public ChatAdaptor(ArrayList<Message> messagrList, OnItemClickListener listener) {
        this.messageList = messagrList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatAdaptor.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        MessageItemBinding binding = MessageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatAdaptor.CategoryViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ChatAdaptor.CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setMessage(messageList.get(position));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public void addMessage(Message msg){
        messageList.add(0, msg);
        notifyItemInserted(0);
    }


//    public User getUser(int position){
//        return userList.get(position);
//    }
//
//    public void updateUser(User user, int position){
//        userList.set(position, user);
//        notifyItemChanged(position);
//    }
}
