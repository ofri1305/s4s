package com.example.try2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.try2.objects.Course;
import com.example.try2.objects.User;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<Course>{

    LayoutInflater layoutInflater;
    public SpinnerAdapter(@NonNull Context context, int resource, ArrayList<Course> customCourses) {
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
            convertView = layoutInflater.inflate(R.layout.spinner, parent, true);

        Course course = getItem(position);
        TextView nameOfDegree = (TextView)convertView.findViewById(R.id.nameOfDegreeSpinner);
        ImageView iconImage = (ImageView) convertView.findViewById(R.id.imageIcon);
        nameOfDegree.setText(course.getCourseName());
        iconImage.setImageResource(course.getLogo());
        return convertView;
    }

}
