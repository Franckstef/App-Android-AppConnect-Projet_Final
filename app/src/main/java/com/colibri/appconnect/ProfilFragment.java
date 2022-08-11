package com.colibri.appconnect;

import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colibri.appconnect.databinding.ProfileViewLayoutBinding;
import com.colibri.appconnect.userprofile.ActionButtonBinding;
import com.colibri.appconnect.userprofile.UserProfile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ProfileViewLayoutBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.profile_view_layout,
                container,
                false
        );
        UserProfile up = UserProfile.MockUserProfile();
        ActionButtonBinding ChatAction = new ActionButtonBinding(
                AppCompatResources.getDrawable(this.getContext(),R.drawable.ic_message),
                getString(R.string.ProfileAction_Chat));
        ActionButtonBinding PhoneAction = new ActionButtonBinding(
                AppCompatResources.getDrawable(this.getContext(),R.drawable.ic_email),
                up.getPrimaryEmail());
        ActionButtonBinding MailAction = new ActionButtonBinding(
                AppCompatResources.getDrawable(this.getContext(),R.drawable.ic_phone),
                up.getPrimaryPhoneNumber());
        binding.setUserProfile(up);
        binding.setAction1(ChatAction);
        binding.setAction2(PhoneAction);
        binding.setAction3(MailAction);
        return binding.getRoot();
    }
}