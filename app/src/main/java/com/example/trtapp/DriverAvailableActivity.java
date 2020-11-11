package com.example.trtapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trtapp.Models.DriverObject;
import com.example.trtapp.driverListRecyclerView.DriverAdapter;
import com.example.trtapp.historyRecyclerView.HistoryAdapter;
import com.example.trtapp.historyRecyclerView.HistoryObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverAvailableActivity extends AppCompatActivity {

    private RecyclerView mDriverRecyclerView;
    private RecyclerView.Adapter mDriverAdapter;
    private RecyclerView.LayoutManager mDriverLayoutManager;
    private String requestId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_available);

        mDriverRecyclerView = (RecyclerView) findViewById(R.id.driversAvailableList);
        mDriverRecyclerView.setNestedScrollingEnabled(false);
        mDriverRecyclerView.setHasFixedSize(true);
        mDriverLayoutManager = new LinearLayoutManager(DriverAvailableActivity.this);
        mDriverRecyclerView.setLayoutManager(mDriverLayoutManager);
        mDriverAdapter = new DriverAdapter(getAvailableDrivers(),DriverAvailableActivity.this);
        mDriverRecyclerView.setAdapter(mDriverAdapter);

        requestId = getIntent().getExtras().getString("requestId");

        fetchDriverAvailable();
    }

    private void fetchDriverAvailable() {
        DatabaseReference driversDatabase = FirebaseDatabase.getInstance().getReference().child("driversAvailable");
        driversDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot driver : dataSnapshot.getChildren()){
                        fetchDriverInformation(driver.getKey());
//                        driverObject.setName(driver.child("name").getValue().toString());
//                        driverObject.setProfileImageUrl(driver.child("profileImageUrl").getValue().toString());
//                        driverObject.setPhone(driver.child("phone").getValue().toString());
//                        driverObject.setService(driver.child("service").getValue().toString());

//                        DriverObject driverObject = driver.getValue(DriverObject.class);
//                        driverObject.setDriverId(driver.getKey());

//                        resultsDriver.add(driverObject);
//                        mDriverAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchDriverInformation(final String key) {
        DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(key);
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    DriverObject driverObject = new DriverObject();
                    driverObject.setName(dataSnapshot.child("name").getValue().toString());
                    driverObject.setProfileImageUrl(dataSnapshot.child("profileImageUrl").getValue().toString());
                    driverObject.setPhone(dataSnapshot.child("phone").getValue().toString());
                    driverObject.setService(dataSnapshot.child("service").getValue().toString());
                    driverObject.setRequestId(requestId);
                    driverObject.setDriverId(key);

                    resultsDriver.add(driverObject);
                    mDriverAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ArrayList resultsDriver = new ArrayList<DriverObject>();
    private List<DriverObject> getAvailableDrivers() {
        return resultsDriver;
    }


}
