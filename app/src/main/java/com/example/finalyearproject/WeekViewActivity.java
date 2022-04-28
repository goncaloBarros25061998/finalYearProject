package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private List<DocumentSnapshot> ridesDocuments;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView()
    {
        monthYearText.setText(CalendarUtils.monthYearFromDate(CalendarUtils.getRideDates().get(0)));
        ArrayList<LocalDate> days = CalendarUtils.daysInWeekArray(CalendarUtils.getRideDates().get(0));

        //CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
       // calendarRecyclerView.setAdapter(calendarAdapter);
        setRideAdpater();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view)
    {
        CalendarUtils.addDate(CalendarUtils.getRideDates().get(0).minusWeeks(1));
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view)
    {
        CalendarUtils.addDate(CalendarUtils.getRideDates().get(0).plusWeeks(1));
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        // TODO Mostrar pagina de detalhes de boleia
        //CalendarUtils.addDate(date);
        //setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setRideAdpater();
    }

    private void setRideAdpater()
    {
        List<Ride> rides = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("rides").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ridesDocuments = task.getResult().getDocuments();

                    for (int i = 0; i < ridesDocuments.size(); i++) {
                        DocumentSnapshot rideDoc = ridesDocuments.get(i);
                        Ride ride = new Ride(
                                rideDoc.getString("location"),
                                rideDoc.getString("time"),
                                rideDoc.getString("email"),
                                rideDoc.getString("seats"),
                                rideDoc.getLong("day").intValue(),
                                rideDoc.getLong("month").intValue(),
                                rideDoc.getLong("year").intValue()
                        );
                        rides.add(ride);
                    }
                }
            }
        });
        RideAdapter eventAdapter = new RideAdapter(getApplicationContext(), rides);
        eventListView.setAdapter(eventAdapter);
    }
}