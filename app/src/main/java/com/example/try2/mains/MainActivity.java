package com.example.try2.mains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.try2.register.Login;
import com.example.try2.R;
import com.example.try2.objects.User;
import com.example.try2.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 3000;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private ImageView logo;
    private ImageView name;
    Animation topAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        firestore= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //name= findViewById(R.id.s4sName);
        logo= findViewById(R.id.logo_option_2);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        //name.setAnimation(topAnim);
        logo.setAnimation(topAnim);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        new Handler().postDelayed(() -> {



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

        },SPLASH_TIMEOUT);

    }
}


