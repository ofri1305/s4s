package com.example.try2.objects;

import java.util.ArrayList;

public class User {
    private String firstName,lastName,email;
    private ArrayList<String> courseNames;
    private ArrayList<Course> courses;

    public User() {}

//    public User(String firstName, String lastName, String email, ArrayList<String> courseNames) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.courseNames = courseNames;
//    }

    public User(String firstName, String lastName, String email, ArrayList<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.courses = courses;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(ArrayList<String> courseNames) {
        this.courseNames = courseNames;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
