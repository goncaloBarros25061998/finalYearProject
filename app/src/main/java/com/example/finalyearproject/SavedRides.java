package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class SavedRides extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private User user = MainActivity.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_rides);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        firebaseFirestore = FirebaseFirestore.getInstance();


        Query query = firebaseFirestore.collection("rides").whereEqualTo("email", user.getEmail());
        //System.out.println(query.get().toString());
        FirestoreRecyclerOptions<Ride> options = new FirestoreRecyclerOptions.Builder<Ride>()
                .setQuery(query, Ride.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Ride, RideViewHolder>(options) {
            @NonNull
            @Override
            public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_view, parent, false);
                return new RideViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RideViewHolder holder, int position, @NonNull Ride model) {
                holder.locationView.setText(model.getLocation());
                holder.dateView.setText(model.getDay() + "-" + model.getMonth() + "-" + model.getYear());
                holder.timeView.setText(model.getTime());
                holder.seatsView.setText(model.getSeats());
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public void onStart() {
        super.onStart();
        this.adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.adapter.stopListening();
    }

    private class RideViewHolder extends RecyclerView.ViewHolder {
        private TextView locationView;
        private TextView dateView;
        private TextView timeView;
        private TextView seatsView;

        public RideViewHolder(@NonNull View itemView) {
            super(itemView);
            locationView = itemView.findViewById(R.id.locationView);
            dateView = itemView.findViewById(R.id.dateView);
            timeView = itemView.findViewById(R.id.timeView);
            seatsView = itemView.findViewById(R.id.seatsView);
        }
    }
}