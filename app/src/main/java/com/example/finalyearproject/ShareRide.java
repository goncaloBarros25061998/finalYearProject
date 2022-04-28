package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ShareRide extends AppCompatActivity implements View.OnClickListener{

    private EditText emailEditText, locationEditText, seatsEditText, timeEditText;
    private DatePicker datePicker;
    private Button ridesButton, shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_ride);

        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        locationEditText = (EditText) findViewById(R.id.editTextLocation);
        seatsEditText = (EditText) findViewById(R.id.editTextSeats);
        timeEditText = (EditText) findViewById(R.id.editTextTime);

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        ridesButton = (Button) findViewById(R.id.ridesButton);
        shareButton = (Button) findViewById(R.id.shareButton);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ridesButton:
                startActivity(new Intent(this, SavedRides.class));
                break;
            case R.id.shareButton:
                shareRide();
                break;
        }
    }

    public void shareRide() {
        String email = emailEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String seats = seatsEditText.getText().toString().trim();
        String time = timeEditText.getText().toString().trim();

        String date = datePicker.getDayOfMonth() + "-" + datePicker.getMonth() + "-" + datePicker.getYear();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (email.isEmpty()){
            emailEditText.setError("Email is Reguired!");
            emailEditText.requestFocus();
            return;
        }
        if(location.isEmpty()){
            locationEditText.setError("Location is Requiered");
            locationEditText.requestFocus();
            return;
        }
        if (seats.isEmpty()){
            seatsEditText.setError("Seat number is Reguired!");
            seatsEditText.requestFocus();
            return;
        }
        if(time.isEmpty()){
            timeEditText.setError("Time is Requiered");
            timeEditText.requestFocus();
            return;
        }

        Map<String, Object> ride = new HashMap<>();
        ride.put("date", date);
        ride.put("email", email);
        ride.put("location", location);
        ride.put("seats", seats);
        ride.put("time", time);


        db.collection("rides")
                .add(ride)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Doc written", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Error", "Error adding document", e);
                    }
                });

    }

}