package com.example.braveen.fit_health_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BioPage extends AppCompatActivity {

    EditText firstNameTxt;
    EditText lastNameTxt;
    EditText ageTxt;
    EditText heightTxt;
    EditText weightTxt;
    Spinner genderTxt;
    Button saveBtn;
    String userId;
    String firstName;
    String lastName;
    String gender;
    int age;
    double height;
    double weight;
    boolean saved;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_page);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        firstNameTxt = findViewById(R.id.BioPageFirstNameTxt);
        lastNameTxt = findViewById(R.id.BioPageLastNameTxt);
        genderTxt = findViewById(R.id.BioPageGenderTxt);
        ageTxt = findViewById(R.id.BioPageAgeTxt);
        heightTxt = findViewById(R.id.BioPageHeightTxt);
        weightTxt = findViewById(R.id.BioPageWeightTxt);
        saveBtn = findViewById(R.id.BioPageSaveBtn);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,R.array.gender_array,android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderTxt.setAdapter(genderAdapter);

        databaseReference.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstName = dataSnapshot.child("firstName").getValue(String.class);
                lastName = dataSnapshot.child("lastName").getValue(String.class);
                gender = dataSnapshot.child("gender").getValue(String.class);
                age = dataSnapshot.child("age").getValue(Integer.class);
                height = dataSnapshot.child("height").getValue(Double.class);
                weight = dataSnapshot.child("weight").getValue(Double.class);
                firstNameTxt.setText(firstName);
                lastNameTxt.setText(lastName);
                if (gender.equals("Male")){
                    genderTxt.setSelection(0);
                }
                else{
                    genderTxt.setSelection(1);
                }
                ageTxt.setText(String.valueOf(age));
                heightTxt.setText(String.valueOf(height));
                weightTxt.setText(String.valueOf(weight));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveBio(){
        saved = true;
        if (ageTxt.getText().toString().trim().length() == 0){
            ageTxt.setText("0");
        }

        if (heightTxt.getText().toString().trim().length() == 0){
            heightTxt.setText("0");
        }

        if (weightTxt.getText().toString().trim().length() == 0){
            weightTxt.setText("0");
        }

        String FirstName = firstNameTxt.getText().toString().trim();
        String LastName = lastNameTxt.getText().toString().trim();
        String Gender = genderTxt.getSelectedItem().toString().trim();
        String Age = ageTxt.getText().toString().trim();
        String Height = heightTxt.getText().toString().trim();
        String Weight = weightTxt.getText().toString().trim();

        if (TextUtils.isEmpty(FirstName)){
            saved = false;
            Toast.makeText(this, "Please enter your first name!", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (TextUtils.isEmpty(LastName)){
            saved = false;
            Toast.makeText(this, "Please enter your last name!", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (Height.matches("(\\d)*[.](\\d)*[.](.)*")){
            saved = false;
            Toast.makeText(this, "Please enter a valid height!", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (Weight.matches("(\\d)*[.](\\d)*[.](.)*")){
            saved = false;
            Toast.makeText(this, "Please enter a valid weight!", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            saved = true;
            UserBio userBio = new UserBio(FirstName,LastName,Gender,Integer.parseInt(Age),Double.parseDouble(Height),Double.parseDouble(Weight));
            databaseReference.child("Users").child(userId).setValue(userBio);
            databaseReference.child("LeaderBoard").child("Name").child(userId).setValue(FirstName);
            databaseReference.child("FitnessGoal").child(userId).child("currentWeight").setValue(Double.parseDouble(Weight));
            Toast.makeText(BioPage.this, "Successfully saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void SaveUserBio(View view){
        saveBio();
        if (saved){
            Intent intent = new Intent(this, GoToPage.class);
            startActivity(intent);
        }
    }
}
