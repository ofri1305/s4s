package com.example.try2.objects;

import android.graphics.drawable.Icon;
import android.media.tv.TvContract;

import com.example.try2.R;

import java.util.ArrayList;

public class Course {
    private String courseName;
    private int logo;
    private static ArrayList<Course> courses = new ArrayList<>();

    public Course(String courseName, int logo) {
        this.courseName = courseName;
        this.logo = logo;
    }

    public Course() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
//    public static void initCourses(){
//        courses.add(new Course("COMPUTER SCIENCE", R.drawable.ic_computer));
//        courses.add(new Course("PSYCHOLOGY", R.drawable.ic_psychology));
//        courses.add(new Course("MEDICINE", R.drawable.ic_medicine));
//        courses.add(new Course("MATHEMATICS", R.drawable.ic_math));
//    }
//    public ArrayList<Course> getAllCourses(){
//        return courses;
//    }
}
