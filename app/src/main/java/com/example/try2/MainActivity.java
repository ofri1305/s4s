package com.example.try2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.try2.objects.User;
import com.example.try2.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 1000;
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
                            Log.i(" Utils.globalUser", "onSuccess: "+ currentUser.getUid());
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
