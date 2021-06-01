package com.example.try2;

import android.os.Bundle;

import com.example.try2.objects.Chat;
import com.example.try2.objects.User;

import com.example.try2.recyclers.ChatRecycler;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.try2.ui.main.SectionsPagerAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TabbedActivity extends AppCompatActivity {
    Spinner sDegree;
    Spinner sYear;
    Spinner sSemester;
    String degrees[]={"First", "Second","Phd"};
    String years[]={"1","2","3","4","5"};
    String semesters[]={"1","2","Summer Semester"};
    String selectedDegree = "First";
    String selectedYear = "1";
    String selectedSemester = "1";
    String course;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        course = getIntent().getStringExtra("CourseName");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),course);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

    sDegree = findViewById(R.id.spinner);
    sYear = findViewById(R.id.spinner2);
    sSemester = findViewById(R.id.spinner3);
        fStore=FirebaseFirestore.getInstance();
    //set array adapters for spinners
    ArrayAdapter forSpinner1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,degrees);
    ArrayAdapter forSpinner2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,years);
    ArrayAdapter forSpinner3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,semesters);

    sDegree.setAdapter(forSpinner1);
    sYear.setAdapter(forSpinner2);
    sSemester.setAdapter(forSpinner3);

    //set onItemSelectedListener listeners to the spinners
    sDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedDegree = degrees[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    sYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedYear = years[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    sSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedSemester = semesters[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });



    //getting the name of the course and according to the name, the activity sends the specific request to firebase

     if(course != null){
         fStore.collection("Test").document("chats").collection("user").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Chat> chats= (ArrayList<Chat>) queryDocumentSnapshots.toObjects(Chat.class);
                 Log.i("chats",""+chats.size());
                 setRecyclerView(chats);
             }
         });
        }



    }

    private void setRecyclerView(ArrayList<Chat> chats) {
        RecyclerView recyclerView = findViewById(R.id.chat_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        ChatRecycler recycler= new ChatRecycler(chats);
        recyclerView.setAdapter(recycler);
    }
}