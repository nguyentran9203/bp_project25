package com.example.bpproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.Color;
import android.view.ViewGroup.MarginLayoutParams;
import android.util.TypedValue;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;       // For LinearLayout
import android.view.ViewGroup;             // For ViewGroup and MarginLayoutParams

import java.util.Arrays;
import java.util.List;

public class PortfolioActivity extends AppCompatActivity {

    private void addTagPills() {
        LinearLayout tagContainer = findViewById(R.id.tagPillContainer);

        String[] tags = {"All", "Illust", "Design"};

        for (String tag : tags) {
            TextView tagPill = new TextView(this);
            tagPill.setText(tag);
            tagPill.setTextColor(Color.WHITE);
            tagPill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tagPill.setBackgroundResource(R.drawable.tag_pill_background);
            tagPill.setPadding(24, 12, 24, 12);

            // Margin between pills
            MarginLayoutParams params = new MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(12, 0, 12, 0);
            tagPill.setLayoutParams(params);

            tagContainer.addView(tagPill);
        }
    }
    private enum ViewMode {
        HeroCarouselStrategy,
        Masonry
    }

    private Spinner viewModeSpinner;
    private FrameLayout galleryPlaceholder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfolio_main);

        viewModeSpinner = findViewById(R.id.viewModeSpinner);
        galleryPlaceholder = findViewById(R.id.galleryPlaceholder);
        Button commissionsButton, portfolioButton, artistDashboardButton;
        commissionsButton = findViewById(R.id.commissionsButton);
        portfolioButton = findViewById(R.id.portfolioButton);
        artistDashboardButton = findViewById(R.id.btnArtistDashboard);

        commissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PortfolioActivity.this, CommissionsListingActivity.class);
                startActivity(intent);
            }
        });


        portfolioButton.setOnClickListener(v -> {
            Intent intent = new Intent(PortfolioActivity.this, PortfolioActivity.class);
            startActivity(intent);
        });

        artistDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortfolioActivity.this, KanbanDashboardActivity.class);
                startActivity(intent);
            }

        });
        // Add tag pills to layout
        addTagPills();



        // Spinner setup
        List<String> options = Arrays.asList("Hero Carousel", "Masonry Grid");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                options
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewModeSpinner.setAdapter(adapter);

        viewModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ViewMode mode;
                switch (position) {
                    case 0:
                        mode = ViewMode.HeroCarouselStrategy;
                        break;
                    case 1:
                        mode = ViewMode.Masonry;
                        break;
                    default:
                        mode = ViewMode.HeroCarouselStrategy;
                        break;
                }
                showFakeGallery(mode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void showFakeGallery(ViewMode mode) {
        LayoutInflater inflater = getLayoutInflater();
        galleryPlaceholder.removeAllViews();


        int layoutRes;
        switch (mode) {
            case HeroCarouselStrategy:
                layoutRes = R.layout.carousel_gallery;
                break;
            case Masonry:
                layoutRes = R.layout.masonry_gallery;
                break;
            default:
                layoutRes = R.layout.carousel_gallery;
        }

        View view = inflater.inflate(layoutRes, galleryPlaceholder, false);
        galleryPlaceholder.addView(view);
    }
}
