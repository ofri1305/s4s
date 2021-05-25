package com.example.try2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.try2.objects.User;
import com.example.try2.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 4000;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestore= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentUser!=null){
                    firestore.collection("users").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Utils.globalUser=documentSnapshot.toObject(User.class);
                            Intent homeIntent = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(homeIntent);
                            finish();
                        }
                    });

                }else{
                    Intent homeIntent = new Intent(MainActivity.this, Login.class);
                    startActivity(homeIntent);
                    finish();
                }

            }
        },SPLASH_TIMEOUT);

    }
}
