package com.example.try2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.try2.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText name, lastName, mEmail, password1, password2;
    FirebaseAuth fAuth;
    Button signUpButton;
    ProgressBar p;
    FirebaseFirestore fStore;
    String userID;
    List<String> degrees;
    AutoCompleteTextView degree1, degree2, degree3;
    ImageView dropDown1, dropDown2, dropDown3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name= findViewById(R.id.name);
        lastName= findViewById(R.id.lastName);
        mEmail = findViewById(R.id.email);
        password1=findViewById(R.id.password1);
        password2=findViewById(R.id.password2);
        signUpButton=findViewById(R.id.buttonSignUp);
        p = findViewById(R.id.progressBar);

        //autoComplete Degrees
        degree1 = findViewById(R.id.autoCompleteDegree1);
        degree2 = findViewById(R.id.autoCompleteDegree2);
        degree3 = findViewById(R.id.autoCompleteDegree3);
        degree1.setThreshold(1);
        degree2.setThreshold(1);
        degree3.setThreshold(1);

        //imageView DropDown
        dropDown1 = findViewById(R.id.dropDown1);
        dropDown2 = findViewById(R.id.dropDown2);
        dropDown3 = findViewById(R.id.dropDown3);

        //firebase stuff
        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        //set the list
        degrees = Arrays.asList("COMPUTER SCIENCE","PSYCHOLOGY","MEDICINE","MATHEMATICS","POLITICS","FINANCE","BUSINESS ADMINISTRATION","ELECTRICAL ENGINEERING",
                "MARKETING","LITERATURE","LAW","HISTORY","DESIGN","ART","HEBREW","ENGLISH","CRIMINOLOGY","ARCHEOLOGY",
                "BIOLOGY","MECHANICAL ENGINEERING","PHYSICS","CHEMISTRY","COMMUNICATION");

        //set adapter for autoComplete degrees
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,degrees);
        degree1.setAdapter(adapter);
        degree2.setAdapter(adapter);
        degree3.setAdapter(adapter);

        //set onClick for dropdown images
        dropDown1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree1.showDropDown();
            }
        });
        dropDown2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree2.showDropDown();
            }
        });
        dropDown3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree3.showDropDown();
            }
        });




        ArrayList<String> chosenDegrees =new ArrayList<>();





        signUpButton.setOnClickListener(v -> {
           //check validation on email and password
            String email = mEmail.getText().toString().trim();
            String password = password1.getText().toString().trim();
            String fullName = name.getText().toString();
            String lastName1 = lastName.getText().toString().trim();
            String chosenDegree1 = degree1.getText().toString();
            String chosenDegree2 = degree2.getText().toString();
            String chosenDegree3 = degree3.getText().toString();

            //add the chosen degrees to the list chosen degrees
            if(!(chosenDegree1.equals("")) && !(chosenDegree1.equals(chosenDegree2)) && !(chosenDegree1.equals(chosenDegree3))){ chosenDegrees.add(chosenDegree1); }
            if(!(chosenDegree2.equals("")) && !(chosenDegree2.equals(chosenDegree1)) && !(chosenDegree2.equals(chosenDegree3)) ){ chosenDegrees.add(chosenDegree2); }
            if(!(chosenDegree3.equals("")) && !(chosenDegree3.equals(chosenDegree1)) && !(chosenDegree3.equals(chosenDegree2))){ chosenDegrees.add(chosenDegree3); }



            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                password1.setError("Password is required");
                return;
            }
            if(password.length()<6){
                password1.setError("Password must be at least 6 characters");
                return;
            }
            p.setVisibility(View.VISIBLE);

            //register the user in firebase
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                   //send verification email
                    fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "We sent you an email, please verify", Toast.LENGTH_LONG).show();

                            //if(fAuth.getCurrentUser().isEmailVerified()){
                                //Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                //course.add("test");
                                User user = new User(fullName,lastName1,email,chosenDegrees);

                                //creating a profile with the new user's details
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("", "onSuccess: user profile is created for "+userID);
                                    }
                                });

                                startActivity(new Intent(getApplicationContext(),Login.class));
//                            }else{
//                                Toast.makeText(SignUpActivity.this, "please verify your email", Toast.LENGTH_LONG).show();
//                            }

                        }else{
                            Toast.makeText(SignUpActivity.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(SignUpActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    p.setVisibility(View.GONE);

                }
            });
        });

    }


}

