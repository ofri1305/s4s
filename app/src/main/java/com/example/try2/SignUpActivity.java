package com.example.try2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText name;
    EditText lastName;
    EditText mEmail;
    EditText password1;
    EditText password2;
    FirebaseAuth fAuth;
    Button signUpButton;
    ProgressBar p;
    FirebaseFirestore fStore;
    String userID;

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

        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        signUpButton.setOnClickListener(v -> {
           //check validation on email and password
            String email = mEmail.getText().toString().trim();
            String password = password1.getText().toString().trim();
            String fullName = name.getText().toString();
            String lastName1 = lastName.getText().toString().trim();



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
                            ArrayList<String>course=new ArrayList<>();
                            course.add("test");
                                User user = new User(fullName,lastName1,email,course);

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
