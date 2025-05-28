package com.example.bpproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Button artistDashboardButton;
    FloatingActionButton messageButton;
    ImageView profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Your main layout


        artistDashboardButton = findViewById(R.id.btnArtistDashboard);
        profileButton = findViewById(R.id.profileBtn);
        messageButton = findViewById(R.id.fabMessages);
        messageButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(MainActivity.this, MessagesActivity.class);
                                                 startActivity(intent);
                                             }
                                         });


        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ArtistProfileActivity.class);
            startActivity(intent);
        });



        artistDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KanbanDashboardActivity.class);
                startActivity(intent);
            }

        });
    }
}

