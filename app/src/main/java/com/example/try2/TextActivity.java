package com.example.try2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        TextView text = findViewById(R.id.textView3);
        String type1 = getIntent().getStringExtra("num1");
        String type2 = getIntent().getStringExtra("num2");

        if(type1 !=null){

            text.setText("priAndPol");
        }

        if(type2!=null){
            text.setText("At StudentsIL we believe that students from every academic institution should be united and help each other to succeed.\n" +
                    "We worked really hard to give you the opportunity and the best place for that, all we need is your cooperation.\n" +
                    "Please share this app with your friend so our community can grow:)");

        }
    }

}
