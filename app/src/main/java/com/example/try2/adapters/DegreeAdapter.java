package com.example.try2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.try2.R;

import java.util.List;

//package com.example.try2;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import java.util.List;
//
//public class DegreeAdapter extends ArrayAdapter {
//
//    private SecondFragment activity;
//    //private int layoutId;
//    private String names[];
//
//
//    public DegreeAdapter(SecondFragment c, String[] names) {
//        super(c,R.layout.row,R.id.textView,names);
//        this.activity=c;
//        this.names=names;
//    }
//
//    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
//        LayoutInflater inflater = LayoutInflater.from(this.activity);
//        View row = inflater.inflate(R.layout.row, parent, false);
//        TextView degree= row.findViewById(R.id.textView);
//
//        degree.setText(names[position]);
//        return row;
//    }
//}
class DegreeAdapter extends ArrayAdapter {
    private List<String> degrees;
    private Context activity;
    private int layoutId;

    public DegreeAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.activity = context;
        this.layoutId = resource;
        this.degrees = objects;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.activity);
        convertView = inflater.inflate(this.layoutId, parent, false);
        TextView name = convertView.findViewById(R.id.textView);
        name.setText(degrees.get(position));

        return convertView;
    }
}
