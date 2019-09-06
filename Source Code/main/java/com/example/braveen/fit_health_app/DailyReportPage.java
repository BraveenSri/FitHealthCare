package com.example.braveen.fit_health_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DailyReportPage extends AppCompatActivity {

    TextView runningDurationTxt;
    TextView cyclingDurationTxt;
    TextView swimmingDurationTxt;
    TextView otherDurationTxt;
    TextView walkingCalorieTxt;
    TextView runningCalorieTxt;
    TextView cyclingCalorieTxt;
    TextView swimmingCalorieTxt;
    TextView otherCalorieTxt;
    TextView totalCalorieTxt;
    Button resetBtn;
    String userId;
    String firstName;
    int stepCount;
    int runningDuration;
    int cyclingDuration;
    int swimmingDuration;
    int otherDuration;
    double BMR;
    double walkingDistance;
    double walkingDuration;
    double walkingCal;
    double runningCal;
    double cyclingCal;
    double swimmingCal;
    double otherCal;
    double totalCal;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report_page);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        runningDurationTxt = findViewById(R.id.RunningDuration);
        cyclingDurationTxt = findViewById(R.id.CyclingDuration);
        swimmingDurationTxt = findViewById(R.id.SwimmingDuration);
        otherDurationTxt = findViewById(R.id.OtherDuration);
        walkingCalorieTxt = findViewById(R.id.WalkingCalories);
        runningCalorieTxt = findViewById(R.id.RunningCalories);
        cyclingCalorieTxt = findViewById(R.id.CyclingCalories);
        swimmingCalorieTxt = findViewById(R.id.SwimmingCalories);
        otherCalorieTxt = findViewById(R.id.OtherCalories);
        totalCalorieTxt = findViewById(R.id.TotalCalories);
        resetBtn = findViewById(R.id.ResetBtn);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("MeasuredValues").hasChild(userId)){
                    BMR = dataSnapshot.child("MeasuredValues").child(userId).child("BMR").getValue(Double.class);
                    runningDuration = dataSnapshot.child("WorkoutDuration").child(userId).child("running").getValue(Integer.class);
                    cyclingDuration = dataSnapshot.child("WorkoutDuration").child(userId).child("cycling").getValue(Integer.class);
                    swimmingDuration = dataSnapshot.child("WorkoutDuration").child(userId).child("swimming").getValue(Integer.class);
                    otherDuration = dataSnapshot.child("WorkoutDuration").child(userId).child("other").getValue(Integer.class);
                    firstName = dataSnapshot.child("Users").child(userId).child("FirstName").getValue(String.class);
                    stepCount = dataSnapshot.child("StepCount").child(userId).getValue(Integer.class);

                    runningDurationTxt.setText(String.valueOf(runningDuration));
                    cyclingDurationTxt.setText(String.valueOf(cyclingDuration));
                    swimmingDurationTxt.setText(String.valueOf(swimmingDuration));
                    otherDurationTxt.setText(String.valueOf(otherDuration));

                    walkingCalorie(stepCount,BMR);
                    runningCalorie(runningDuration,BMR);
                    cyclingCalorie(cyclingDuration,BMR);
                    swimmingCalorie(swimmingDuration,BMR);
                    otherCalorie(otherDuration,BMR);
                    totalCalorie(walkingCal,runningCal,cyclingCal,swimmingCal,otherCal);

                    walkingCalorieTxt.setText(String.valueOf(walkingCal));
                    runningCalorieTxt.setText(String.valueOf(runningCal));
                    cyclingCalorieTxt.setText(String.valueOf(cyclingCal));
                    swimmingCalorieTxt.setText(String.valueOf(swimmingCal));
                    otherCalorieTxt.setText(String.valueOf(otherCal));
                    totalCalorieTxt.setText(String.valueOf(totalCal));
                    databaseReference.child("LeaderBoard").child("Calories").child(userId).setValue(totalCal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public double walkingCalorie(int StepCount, double BMRVal){
        walkingDistance = StepCount*0.743;
        walkingDuration = walkingDistance/80;
        walkingCal = round(3.5*BMRVal*walkingDuration/60/24,2);
        return walkingCal;
    }

    public double runningCalorie(int RunningDuration, double BMRVal){
        runningCal = round(11*BMRVal*RunningDuration/60/24,2);
        return runningCal;
    }

    public double cyclingCalorie(int CyclingDuration, double BMRVal){
        cyclingCal = round(6.8*BMRVal*CyclingDuration/60/24,2);
        return cyclingCal;
    }

    public double swimmingCalorie(int SwimmingDuration, double BMRVal){
        swimmingCal = round(9.5*BMRVal*SwimmingDuration/60/24,2);
        return swimmingCal;
    }

    public double otherCalorie(int OtherDuration, double BMRVal){
        otherCal = round(5*BMRVal*OtherDuration/60/24,2);
        return otherCal;
    }

    public double totalCalorie(double WalkingCal, double RunningCal, double CyclingCal, double SwimmingCal, double OtherCal){
        totalCal = round(WalkingCal+RunningCal+CyclingCal+SwimmingCal+OtherCal,2);
        return totalCal;
    }

    public void Reset(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DailyReportPage.this);
        alertDialog.setMessage("Are you sure to reset the workout data?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WorkoutDuration workoutDuration = new WorkoutDuration(0,0,0,0);
                        databaseReference.child("WorkoutDuration").child(userId).setValue(workoutDuration);
                        databaseReference.child("StepCount").child(userId).setValue(0);
                        databaseReference.child("LeaderBoard").child("Calories").child(userId).setValue(0);
                        Intent intent = new Intent(DailyReportPage.this, GoToPage.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.setTitle("Data reset");
        alert.show();
    }
}
