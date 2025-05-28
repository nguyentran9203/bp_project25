package com.example.bpproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommissionsListingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Commission> commissionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commissions_listing);

        recyclerView = findViewById(R.id.recyclerViewCommissions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button commissionsButton, portfolioButton, artistDashboardButton;
        commissionsButton = findViewById(R.id.commissionsButton);
        portfolioButton = findViewById(R.id.portfolioButton);
        artistDashboardButton = findViewById(R.id.btnArtistDashboard);

        commissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommissionsListingActivity.this, CommissionsListingActivity.class);
                startActivity(intent);
            }
        });


        portfolioButton.setOnClickListener(v -> {
            Intent intent = new Intent(CommissionsListingActivity.this, PortfolioActivity.class);
            startActivity(intent);
        });

        artistDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommissionsListingActivity.this, KanbanDashboardActivity.class);
                startActivity(intent);
            }

        });
        // Sample data
        commissionList = new ArrayList<>();
        commissionList.add(new Commission(R.drawable.character_illustration, "Character Illustration", "From $30"));
        commissionList.add(new Commission(R.drawable.character_design, "Character Design", "From $50"));


        CommissionsAdapter adapter = new CommissionsAdapter(commissionList);
        recyclerView.setAdapter(adapter);
    }

    // Inner data class (no separate model)
    public static class Commission {
        int imageResId;
        String serviceName;
        String price;

        public Commission(int imageResId, String serviceName, String price) {
            this.imageResId = imageResId;
            this.serviceName = serviceName;
            this.price = price;
        }
    }

    // RecyclerView Adapter
    class CommissionsAdapter extends RecyclerView.Adapter<CommissionsAdapter.CommissionViewHolder> {

        List<Commission> commissions;

        public CommissionsAdapter(List<Commission> commissions) {
            this.commissions = commissions;
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
            Commission commission = commissions.get(position);
            holder.imageView.setImageResource(commission.imageResId);
            holder.textServiceName.setText(commission.serviceName);
            holder.textPrice.setText(commission.price);

            holder.buttonFillForm.setOnClickListener(v -> {
                // Handle "Fill Form" button click
                Intent intent = new Intent(CommissionsListingActivity.this, FillFormActivity.class);
                intent.putExtra("serviceName", commission.serviceName);
                intent.putExtra("price", commission.price);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return commissions.size();
        }

        class CommissionViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView textServiceName, textPrice;
            Button buttonFillForm;

            public CommissionViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageCommission);
                textServiceName = itemView.findViewById(R.id.textServiceName);
                textPrice = itemView.findViewById(R.id.textPrice);
                buttonFillForm = itemView.findViewById(R.id.buttonFillForm);
            }
        }
    }
}
