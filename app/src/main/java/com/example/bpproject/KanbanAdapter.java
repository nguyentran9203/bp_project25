package com.example.bpproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KanbanAdapter extends RecyclerView.Adapter<KanbanAdapter.ViewHolder> {

    private final List<KanbanDashboardActivity.Commission> commissionList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public KanbanAdapter(List<KanbanDashboardActivity.Commission> commissionList, OnItemClickListener listener) {
        this.commissionList = commissionList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceNameTextView;

        TextView clientNameTextView, dateTextView;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clientNameTextView = itemView.findViewById(R.id.clientNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);

            progressBar = itemView.findViewById(R.id.progressBar); // 👈 This
        }

    }


    @NonNull
    @Override
    public KanbanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kanban_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KanbanAdapter.ViewHolder holder, int position) {
        KanbanDashboardActivity.Commission commission = commissionList.get(position);
        holder.clientNameTextView.setText(commission.clientName);
        holder.dateTextView.setText(commission.date);
        holder.progressBar.setProgress(commission.progressPercent); // 👈 Add this
        holder.serviceNameTextView.setText(commission.serviceName);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), KanbanCardDetailActivity.class);
            intent.putExtra("clientName", commission.clientName);
            intent.putExtra("date", commission.date);
            intent.putExtra("progress", commission.progressPercent);

            intent.putExtra("position", holder.getAdapterPosition()); // to update laterholder.serviceNameTextView.text = commission.serviceName

            v.getContext().startActivity(intent);

        });
    }


    @Override
    public int getItemCount() {
        return commissionList.size();
    }

    public void moveItem(int from, int to) {
        KanbanDashboardActivity.Commission item = commissionList.remove(from);
        commissionList.add(to, item);
        notifyItemMoved(from, to);
    }
}
