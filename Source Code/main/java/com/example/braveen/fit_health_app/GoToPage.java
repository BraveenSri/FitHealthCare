package com.example.braveen.fit_health_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class GoToPage extends AppCompatActivity implements SensorEventListener {

    TextView BMITxt;
    TextView BMRTxt;
    TextView stepCountTxt;
    String userId;
    String gender;
    int age;
    int steps;
    int stepCount;
    double height;
    double weight;
    double BMIValue;
    double BMRValue;
    boolean walking = false;
    Calendar calendar;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    SensorManager sensorManager;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_page);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        BMITxt = findViewById(R.id.BMIVal);
        BMRTxt = findViewById(R.id.BMRVal);
        stepCountTxt = findViewById(R.id.StepCount);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sharedPreferences = getApplicationContext().getSharedPreferences("My_Preferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,00);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").hasChild(userId)){
                    gender = dataSnapshot.child("Users").child(userId).child("gender").getValue(String.class);
                    age = dataSnapshot.child("Users").child(userId).child("age").getValue(Integer.class);
                    height = dataSnapshot.child("Users").child(userId).child("height").getValue(Double.class);
                    weight = dataSnapshot.child("Users").child(userId).child("weight").getValue(Double.class);
                    stepCount = dataSnapshot.child("StepCount").child(userId).getValue(Integer.class);
                    stepCountTxt.setText(String.valueOf(stepCount) + " steps");
                    measureBMI(weight,height);
                    measureBMR(weight,height,gender,age);
                    BMITxt.setText(String.valueOf(BMIValue));
                    BMRTxt.setText(String.valueOf(BMRValue));
                    MeasuredValue measuredValue = new MeasuredValue(BMIValue, BMRValue);
                    databaseReference.child("MeasuredValues").child(userId).setValue(measuredValue);
                }
                else{
                    UserBio userBio = new UserBio("","","Male",0,0,0);
                    WorkoutDuration workoutDuration = new WorkoutDuration(0,0,0,0);
                    FitnessGoal fitnessGoal = new FitnessGoal(0,0,"Ok",0);
                    databaseReference.child("Users").child(userId).setValue(userBio);
                    databaseReference.child("WorkoutDuration").child(userId).setValue(workoutDuration);
                    databaseReference.child("StepCount").child(userId).setValue(0);
                    databaseReference.child("LeaderBoard").child("Name").child(userId).setValue("");
                    databaseReference.child("LeaderBoard").child("Calories").child(userId).setValue(0);
                    databaseReference.child("FitnessGoal").child(userId).setValue(fitnessGoal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        walking = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else{
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        walking = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (walking){
            steps = (int)sensorEvent.values[0];
            editor.putInt("steps", steps);
            editor.commit();
            databaseReference.child("StepCount").child(userId).setValue(steps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void EditBio(View view){
        Intent intent = new Intent(this, BioPage.class);
        startActivity(intent);
    }

    public void LeaderBoard(View view){
        Intent intent = new Intent(this, LeaderboardPage.class);
        startActivity(intent);
    }

    public void Exit(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GoToPage.this);
        alertDialog.setMessage("Are you sure to sign out from the application?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth.signOut();
                        Intent intent = new Intent(GoToPage.this, SignInPage.class);
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
        alert.setTitle("Sign Out");
        alert.show();
    }

    public void DailyReport(View view){
        Intent intent = new Intent(this, DailyReportPage.class);
        startActivity(intent);
    }

    public void Target(View view){
        Intent intent = new Intent(this, SetTargetPage.class);
        startActivity(intent);
    }

    public void Workout(View view){
        Intent intent = new Intent(this, WorkoutPage.class);
        startActivity(intent);
    }

    public  double measureBMI(double Weight, double Height){
        BMIValue = round(Weight/(Height*Height/10000),2);
        return BMIValue;
    }

    public double measureBMR(double Weight, double Height, String Gender, int Age){
        if (Gender.equals("Male")){
            if (Age == 0){
                BMRValue = round(0,2);
            }
            else {
                BMRValue = round((10*Weight)+(6.25*Height)-(5*Age)+5,2);
            }
        }
        else if (Gender.equals("Female")){
            if (Age == 0){
                BMRValue = round(0,2);
            }
            else {
                BMRValue = round((10*Weight)+(6.25*Height)-(5*Age)-161,2);
            }
        }
        return BMRValue;
    }

    public double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}