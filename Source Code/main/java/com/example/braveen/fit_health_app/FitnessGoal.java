package com.example.braveen.fit_health_app;

/**
 * Created by Braveen on 07/03/2018.
 */

public class FitnessGoal {
    public String diet;
    public int expectedPeriod;
    public double currentWeight;
    public double goalWeight;

    public FitnessGoal(double currentWeight, double goalWeight, String diet, int expectedPeriod) {
        this.currentWeight = currentWeight;
        this.goalWeight = goalWeight;
        this.diet = diet;
        this.expectedPeriod = expectedPeriod;
    }
}
