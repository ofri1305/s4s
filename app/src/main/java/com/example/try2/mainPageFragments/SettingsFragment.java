package com.example.try2.mainPageFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.try2.R;
import com.example.try2.Report;
import com.example.try2.TextActivity;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


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
        View v= inflater.inflate(R.layout.fragment_third, container, false);
        Button priAndPol=(Button) v.findViewById(R.id.buttonPriAndPol);
        Button aboutUs=(Button) v.findViewById(R.id.buttonAboutUs);
        Button report=(Button) v.findViewById(R.id.buttonReport);
        String value1="priAndPol";
        String value2="aboutUs";



        priAndPol.setOnClickListener(v1 -> {
            Intent in=new Intent(getActivity(), TextActivity.class);
            in.putExtra("num1", value1);
            startActivity(in);
        });

        aboutUs.setOnClickListener(v12 -> {
            Intent in=new Intent(getActivity(),TextActivity.class);
            in.putExtra("num2",value2);
            startActivity(in);
        });

        report.setOnClickListener(v13 -> {
            Intent in=new Intent(getActivity(), Report.class);
            startActivity(in);
        });
        return v;
    }
}
