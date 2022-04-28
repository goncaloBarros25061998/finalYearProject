package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FindRide extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    //private CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate todayDate;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private User user = MainActivity.getUser();
    private List<Ride> rides = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);
        initWidgets();
        todayDate = LocalDate.now();

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("rides")
                .whereEqualTo("year",new Long(todayDate.getYear()))
                .whereEqualTo("month",new Long(todayDate.getMonthValue()))
                .get().addOnSuccessListener(
                queryDocumentSnapshots -> {
                    rides = queryDocumentSnapshots.toObjects(Ride.class);
                    setMonthView();

                }
        );
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        for (Ride ride : this.rides) {
            LocalDate localDate = LocalDate.of(ride.getYear(),  ride.getMonth(), ride.getDay());
            CalendarUtils.addDate(localDate);
        }
        CalendarUtils.setRides(this.rides);

        monthYearText.setText(CalendarUtils.monthYearFromDate(todayDate));
        ArrayList<LocalDate> daysInMonth = CalendarUtils.daysInMonthArray(todayDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, rides, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        CalendarUtils.resetDates();
        CalendarUtils.addDate(todayDate.minusMonths(1));
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        CalendarUtils.resetDates();
        CalendarUtils.addDate(todayDate.plusMonths(1));
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.addDate(date);
            //System.out.println(CalendarUtils.getRideDates());
            setMonthView();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeekViewActivity.class));
    }

}
