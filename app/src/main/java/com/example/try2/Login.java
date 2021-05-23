package com.example.try2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText username;
    private EditText password;
    ProgressBar p;
    Button loginButton;
    FirebaseAuth fAuth;
    TextView sign;
    TextView reset;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        p = findViewById(R.id.progressBar2);
        loginButton = findViewById(R.id.loginButton);
        sign = findViewById(R.id.textView4);
        reset = findViewById(R.id.reset);
        fAuth = FirebaseAuth.getInstance();

        //reset password
        //sends a link to email to reset password
        reset.setOnClickListener(v -> {
            if(username.equals("")){
                username.setError("Enter your email");
            }else{
                String mail = username.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(Login.this, "Reset Link was sent to your email", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(Login.this, "Error!"+ e.getMessage(), Toast.LENGTH_SHORT).show());
            }

        });

        //sign up button - go to sign up activity
        sign.setOnClickListener(v -> {
            Intent in=new Intent(Login.this, SignUpActivity.class);
            startActivity(in);
        });

        //login button handler
        loginButton.setOnClickListener((View view)->{
            //check validation on email and password
            String email = username.getText().toString().trim();
            String pssd = password.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                username.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(pssd)){
                password.setError("Password is required");
                return;
            }
            if(pssd.length()<6){
                password.setError("Password must be at least 6 characters");
                return;
            }
            p.setVisibility(View.VISIBLE);

            //authenticate the user
            fAuth.signInWithEmailAndPassword(email, pssd).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    if(fAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(Login.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                    }else{
                        Toast.makeText(Login.this, "verify your email!", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(Login.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    p.setVisibility(View.GONE);

                }
            });
        });
    }
}
