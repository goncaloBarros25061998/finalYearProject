package com.example.finalyearproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class RideDetailsActivity extends AppCompatActivity {

    TextView locationView, dateView, seatsView, timeView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);
        String rideId = getIntent().getStringExtra("RIDE_ID");
        Ride ride = CalendarUtils.getRides().get(Integer.valueOf(rideId));
        locationView = (TextView) findViewById(R.id.locationView);
        dateView = (TextView) findViewById(R.id.dateView);
        seatsView = (TextView) findViewById(R.id.seatsView);
        timeView = (TextView) findViewById(R.id.timeView);

        locationView.setText(ride.getLocation());
        dateView.setText(ride.getDay() + "-" + ride.getMonth() + "-" + ride.getYear());
        seatsView.setText(ride.getSeats());
        timeView.setText(ride.getTime());
    }
}