package com.example.try2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.try2.R;
import com.example.try2.objects.Course;

import java.util.ArrayList;
import java.util.List;

public class ListDegreesAdapter extends ArrayAdapter<Course> {
    LayoutInflater layoutInflater;
//    private Context activity;
//    private int layoutId;
//    private List<Course> courses;

    public ListDegreesAdapter(@NonNull Context context, int resource, ArrayList<Course> courses) {
        super(context, resource, courses);
        layoutInflater = LayoutInflater.from(context);
//        this.activity = context;
//        this.layoutId = resource;
//        this.courses = courses;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.degree_home, parent, false);

        Course course = getItem(position);
        ImageView icon = convertView.findViewById(R.id.iconHome);
        TextView degree = convertView.findViewById(R.id.degreeHome);
        icon.setImageResource(course.getLogo());
        degree.setText(course.getCourseName());
        return convertView;
    }

}
