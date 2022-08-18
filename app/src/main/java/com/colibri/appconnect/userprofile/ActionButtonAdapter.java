package com.colibri.appconnect.userprofile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.colibri.appconnect.R;
import com.colibri.appconnect.databinding.TmpActionButtonBinding;

public class ActionButtonAdapter extends ListAdapter<ActionButtonBinding, ActionButtonAdapter.ActionButtonHolder> {

    ActionButtonAdapter(){
        super(ActionButtonBinding.diffCallback);
    }

    @NonNull
    @Override
    public ActionButtonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tmp_action_button,parent,false);
        return new ActionButtonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionButtonHolder holder, int position) {
        ActionButtonBinding item = getItem(position);
        Log.d(TAG, "onBindViewHolder: Pos:" + position + " item: " + item);
        holder.bind(item);
    }

    static class ActionButtonHolder extends RecyclerView.ViewHolder {
        private final TmpActionButtonBinding binding;
        ActionButtonHolder(View itemView){
            super(itemView);
            binding = TmpActionButtonBinding.bind(itemView);
        }
        void bind(ActionButtonBinding abb){
            binding.setAction(abb);
        }
    }

    private static final String TAG = "AP::ActionButtonAdapter";
}
