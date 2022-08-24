package com.colibri.appconnect.contactList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.colibri.appconnect.R;
import com.colibri.appconnect.data.entity.User;
import com.colibri.appconnect.databinding.UserItemBinding;


public class ContactListAdapter extends ListAdapter<User, ContactListAdapter.ContactHolder> {

    ContactClickListener contactClickListener;

    ContactListAdapter(ContactClickListener clickListener){
        super(User.diffCallback);
        contactClickListener = clickListener;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        User item = getItem(position);
        Log.d(TAG, "onBindViewHolder: Pos:" + position + " item: " + item);
        holder.bind(item);
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private final UserItemBinding binding;

        ContactHolder(View itemView){
            super(itemView);
            binding = UserItemBinding.bind(itemView);
        }
        void bind(User abb){
            binding.setUser(abb);
            this.itemView.setOnClickListener(v->{
                contactClickListener.onUserClick(abb.getId());
            });
        }
    }

    private static final String TAG = "AP::ActionButtonAdapter";
}