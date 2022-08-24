package com.colibri.appconnect.userprofile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colibri.appconnect.ChatActivity;
import com.colibri.appconnect.R;
import com.colibri.appconnect.databinding.FragmentProfilBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {

    private SharedUserProfileViewModel viewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USERID = "UserId";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String userId;
//    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param UserId Parameter 1.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String UserId) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERID, UserId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USERID);
        }

        SharedUserProfileViewModel.Factory vmFactory = new SharedUserProfileViewModel.Factory(userId);
        viewModel = new ViewModelProvider(this,vmFactory).get(SharedUserProfileViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        // Inflate the layout for this fragment
        FragmentProfilBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profil,
                container,
                false
        );

        viewModel.setSignOutClickListener(this::OnSignOut);
        viewModel.setPhoneClickListener(this::OnPhoneAction);
        viewModel.setEmailClickListener(this::OnEmailAction);
        viewModel.setChatClickListener(this::OnChatAction);


        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        return binding.getRoot();

    }

    private void OnChatAction(String OtherUserId){
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra(ChatActivity.USERTO, OtherUserId);
        startActivity(intent);
    }

    private void OnPhoneAction(String phoneNumber){
        final Uri phoneUri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL, phoneUri);
        startActivity(intent);
    }

    private void OnEmailAction(String email){
        final Uri emailUri = Uri.parse("mailto:" + email);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(emailUri);
        startActivity(Intent.createChooser(emailIntent, "Envoyer un courriel"));
    }

    private void OnSignOut(View signOutButton) {
        requireActivity().finish();
    }
}