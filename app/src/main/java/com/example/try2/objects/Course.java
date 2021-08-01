package com.example.try2.objects;

import android.graphics.drawable.Icon;
import android.media.tv.TvContract;

public class Course {
    private String courseName;
    private int logo;

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
}
