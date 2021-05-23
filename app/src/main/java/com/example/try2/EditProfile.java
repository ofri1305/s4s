package com.example.try2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    ImageView profile;
    EditText fName, lName, email;
    Button saveButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profile = findViewById(R.id.profileImageChange);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        email = findViewById(R.id.editTextEmail);
        saveButton = findViewById(R.id.saveButton);

        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();


        //fetch data from intent
        Intent data = getIntent();
        String firstName = data.getStringExtra("firstName");
        String lastName = data.getStringExtra("lastName");
        String emailString = data.getStringExtra("email");

        //display previous data on screen
        fName.setText(firstName);
        lName.setText(lastName);
        email.setText(emailString);

        //save the new data into firebase
        saveButton.setOnClickListener(v -> {
            //validation check
            if(fName.getText().toString().isEmpty() || lName.getText().toString().isEmpty() || email.getText().toString().isEmpty()){
                Toast.makeText(EditProfile.this, "one or more fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }

                String emailF = email.getText().toString();
                user.updateEmail(emailF).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email", emailF);
                        edited.put("fullName", fName.getText().toString());
                        edited.put("lastName", lName.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profiled Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), FirstFragment.class));
                                finish();
                            }
                        });
                        //Toast.makeText(EditProfile.this, "Profile", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        });

        //create storage reference and use picasso library
        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Picasso.get().load(uri).into(profile);
        });


        //set new profile picture
        profile.setOnClickListener(v -> {
            //open gallery
            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGallery, 1000);
        });

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,@androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        }
    }

    //method that uploads image to firebase storage
    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener((OnSuccessListener)(taskSnapshot)->{
            fileRef.getDownloadUrl().addOnSuccessListener((OnSuccessListener)(uri)->{
                Picasso.get().load((Uri) uri).into(profile);
            });
        }).addOnFailureListener((e) -> {
            Toast.makeText(EditProfile.this, "Failed", Toast.LENGTH_SHORT).show();
        });
    }
}
