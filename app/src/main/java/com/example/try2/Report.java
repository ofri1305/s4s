package com.example.try2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Report extends AppCompatActivity {

    private EditText to;
    private EditText subject;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        to = findViewById(R.id.edit_text_to);
        subject = findViewById(R.id.edit_text_subject);
        message = findViewById(R.id.edit_text_message);

        to.setText("tamarofriprojects@gmail.com");

        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

    }

    private void sendMail(){
        String recipients = to.getText().toString();
      //  String[] recipients = recipientList.split(",");

        String theSubject = subject.getText().toString();
        String theMessage = message.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, theSubject);
        intent.putExtra(Intent.EXTRA_TEXT, theMessage);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));

    }
}