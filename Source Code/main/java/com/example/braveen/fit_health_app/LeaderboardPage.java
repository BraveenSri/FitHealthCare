package com.example.braveen.fit_health_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;


public class LeaderboardPage extends AppCompatActivity {

    ListView rankListView;
    ListView nameListView;
    ListView calListView;
    String userId;
    int count;
    int maxIndex;
    double cal;
    ArrayList<String> rankList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> calorieList = new ArrayList<>();
    ArrayList<String> orderedNameList = new ArrayList<>();
    ArrayList<String> orderedCalList = new ArrayList<>();
    ArrayList<Double> calList = new ArrayList<>();
    ArrayAdapter<String> rankAdapter;
    ArrayAdapter<String> nameAdapter;
    ArrayAdapter<String> calorieAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_page);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        rankListView = findViewById(R.id.rankList);
        nameListView = findViewById(R.id.nameList);
        calListView = findViewById(R.id.calorieList);

        databaseReference.child("LeaderBoard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot nameSnapshot : dataSnapshot.child("Name").getChildren()){
                    String nameVal = nameSnapshot.getValue(String.class);
                    nameList.add(nameVal);
                }

                for (DataSnapshot calorieSnapshot : dataSnapshot.child("Calories").getChildren()){
                    String calorieVal = String.valueOf(calorieSnapshot.getValue(Double.class));
                    calorieList.add(calorieVal);
                }

                count = nameList.size();
                for (int i=1; i < count+1; i++){
                    rankList.add(String.valueOf(i));
                }

                for (int i = 0; i < count; i++){
                    cal = Double.parseDouble(calorieList.get(i));
                    calList.add(cal);
                }

                for (int i = 0; i< count; i++){
                    maxIndex = calList.indexOf(Collections.max(calList));
                    orderedCalList.add(String.valueOf(calList.get(maxIndex)));
                    orderedNameList.add(nameList.get(maxIndex));
                    calList.remove(maxIndex);
                    nameList.remove(maxIndex);
                }

                rankAdapter = new ArrayAdapter<String>(LeaderboardPage.this, android.R.layout.simple_list_item_1, rankList);
                nameAdapter = new ArrayAdapter<String>(LeaderboardPage.this, android.R.layout.simple_list_item_1, orderedNameList);
                calorieAdapter = new ArrayAdapter<String>(LeaderboardPage.this, android.R.layout.simple_list_item_1, orderedCalList);
                rankListView.setAdapter(rankAdapter);
                nameListView.setAdapter(nameAdapter);
                calListView.setAdapter(calorieAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
