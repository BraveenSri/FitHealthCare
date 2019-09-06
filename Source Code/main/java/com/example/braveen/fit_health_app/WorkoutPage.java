package com.example.braveen.fit_health_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkoutPage extends AppCompatActivity {

    EditText runningTxt;
    EditText cyclingTxt;
    EditText swimmingTxt;
    EditText otherTxt;
    Button saveBtn;
    String userId;
    int running;
    int cycling;
    int swimming;
    int other;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_page);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        runningTxt = findViewById(R.id.RunningDuration);
        cyclingTxt = findViewById(R.id.CyclingDuration);
        swimmingTxt = findViewById(R.id.SwimmingDuration);
        otherTxt = findViewById(R.id.OtherDuration);
        saveBtn = findViewById(R.id.SaveDuration);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                running = dataSnapshot.child("WorkoutDuration").child(userId).child("running").getValue(Integer.class);
                cycling = dataSnapshot.child("WorkoutDuration").child(userId).child("cycling").getValue(Integer.class);
                swimming = dataSnapshot.child("WorkoutDuration").child(userId).child("swimming").getValue(Integer.class);
                other = dataSnapshot.child("WorkoutDuration").child(userId).child("other").getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void SaveWorkoutDuration(View view){
        if (runningTxt.getText().toString().trim().length() == 0){
            runningTxt.setText("0");
        }

        if (cyclingTxt.getText().toString().trim().length() == 0){
            cyclingTxt.setText("0");
        }

        if (swimmingTxt.getText().toString().trim().length() == 0){
            swimmingTxt.setText("0");
        }

        if (otherTxt.getText().toString().trim().length() == 0){
            otherTxt.setText("0");
        }

        String Running = runningTxt.getText().toString().trim();
        String Cycling = cyclingTxt.getText().toString().trim();
        String Swimming = swimmingTxt.getText().toString().trim();
        String Other = otherTxt.getText().toString().trim();

        WorkoutDuration workoutDuration = new WorkoutDuration(Integer.parseInt(Running)+running,Integer.parseInt(Cycling)+cycling,Integer.parseInt(Swimming)+swimming,Integer.parseInt(Other)+other);
        databaseReference.child("WorkoutDuration").child(userId).setValue(workoutDuration);

        Toast.makeText(WorkoutPage.this, "Successfully saved", Toast.LENGTH_SHORT).show();

        runningTxt.setText("0");
        cyclingTxt.setText("0");
        swimmingTxt.setText("0");
        otherTxt.setText("0");
    }
}
