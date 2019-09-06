package com.example.braveen.fit_health_app;

/**
 * Created by Braveen on 28/01/2018.
 */

public class UserBio {
    public String firstName;
    public String lastName;
    public String gender;
    public int age;
    public double height;
    public double weight;

    public UserBio(String firstName, String lastName, String gender, int age, double height, double weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }
}
