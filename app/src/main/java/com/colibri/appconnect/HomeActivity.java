package com.colibri.appconnect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.colibri.appconnect.databinding.ActivityHomeBinding;
import com.google.firebase.firestore.CollectionReference;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HOmeActivity";
    ActivityHomeBinding binding;
    CollectionReference testRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //section FIRESTORE
//        FirebaseFirestore.getInstance().collection("users").limit(1)
//                .addSnapshotListener((value, e) -> {
//                    if (e != null) {
//                        Log.w(TAG, "listen:error", e);
//                        return;
//                    }
//
//                    assert value != null;
//                    for (DocumentChange dc : value.getDocumentChanges()) {
//                        switch (dc.getType()) {
//                            case ADDED:
//                                Log.d(TAG, "New city: " + dc.getDocument().getData());
//                                break;
//                            case MODIFIED:
//                                Log.d(TAG, "Modified city: " + dc.getDocument().getData());
//                                break;
//                            case REMOVED:
//                                Log.d(TAG, "Removed city: " + dc.getDocument().getData());
//                                break;
//                        }
//                    }
//                });


        // Section Realtime Database
//        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("messages");
//
//        //myRef.setValue("Hello, World!");
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
    }



}