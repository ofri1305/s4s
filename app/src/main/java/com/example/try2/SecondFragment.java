package com.example.try2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    ListView myList;
    EditText theFilter;
    ArrayList<String> degrees;
    ArrayAdapter<String> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_second,container,false);
        //connect to layout
        myList = v.findViewById(R.id.list);
        theFilter = v.findViewById(R.id.editTextTextPersonName);
       //set adapter
        ArrayList <String> degrees= new ArrayList<>();


//        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,degrees);
//        myList.setAdapter(adapter);
        //add to list
        degrees.add("COMPUTER SCIENCE");
        degrees.add("PSYCHOLOGY");
        degrees.add("MEDICINE");
        degrees.add("MATHEMATICS");
        degrees.add("POLITICS");
        degrees.add("FINANCE");
        degrees.add("BUSINESS ADMINISTRATION");
        degrees.add("ELECTRICAL ENGINEERING");
        degrees.add("MARKETING");
        degrees.add("POLITICS");
        degrees.add("LITERATURE");
        degrees.add("LAW");
        degrees.add("HISTORY");
        degrees.add("DESIGN");
        degrees.add("ART");
        degrees.add("HEBREW");
        degrees.add("CRIMINOLOGY");
        degrees.add("ARCHEOLOGY");
        degrees.add("BIOLOGY");
        degrees.add("MECHANICAL ENGINEERING");
        degrees.add("PHYSICS");
        degrees.add("CHEMISTRY");
        degrees.add("COMMUNICATION");

        adapter=new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item,degrees){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view= super.getView(position, convertView, parent);
                view.setBackground(getResources().getDrawable(R.drawable.purple));
                return view;
            }
        };
//        b.setOnClickListener(v1 -> {
//            startActivity(new Intent(getContext(),TabbedActivity.class));
//
//        });

        myList.setAdapter(adapter);
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ( SecondFragment.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //setting on item click for each item on thr list and put extra value of its name
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),TabbedActivity.class);
                intent.putExtra("CourseName", myList.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });



        return v;

    }
}
