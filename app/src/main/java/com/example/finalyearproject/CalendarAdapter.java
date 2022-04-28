package com.example.finalyearproject;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final List<Ride> rides;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, List<Ride> rides, OnItemListener onItemListener)
    {
        this.days = days;
        this.rides = rides;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (days.size() > 15) { //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        } else { //week view
            layoutParams.height = parent.getHeight();
        }
        return new CalendarViewHolder(view, this.onItemListener, this.days);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {

        LocalDate date = days.get(position);
        if (date == null) {
            holder.dayOfMonth.setText("");
        } else {
            for (LocalDate rideDate : CalendarUtils.getRideDates()) {
                if (rideDate.getDayOfMonth() == date.getDayOfMonth()
                        && rideDate.getMonthValue() == date.getMonthValue()) {
                    holder.parentView.setBackgroundColor(Color.LTGRAY);
                }
            }
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
