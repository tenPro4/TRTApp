package com.example.trtapp.driverListRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trtapp.R;

public class DriverViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Button driverId;
    public TextView driverName,driverService;
    public ImageView driverImage;

    public DriverViewHolders(@NonNull View itemView) {
        super(itemView);

        driverId = itemView.findViewById(R.id.driverId);
        driverName = itemView.findViewById(R.id.driverName);
        driverImage = itemView.findViewById(R.id.driver_image);
        driverService = itemView.findViewById(R.id.driverService);
    }

    @Override
    public void onClick(View v) {
        //assign driverId
        Log.i("ClickItem","true");
    }
}
