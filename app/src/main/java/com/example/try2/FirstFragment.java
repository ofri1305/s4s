package com.example.try2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {


        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    Button logOutButton, resetPsw, changeProfile;
    TextView fName, lName, eMail, degree1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    ImageView profileImage;
    StorageReference storageReference;

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
            View v=inflater.inflate(R.layout.fragment_first, container, false);
            //text views
            fName = v.findViewById(R.id.n1);
            lName = v.findViewById(R.id.n2);
            eMail = v.findViewById(R.id.m1);
            //degree1 = v.findViewById(R.id.degrees);

            //buttons
            resetPsw = v.findViewById(R.id.button3);
            changeProfile = v.findViewById(R.id.changeProfile);
            logOutButton = v.findViewById(R.id.logoutButton);

            //image
            profileImage = v.findViewById(R.id.profileImage);

            //firebase
            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            storageReference = FirebaseStorage.getInstance().getReference();


        userId = fAuth.getCurrentUser().getUid();

       //create a collection named "users" in firebase and put attributes
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fName.setText(value.getString("firstName"));
                lName.setText(value.getString("lastName"));
                eMail.setText(value.getString("email"));
                //degree1.setText(value.getString("courseNames"));
            }
        });

        //import the profile image from firebase to app
        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Picasso.get().load(uri).into(profileImage);
        });


//        documentReference.addSnapshotListener(getActivity(), (value, error) -> {
//            fName.setText(value.getString("fullName"));
//            lName.setText(value.getString("lastName"));
//            eMail.setText(value.getString("email"));
//        });

        // Inflate the layout for this fragment


        //reset password
        resetPsw.setOnClickListener(v1 -> {
            if(eMail.equals("")){
                eMail.setError("Enter your email");
            }else{
                String mail = eMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Reset Link was sent to your email", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(getContext(), "Error!"+ e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        //change profile onclick - go to editProfile activity
        changeProfile.setOnClickListener(v12 -> {
            Intent i = new Intent(getContext(),EditProfile.class);
            i.putExtra("firstName", fName.getText().toString());
            i.putExtra("lastName", lName.getText().toString());
            i.putExtra("email", eMail.getText().toString());
            startActivity(i);

        });
        return v;
    }
    //logout when logout button is clicked
    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(),Login.class));

    }
}
