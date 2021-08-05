package com.example.try2.mainPageFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.try2.R;
import com.example.try2.adapters.ListDegreesAdapter;
import com.example.try2.degreePageFragments.TabbedActivity;
import com.example.try2.objects.Course;
import com.example.try2.utils.Utils;

import java.util.ArrayList;



public class HomeFragment extends Fragment {


    ListView myList;
    EditText theFilter;
    ArrayAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second,container,false);
        myList = v.findViewById(R.id.list);

       /* ArrayList <String> degrees= Utils.globalUser.getCourseNames();
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,degrees);
        myList.setAdapter(adapter);*/
        ArrayList <Course> degrees= Utils.globalUser.getCourses();
        ListDegreesAdapter listDegreesAdapter = new ListDegreesAdapter(getActivity(), R.layout.degree_home, degrees);
        myList.setAdapter(listDegreesAdapter);


//
//        adapter=new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item,degrees){
//
//            //the design
//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                View view= super.getView(position, convertView, parent);
//                view.setBackground(getResources().getDrawable(R.drawable.costum_row));
//                return view;
//            }
//        };


        //myList.setAdapter(adapter); //CRASHES THE APP!!!


        //SAVE FOR SEARCH IN FILES
//        theFilter.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                ( HomeFragment.this).adapter.getFilter().filter(s);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        //setting on item click for each item on thr list and put extra value of its name
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TabbedActivity.class);
                intent.putExtra("CourseName",((Course) myList.getItemAtPosition(position)).getCourseName());
                startActivity(intent);
            }
        });



        return v;

    }
}
