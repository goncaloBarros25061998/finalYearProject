package com.example.finalyearproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RideAdapter extends ArrayAdapter<Ride> {

    public RideAdapter(@NonNull Context context, List<Ride> rides) {
        super(context, 0, rides);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Ride ride = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ride_cell, parent, false);
        }
        TextView rideCellTV = convertView.findViewById(R.id.rideCellTV);

        String rideTitle = ride.getLocation() + " " + ride.getDay() + " " + ride.getMonth() + " " + ride.getYear();
        rideCellTV.setText(rideTitle);
        return convertView;
    }
}
