package com.example.try2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TextActivity extends AppCompatActivity {

    FirebaseFirestore fStore;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        TextView content = findViewById(R.id.contentTextView);
        String type1 = getIntent().getStringExtra("num1");
        String type2 = getIntent().getStringExtra("num2");
        fStore = FirebaseFirestore.getInstance();


        if(type1 !=null){
            fStore.collection("settings").document("terms_of_use").get().addOnSuccessListener(documentSnapshot -> {
                content.setText(documentSnapshot.getString("policy"));
            });
        }

        if(type2!=null){
            fStore.collection("settings").document("about_us").get().addOnSuccessListener(documentSnapshot -> {
                content.setText(documentSnapshot.getString("story"));
            });
        }
    }

}
