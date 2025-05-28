package com.example.bpproject;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class KanbanCardDetailActivity extends AppCompatActivity {

    private TextView clientNameTextView, dateTextView;
    private ProgressBar progressBar;
    private Button saveButton;
    private CheckBox checkStep1, checkStep2, checkStep3, checkStep4;

    private int progress = 0;

    // Keys for savedInstanceState bundle
    private static final String KEY_PROGRESS = "progress";
    private static final String KEY_CHECK1 = "check1";
    private static final String KEY_CHECK2 = "check2";
    private static final String KEY_CHECK3 = "check3";
    private static final String KEY_CHECK4 = "check4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commission_card);

        // Initialize views
        clientNameTextView = findViewById(R.id.clientNameTextView);
        dateTextView = findViewById(R.id.dateTextView);
        progressBar = findViewById(R.id.progressBar);
        saveButton = findViewById(R.id.saveButton);

        checkStep1 = findViewById(R.id.checkStep1);
        checkStep2 = findViewById(R.id.checkStep2);
        checkStep3 = findViewById(R.id.checkStep3);
        checkStep4 = findViewById(R.id.checkStep4);

        // Set progressBar max explicitly
        progressBar.setMax(100);

        // Get intent extras safely
        Intent intent = getIntent();
        String clientName = intent.getStringExtra("clientName");
        String date = intent.getStringExtra("date");
        progress = intent.getIntExtra("progress", 0);

        clientNameTextView.setText(clientName != null ? clientName : "Unknown Client");
        dateTextView.setText(date != null ? date : "Unknown Date");

        if (savedInstanceState != null) {
            // Restore checkbox states and progress
            checkStep1.setChecked(savedInstanceState.getBoolean(KEY_CHECK1, false));
            checkStep2.setChecked(savedInstanceState.getBoolean(KEY_CHECK2, false));
            checkStep3.setChecked(savedInstanceState.getBoolean(KEY_CHECK3, false));
            checkStep4.setChecked(savedInstanceState.getBoolean(KEY_CHECK4, false));
            progress = savedInstanceState.getInt(KEY_PROGRESS, progress);
            progressBar.setProgress(progress);
            updateProgressBarColor(progress);
        } else {
            // First load, update checkboxes & progress bar from progress value
            updateCheckboxes(progress);
        }

        // Listener for checkboxes to update progress dynamically
        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            progress = calculateProgressFromCheckboxes();
            progressBar.setProgress(progress);
            updateProgressBarColor(progress);
        };

        // Attach listener to all checkboxes
        checkStep1.setOnCheckedChangeListener(listener);
        checkStep2.setOnCheckedChangeListener(listener);
        checkStep3.setOnCheckedChangeListener(listener);
        checkStep4.setOnCheckedChangeListener(listener);

        // Save button action - sends updated progress back
        saveButton.setOnClickListener(v -> {
            int finalProgress = calculateProgressFromCheckboxes();
            Intent result = new Intent();
            result.putExtra("listName", intent.getStringExtra("listName"));
            result.putExtra("position", intent.getIntExtra("position", -1));
            result.putExtra("progress", finalProgress);
            setResult(RESULT_OK, result);

            Toast.makeText(this, "Progress saved!", Toast.LENGTH_SHORT).show();

            finish(); // Close this activity and return to previous
        });
    }

    // Update checkboxes according to progress percentage
    private void updateCheckboxes(int progress) {
        checkStep1.setChecked(progress >= 25);
        checkStep2.setChecked(progress >= 50);
        checkStep3.setChecked(progress >= 75);
        checkStep4.setChecked(progress == 100);

        progressBar.setProgress(progress);
        updateProgressBarColor(progress);
    }

    // Calculate progress percent based on checked steps
    private int calculateProgressFromCheckboxes() {
        int steps = 4;
        int done = 0;
        if (checkStep1.isChecked()) done++;
        if (checkStep2.isChecked()) done++;
        if (checkStep3.isChecked()) done++;
        if (checkStep4.isChecked()) done++;
        return (done * 100) / steps;
    }

    // Set progress bar color dynamically based on progress value
    private void updateProgressBarColor(int progress) {
        int colorResId;
        if (progress == 100) {
            colorResId = R.color.green;  // Completed
        } else if (progress >= 50) {
            colorResId = R.color.orange; // Halfway done
        } else {
            colorResId = R.color.red;    // Less than halfway
        }
        int color = ContextCompat.getColor(this, colorResId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setProgressTintList(ColorStateList.valueOf(color));
        } else {
            // For pre-Lollipop, fallback: No color change or use other method if needed
        }
    }

    // Save checkbox states & progress to handle device rotations or config changes
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_CHECK1, checkStep1.isChecked());
        outState.putBoolean(KEY_CHECK2, checkStep2.isChecked());
        outState.putBoolean(KEY_CHECK3, checkStep3.isChecked());
        outState.putBoolean(KEY_CHECK4, checkStep4.isChecked());
        outState.putInt(KEY_PROGRESS, progress);
    }

    // When user presses back button, also save progress and return it
    @Override
    public void onBackPressed() {
        int finalProgress = calculateProgressFromCheckboxes();
        Intent result = new Intent();
        result.putExtra("listName", getIntent().getStringExtra("listName"));
        result.putExtra("position", getIntent().getIntExtra("position", -1));
        result.putExtra("progress", finalProgress);
        setResult(RESULT_OK, result);
        super.onBackPressed();
    }
}
