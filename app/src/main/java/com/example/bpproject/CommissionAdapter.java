package com.example.bpproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommissionAdapter extends RecyclerView.Adapter<CommissionAdapter.CommissionViewHolder> {

    private Context context;
    private List<CommissionItem> commissionList;

    public CommissionAdapter(Context context, List<CommissionItem> commissionList) {
        this.context = context;
        this.commissionList = commissionList;
    }

    @NonNull
    @Override
    public CommissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_commission, parent, false);
        return new CommissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommissionViewHolder holder, int position) {
        CommissionItem item = commissionList.get(position);
        holder.service.setText(item.getServiceName());
        holder.price.setText(item.getPrice());
        holder.image.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return commissionList.size();
    }

    public static class CommissionViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView service;
        TextView price;

        public CommissionViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageCommission);
            service = itemView.findViewById(R.id.textServiceName);
            price = itemView.findViewById(R.id.textPrice);
        }
    }
}
