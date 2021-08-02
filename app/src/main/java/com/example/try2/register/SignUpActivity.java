package com.example.try2.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.try2.R;
import com.example.try2.adapters.SpinnerAdapter;
import com.example.try2.objects.Course;
import com.example.try2.objects.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    EditText name, lastName, mEmail, password1, password2;
    FirebaseAuth fAuth;
    Button signUpButton;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    List<String> degrees;
    ImageView dropDown1, dropDown2, dropDown3;
    Spinner spinner1, spinner2, spinner3;


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
        progressBar = findViewById(R.id.progressBar);
        spinner1 = findViewById(R.id.spinnerDegree1);
        spinner2 = findViewById(R.id.spinnerDegree2);
        spinner3 = findViewById(R.id.spinnerDegree3);




        //firebase stuff
        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        //set the list- WE NEED TO PUT THE LIST IN FIREBASE!!!!
        degrees = Arrays.asList("COMPUTER SCIENCE","PSYCHOLOGY","MEDICINE","MATHEMATICS","POLITICS","FINANCE","BUSINESS ADMINISTRATION","ELECTRICAL ENGINEERING",
                "MARKETING","LITERATURE","LAW","HISTORY","DESIGN","ART","HEBREW","ENGLISH","CRIMINOLOGY","ARCHEOLOGY",
                "BIOLOGY","MECHANICAL ENGINEERING","PHYSICS","CHEMISTRY","COMMUNICATION");
        ArrayList<Course> customCourses = new ArrayList<>();
        //Course.initCourses();
        customCourses.add(new Course("COMPUTER SCIENCE", R.drawable.ic_computer));
        customCourses.add(new Course("PSYCHOLOGY", R.drawable.ic_psychology));
        customCourses.add(new Course("MEDICINE", R.drawable.ic_medicine));
        customCourses.add(new Course("MATHEMATICS", R.drawable.ic_math));


        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, R.layout.spinner, customCourses);
        spinner1.setAdapter(spinnerAdapter);
        spinner2.setAdapter(spinnerAdapter);
        spinner3.setAdapter(spinnerAdapter);



        ArrayList<Course> chosenDegrees =new ArrayList<>();


        signUpButton.setOnClickListener(v -> {
           //check validation on email and password
            String email = mEmail.getText().toString().trim();
            String password = password1.getText().toString().trim();
            String passwordAgain = password2.getText().toString().trim();
            String fullName = name.getText().toString();
            String lastName1 = lastName.getText().toString().trim();
            String chosenDegree1 = spinner1.getSelectedItem().toString();
            String chosenDegree2 = spinner2.getSelectedItem().toString();
            String chosenDegree3 = spinner3.getSelectedItem().toString();

            //add the chosen degrees to the list chosen degrees
            if(!(chosenDegree1.equals("")) && !(chosenDegree1.equals(chosenDegree2)) && !(chosenDegree1.equals(chosenDegree3))){
                Course course = customCourses.stream()
                        .filter(degree -> chosenDegree1.equals(degree.getCourseName())).findAny().orElse(null);
                chosenDegrees.add(course);
            }
            if(!(chosenDegree2.equals("")) && !(chosenDegree2.equals(chosenDegree1)) && !(chosenDegree2.equals(chosenDegree3)) ){
                Course course = customCourses.stream()
                        .filter(degree -> chosenDegree2.equals(degree.getCourseName())).findAny().orElse(null);
                chosenDegrees.add(course);
            }
            if(!(chosenDegree3.equals("")) && !(chosenDegree3.equals(chosenDegree1)) && !(chosenDegree3.equals(chosenDegree2))){
                Course course = customCourses.stream()
                        .filter(degree -> chosenDegree3.equals(degree.getCourseName())).findAny().orElse(null);
                chosenDegrees.add(course);
            }



            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                password1.setError("Password is required");
                return;
            }
            if(TextUtils.isEmpty(passwordAgain)){
                password2.setError("Password is required");
                return;
            }
            if(passwordAgain.equals(password)){
                password2.setError("Password is not matching");
                return;
            }
            if(password.length()<6){
                password1.setError("Password must be at least 6 characters");
                return;
            }
            if(chosenDegree1.equals("") && chosenDegree2.equals("") && chosenDegree3.equals("")){
                Toast.makeText(SignUpActivity.this, "must choose at least 1 degree", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

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
                                User user = new User(fullName,lastName1,email,chosenDegrees);
                                //Log.i("chosen degrees", String.valueOf(chosenDegrees));

                                //creating a profile with the new user's details
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("", "onSuccess: user profile is created for "+userID);
                                    }
                                });

                                startActivity(new Intent(getApplicationContext(), Login.class));
//                            }else{
//                                Toast.makeText(SignUpActivity.this, "please verify your email", Toast.LENGTH_LONG).show();
//                            }

                        }else{
                            Toast.makeText(SignUpActivity.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(SignUpActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            });
        });

    }


}

