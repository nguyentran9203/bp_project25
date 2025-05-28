package com.example.bpproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ArtistProfileActivity extends AppCompatActivity {

    // UI Components
    private TextView artistDisplayName;
    private TextView artistUsername;
    private TextView artistBio;
    private TextView ratingText;
    ImageView image;
    TextView price;
    TextView service;
    Button artistDashboardButton, commissionButton, portfolioBtn;

    FloatingActionButton messageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_profile); // Assuming your XML is named activity_artist_profile.xml

        artistDashboardButton = findViewById(R.id.btnArtistDashboard);
        artistDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistProfileActivity.this, KanbanDashboardActivity.class);
                startActivity(intent);
            }

       });
        portfolioBtn = findViewById(R.id.portfolioButton);
        portfolioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistProfileActivity.this, PortfolioActivity.class);
                startActivity(intent);
            }

        });
        commissionButton = findViewById(R.id.commissionsButton);
        commissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistProfileActivity.this, CommissionsListingActivity.class);
                startActivity(intent);
            }

        });

        messageButton = findViewById(R.id.fabMessages);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistProfileActivity.this, MessagesActivity.class);
                startActivity(intent);
            }
            });

        // Initialize views
        initializeViews();


        // Load artist data (you would replace this with your actual data loading logic)
        loadArtistData();


    }

    private void initializeViews() {
        artistDisplayName = findViewById(R.id.artistDisplayName);
        artistUsername = findViewById(R.id.artistUsername);
        artistBio = findViewById(R.id.artistBio);
        ratingText = findViewById(R.id.ratingText);

        price = findViewById(R.id.textPrice);
        service = findViewById(R.id.textServiceName);
        image = findViewById(R.id.imageCommission);

    }


    private void loadArtistData() {
        // In a real app, you would fetch this data from a database or API
        // These are just placeholder values
        artistDisplayName.setText("Jane Doe");
        artistUsername.setText("@jane.doe");
        artistBio.setText("Digital artist specializing in character design and illustrations.");
        ratingText.setText("4.8 ★ (23 reviews)");
    }




}


