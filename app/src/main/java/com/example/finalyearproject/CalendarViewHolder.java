package com.example.finalyearproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private ArrayList<LocalDate> days;
    public View parentView;
    public TextView dayOfMonth;
    private CalendarAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
    {
        super(itemView);
        this.parentView = itemView.findViewById(R.id.parentView);
        this.dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view)
    {
        System.out.println();
        List<Ride> rides = CalendarUtils.getRides();
        LocalDate date = days.get(getAbsoluteAdapterPosition());
        int counter = 0;
        int id = 0;
        System.out.println(date.getDayOfMonth());
        System.out.println(date.getMonthValue());
        for (LocalDate ride : CalendarUtils.getRideDates()) {
            if (ride.getDayOfMonth() == date.getDayOfMonth()
                    && ride.getMonthValue() == date.getMonthValue()) {
                id = counter;

            }
            counter++;
        }
        Intent intent = new Intent(parentView.getContext(), RideDetailsActivity.class);
        intent.putExtra("RIDE_ID", String.valueOf(id));
        parentView.getContext().startActivity(intent);


        Ride ride = rides.get(id);
        System.out.println(ride.getLocation());
        onItemListener.onItemClick(getAbsoluteAdapterPosition(), days.get(getAbsoluteAdapterPosition()));
    }
}