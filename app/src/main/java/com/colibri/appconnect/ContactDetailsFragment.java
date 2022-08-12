package com.colibri.appconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ContactDetailsFragment extends Fragment {

    ImageButton message, email, phone;
    private View view;

    public ContactDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        message = view.findViewById(R.id.imageButtonMessage);
        email = view.findViewById(R.id.imageButtonEmail);
        phone = view.findViewById(R.id.imageButtonTel);

        message.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            startActivity(intent);
        });

        email.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:franckstefani1@gmail.com"));
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));
        });

        phone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ "5146923710"));
            startActivity(intent);
        });

        return view;
    }

}