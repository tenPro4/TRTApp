package com.example.trtapp.driverListRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trtapp.HistorySingleActivity;
import com.example.trtapp.Models.DriverObject;
import com.example.trtapp.R;
import com.example.trtapp.RequestListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.util.HashMap;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverViewHolders>{

    private List<DriverObject> itemList;
    private Context context;

    public DriverAdapter(List<DriverObject> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public DriverViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.driver_item,viewGroup,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        DriverViewHolders rcv = new DriverViewHolders(view);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolders holder, int i) {
        final DriverObject driver = itemList.get(i);
        holder.driverName.setText(driver.getName());
        holder.driverService.setText(driver.getService());
        Picasso.get().load(driver.getProfileImageUrl()).placeholder(R.mipmap.ic_default_user).into(holder.driverImage);
        holder.driverId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, driver.getRequestId(), Toast.LENGTH_SHORT).show();

                final DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers")
                        .child(driver.getDriverId()).child("customerRequest");
                final DatabaseReference reqRef = FirebaseDatabase.getInstance().getReference().child("requestList").child(driver.getRequestId());
                final DatabaseReference cusRef = FirebaseDatabase.getInstance().getReference().child("customerRequest");
                final HashMap map = new HashMap();

                reqRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            final String cusId = dataSnapshot.child("customerRideId").getValue().toString();
                            map.put("customerRideId",cusId);
                            map.put("destination", dataSnapshot.child("destination").getValue().toString());
                            map.put("destinationLat",dataSnapshot.child("destinationLat").getValue().toString());
                            map.put("destinationLng", dataSnapshot.child("destinationLng").getValue().toString());
                            driverRef.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    reqRef.removeValue();
                                    HashMap<String,Object> cusMap = new HashMap<>();
                                    cusMap.put("found",true);
                                    cusMap.put("driverId",driver.getDriverId());
                                    cusRef.child(cusId).updateChildren(cusMap);
                                }
                            });

                            Intent intent = new Intent(context, RequestListActivity.class);
                            context.startActivity(intent);

                            Toast.makeText(context, "Assign Success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
