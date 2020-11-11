package com.example.trtapp.requestListRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trtapp.R;

public class RequestViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView cusName,cusService,cusDestination;
    public Button assignDriverBtn;


    public RequestViewHolders(@NonNull View itemView) {
        super(itemView);

        cusName = itemView.findViewById(R.id.cusName);
        cusService = itemView.findViewById(R.id.cusService);
        cusDestination = itemView.findViewById(R.id.cusLocation);
        assignDriverBtn = itemView.findViewById(R.id.assignDriverBtn);
    }


    @Override
    public void onClick(View v) {
        Log.i("RequestIsClicked","true");
    }
}
