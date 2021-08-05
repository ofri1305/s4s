package com.example.try2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.try2.R;
import com.example.try2.objects.Course;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Course>{
    LayoutInflater layoutInflater;

    public SpinnerAdapter(@NonNull Context context, int resource, List<Course> customCourses) {
        super(context, resource, customCourses);
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
        View rowView = layoutInflater.inflate(R.layout.spinner, null, true);
        Course course = getItem(position);
        TextView nameOfDegree = (TextView)rowView.findViewById(R.id.nameOfDegreeSpinner);
        ImageView iconImage = (ImageView) rowView.findViewById(R.id.imageIcon);
        nameOfDegree.setText(course.getCourseName());
        iconImage.setImageResource(course.getLogo());
        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.spinner, parent, false);

        Course course = getItem(position);
        TextView nameOfDegree = (TextView)convertView.findViewById(R.id.nameOfDegreeSpinner);
        ImageView iconImage = (ImageView) convertView.findViewById(R.id.imageIcon);
        nameOfDegree.setText(course.getCourseName());
        iconImage.setImageResource(course.getLogo());
        return convertView;
    }

}
