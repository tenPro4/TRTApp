package com.example.trtapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.trtapp.Models.DriverObject;
import com.example.trtapp.driverListRecyclerView.DriverAdapter;
import com.example.trtapp.requestListRecyclerView.RequestObject;
import com.example.trtapp.requestListRecyclerView.RequestsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestListActivity extends AppCompatActivity {

    private RecyclerView mRequestRecyclerView;
    private RecyclerView.Adapter mRequestAdapter;
    private RecyclerView.LayoutManager mRequestLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        mRequestRecyclerView = (RecyclerView) findViewById(R.id.requestList);
        mRequestRecyclerView.setNestedScrollingEnabled(false);
        mRequestRecyclerView.setHasFixedSize(true);
        mRequestLayoutManager = new LinearLayoutManager(RequestListActivity.this);
        mRequestRecyclerView.setLayoutManager(mRequestLayoutManager);
        mRequestAdapter = new RequestsAdapter(getRequestListData(),RequestListActivity.this);
        mRequestRecyclerView.setAdapter(mRequestAdapter);

        findViewById(R.id.logoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        fetchRequestList();
    }

    private void fetchRequestList() {
        DatabaseReference requestsDatabase = FirebaseDatabase.getInstance().getReference().child("requestList");
        requestsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot rq : dataSnapshot.getChildren()){
                        fetchDriverInfo(rq);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchDriverInfo(final DataSnapshot rqSnapshot) {
        final String customerId = rqSnapshot.child("customerRideId").getValue().toString();
        DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Users").child("Customers").child(customerId);
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RequestObject rq = new RequestObject();
                if(dataSnapshot.exists()){
                    rq.setName(dataSnapshot.child("name").getValue().toString());
                    rq.setPhone(dataSnapshot.child("phone").getValue().toString());
                    rq.setProfileImageUrl(dataSnapshot.child("profileImageUrl").getValue().toString());
                }
                rq.setRequestId(rqSnapshot.getKey());
                rq.setCustomerRideId(customerId);
                rq.setDestination(rqSnapshot.child("destination").getValue().toString());
                rq.setService(rqSnapshot.child("service").getValue().toString());
                rq.setDestination(rqSnapshot.child("destination").getValue().toString());
                rq.setDestinationLat(rqSnapshot.child("destinationLat").getValue().toString());
                rq.setDestinationLng(rqSnapshot.child("destinationLng").getValue().toString());

                resultsRequest.add(rq);
                mRequestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private ArrayList resultsRequest = new ArrayList<RequestObject>();
    private List<RequestObject> getRequestListData() {
        return resultsRequest;
    }
}
