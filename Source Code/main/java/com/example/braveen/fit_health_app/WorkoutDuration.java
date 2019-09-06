package com.example.braveen.fit_health_app;

/**
 * Created by Braveen on 17/02/2018.
 */

public class WorkoutDuration {
    public int running;
    public int cycling;
    public int swimming;
    public int other;

    public WorkoutDuration(int running, int cycling, int swimming, int other) {
        this.running = running;
        this.cycling = cycling;
        this.swimming = swimming;
        this.other = other;
    }
}
