package com.colibri.appconnect.userprofile;

import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        SharedUserProfileViewModel.Factory vmFactory = new SharedUserProfileViewModel.Factory("as", "asd");
        viewModel = new ViewModelProvider(this,vmFactory).get(SharedUserProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentProfilBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profil,
                container,
                false
        );
        viewModel.getUserProfile();
        LiveData<ActionButtonBinding> PhoneAction = Transformations.map(viewModel.getUserProfile(), resUserProfile->{
            if (resUserProfile == null) {return null;}
            return new ActionButtonBinding(
                    AppCompatResources.getDrawable(this.getContext(),R.drawable.ic_email),
                    resUserProfile.getPrimaryEmail());
        });

        LiveData<ActionButtonBinding> MailAction = Transformations.map(viewModel.getUserProfile(), resUserProfile->{
            if (resUserProfile == null) {return null;}
            return new ActionButtonBinding(
                    AppCompatResources.getDrawable(this.getContext(),R.drawable.ic_phone),
                    resUserProfile.getPrimaryPhoneNumber());
        });
        UserProfile up = UserProfile.MockUserProfile();
        ActionButtonBinding ChatAction = new ActionButtonBinding(
                AppCompatResources.getDrawable(this.getContext(),R.drawable.ic_identity),
                "Sign Out");

        viewModel.getUserProfile().observe(this,
                userProfile -> {
                    if (userProfile != null) {
                        userProfile.setAvatarToImageView(binding.include.profileImage);
                    }
                }
        );
        binding.setLifecycleOwner(this);
        binding.setUserProfile(viewModel);
        binding.setAction1(ChatAction);
//        binding.setAction2(PhoneAction);
//        binding.setAction3(MailAction);
        return binding.getRoot();
    }
}