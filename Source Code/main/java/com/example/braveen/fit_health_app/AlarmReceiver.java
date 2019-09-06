package com.example.braveen.fit_health_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Braveen on 04/03/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    String userId;
    String diet;
    double intake;
    double weight;
    double newWeight;
    double BMR;
    double calories;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        WorkoutDuration workoutDuration = new WorkoutDuration(0,0,0,0);
        databaseReference.child("WorkoutDuration").child(userId).setValue(workoutDuration);
        databaseReference.child("StepCount").child(userId).setValue(0);
        databaseReference.child("LeaderBoard").child("Calories").child(userId).setValue(0);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                weight = dataSnapshot.child("Users").child(userId).child("Weight").getValue(Double.class);
                BMR = dataSnapshot.child("MeasuredValues").child(userId).child("BMR").getValue(Double.class);
                calories = dataSnapshot.child("LeaderBoard").child("Calories").child(userId).getValue(Double.class);
                diet = dataSnapshot.child("FitnessGoal").child(userId).child("Diet").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (diet.equals("Awful")){
            intake = BMR*0.7;
        }
        else if (diet.equals("Bad")){
            intake = BMR*0.525;
        }
        else if (diet.equals("Ok")){
            intake = BMR*0.35;
        }
        else if (diet.equals("Good")){
            intake = BMR*0.175;
        }
        else{
            intake = 0;
        }

        newWeight = round(weight - ((calories - intake)/6318),2);
        databaseReference.child("Users").child(userId).child("Weight").setValue(newWeight);
        databaseReference.child("FitnessGoal").child(userId).child("CurrentWeight").setValue(newWeight);
    }

    public static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
