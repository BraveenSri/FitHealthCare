package com.example.braveen.fit_health_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetTargetPage extends AppCompatActivity {

    TextView currentWeightTxt;
    TextView expectedPeriodTxt;
    EditText goalWeightTxt;
    Spinner dietTxt;
    ImageView awfulImg;
    ImageView badImg;
    ImageView okImg;
    ImageView goodImg;
    ImageView greatImg;
    String userId;
    String diet;
    int period;
    int expectedPeriod;
    double BMR;
    double calories;
    double intake;
    double weightLoss;
    double currentWeight;
    double goalWeight;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_target_page);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        expectedPeriodTxt = findViewById(R.id.ExpectedPeriod);
        currentWeightTxt = findViewById(R.id.CurrentWeight);
        goalWeightTxt = findViewById(R.id.GoalWeight);
        dietTxt = findViewById(R.id.Diet);
        awfulImg = findViewById(R.id.AwfulImg);
        badImg = findViewById(R.id.BadImg);
        okImg = findViewById(R.id.OkImg);
        goodImg = findViewById(R.id.GoodImg);
        greatImg = findViewById(R.id.GreatImg);

        ArrayAdapter<CharSequence> dietAdapter = ArrayAdapter.createFromResource(this,R.array.diet_array,android.R.layout.simple_spinner_item);
        dietAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietTxt.setAdapter(dietAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BMR = dataSnapshot.child("MeasuredValues").child(userId).child("BMR").getValue(Double.class);
                calories = dataSnapshot.child("LeaderBoard").child("Calories").child(userId).getValue(Double.class);
                currentWeight = dataSnapshot.child("FitnessGoal").child(userId).child("currentWeight").getValue(Double.class);
                goalWeight = dataSnapshot.child("FitnessGoal").child(userId).child("goalWeight").getValue(Double.class);
                diet = dataSnapshot.child("FitnessGoal").child(userId).child("diet").getValue(String.class);
                expectedPeriod = dataSnapshot.child("FitnessGoal").child(userId).child("expectedPeriod").getValue(Integer.class);
                currentWeightTxt.setText(String.valueOf(currentWeight));
                goalWeightTxt.setText(String.valueOf(goalWeight));
                expectedPeriodTxt.setText(String.valueOf(expectedPeriod));
                if (diet.equals("Awful")){
                    dietTxt.setSelection(0);
                }
                else if (diet.equals("Bad")){
                    dietTxt.setSelection(1);
                }
                else if (diet.equals("Ok")){
                    dietTxt.setSelection(2);
                }
                else if (diet.equals("Good")){
                    dietTxt.setSelection(3);
                }
                else{
                    dietTxt.setSelection(4);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void CalculatePeriod(View view){
        String Diet = dietTxt.getSelectedItem().toString().trim();
        String GoalWeight = goalWeightTxt.getText().toString().trim();

        if (TextUtils.isEmpty(GoalWeight)){
            Toast.makeText(this, "Please enter the goal weight!", Toast.LENGTH_SHORT).show();
            expectedPeriodTxt.setText("0");
            return;
        }

        databaseReference.child("FitnessGoal").child(userId).child("diet").setValue(Diet);
        databaseReference.child("FitnessGoal").child(userId).child("goalWeight").setValue(Double.parseDouble(GoalWeight));

        calPeriod(Diet,BMR,calories,currentWeight,Double.parseDouble(GoalWeight));

        expectedPeriodTxt.setText(String.valueOf(period));
        databaseReference.child("FitnessGoal").child(userId).child("expectedPeriod").setValue(period);
    }

    public int calPeriod(String Diet, double BMRVal, double Calories, double CurrentWeight, double GoalWeight){
        if (Diet.equals("Awful")){
            intake = BMRVal*0.7;
        }
        else if (Diet.equals("Bad")){
            intake = BMRVal*0.525;
        }
        else if (Diet.equals("Ok")){
            intake = BMRVal*0.35;
        }
        else if (Diet.equals("Good")){
            intake = BMRVal*0.175;
        }
        else{
            intake = 0;
        }
        weightLoss = (Calories - intake)/6318;
        if (weightLoss >= 0){
            period = (int)(round((CurrentWeight - GoalWeight)/weightLoss,0));
        }
        else{
            period = 0;
        }
        return period;
    }

    public double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
