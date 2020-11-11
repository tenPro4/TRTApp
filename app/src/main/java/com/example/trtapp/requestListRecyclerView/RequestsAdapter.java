package com.example.trtapp.requestListRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trtapp.DriverAvailableActivity;
import com.example.trtapp.HistorySingleActivity;
import com.example.trtapp.R;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestViewHolders> {
    private List<RequestObject> itemList;
    private Context context;

    public RequestsAdapter(List<RequestObject> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_item,viewGroup,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        RequestViewHolders rcv = new RequestViewHolders(view);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolders holder, int i) {
        final RequestObject req = itemList.get(i);
        holder.cusName.setText(req.getName());
        holder.cusDestination.setText(req.getDestination());
        holder.cusService.setText(req.getService());
        holder.assignDriverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DriverAvailableActivity.class);
                Bundle b = new Bundle();
                b.putString("requestId", req.getRequestId());
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
