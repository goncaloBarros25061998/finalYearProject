package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FindRide extends AppCompatActivity {
    //private CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
    private List<DocumentSnapshot> rides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("rides").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    rides = task.getResult().getDocuments();
                    for (int i = 0; i < rides.size(); i++) {
                        Log.i(rides.get(i).getString("location") + " " + rides.get(i).getTimestamp("date").toDate().toString() + " " + rides.get(i).getLong("seats"), "rides");
                    }

                }
            }
        });
    }
}