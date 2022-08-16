package com.colibri.appconnect.contactList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.colibri.appconnect.CustomAdaptor;
import com.colibri.appconnect.OnItemClickListener;
import com.colibri.appconnect.R;
import com.colibri.appconnect.data.entity.User;
import com.colibri.appconnect.data.repository;
import com.colibri.appconnect.databinding.FragmentContactBinding;
import com.colibri.appconnect.userprofile.ProfilFragment;
import com.colibri.appconnect.util.QueryStatus;

import java.util.List;


public class ContactFragment extends Fragment implements ContactClickListener{

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);

        ContactListAdapter contactListAdapter = new ContactListAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvConversation.setLayoutManager(linearLayoutManager);
        binding.rvConversation.setAdapter(contactListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvConversation.getContext(),
                linearLayoutManager.getOrientation());
        binding.rvConversation.addItemDecoration(dividerItemDecoration);


        LiveData<QueryStatus<List<User>>> listUser = repository.getInstance().getListUser();
        listUser.observe(getViewLifecycleOwner(), users ->{
            if (users.isSuccessful()){
                contactListAdapter.submitList(users.getData());
            }

        });

        return binding.getRoot();
    }

    @Override
    public void onUserClick(String userId) {
        Fragment fr = ProfilFragment.newInstance(userId);
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.flFragment, fr);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
    }
}