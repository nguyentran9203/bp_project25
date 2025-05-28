package com.example.bpproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuoteCalculatorActivity extends AppCompatActivity {

    private Spinner commissionTypeSpinner;
    private CheckBox additionalCharacterCheckbox, complexBackgroundCheckbox, propsPetsCheckbox,
            priorityDeliveryCheckbox, commercialUseCheckbox;
    private SeekBar detailsSeekBar, charactersSeekBar;
    private TextView totalTextView;

    // Example base prices for commission types, match these to your @array/commission_types order
    private final double[] basePrices = {30.0, 45.0, 60.0, 50.0};
    private final double additionalCharacterPrice = 20.0;
    private final double complexBackgroundPrice = 30.0;
    private final double propsPetsPrice = 15.0;
    private final double priorityDeliveryPrice = 25.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_calculator); // Replace with your actual XML layout file name

        // Bind views
        commissionTypeSpinner = findViewById(R.id.commissionTypeSpinner);
        additionalCharacterCheckbox = findViewById(R.id.additionalCharacterCheckbox);
        complexBackgroundCheckbox = findViewById(R.id.complexBackgroundCheckbox);
        propsPetsCheckbox = findViewById(R.id.propsPetsCheckbox);
        priorityDeliveryCheckbox = findViewById(R.id.priorityDeliveryCheckbox);
        commercialUseCheckbox = findViewById(R.id.commercialUseCheckbox);
        detailsSeekBar = findViewById(R.id.detailsSeekBar);
        charactersSeekBar = findViewById(R.id.charactersSeekBar);
        totalTextView = findViewById(R.id.totalTextView);

        // Set listeners to update total whenever input changes
        commissionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTotal();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        View.OnClickListener checkboxListener = v -> updateTotal();
        additionalCharacterCheckbox.setOnClickListener(checkboxListener);
        complexBackgroundCheckbox.setOnClickListener(checkboxListener);
        propsPetsCheckbox.setOnClickListener(checkboxListener);
        priorityDeliveryCheckbox.setOnClickListener(checkboxListener);
        commercialUseCheckbox.setOnClickListener(checkboxListener);

        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTotal();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        };

        detailsSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        charactersSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        // Initialize charactersSeekBar progress to 0 (means 1 character)
        charactersSeekBar.setProgress(0);

        // Initial total update
        updateTotal();
    }

    private void updateTotal() {
        // Get base price from commission type spinner
        int commissionTypeIndex = commissionTypeSpinner.getSelectedItemPosition();
        double basePrice = 0;
        if (commissionTypeIndex >= 0 && commissionTypeIndex < basePrices.length) {
            basePrice = basePrices[commissionTypeIndex];
        }

        // Get detail level multiplier (scale 1-10)
        int detailLevel = detailsSeekBar.getProgress() + 1; // SeekBar max=9, so +1 to make 1-10
        // For example, details increase price by 10% per level above 1:
        double detailMultiplier = 1 + (detailLevel - 1) * 0.1;

        // Number of characters (optional) - minimum 1
        int characterCount = charactersSeekBar.getProgress() + 1;

        // Add-ons prices
        double addOnsPrice = 0;

        if (additionalCharacterCheckbox.isChecked()) {
            // Additional characters: characterCount - 1 * price each
            addOnsPrice += (characterCount) * additionalCharacterPrice;
        }

        if (complexBackgroundCheckbox.isChecked()) {
            addOnsPrice += complexBackgroundPrice;
        }

        if (propsPetsCheckbox.isChecked()) {
            addOnsPrice += propsPetsPrice;
        }

        if (priorityDeliveryCheckbox.isChecked()) {
            addOnsPrice += priorityDeliveryPrice;
        }

        // Calculate subtotal before commercial use
        double subtotal = (basePrice * detailMultiplier) + addOnsPrice;

        // Commercial use doubles the total price (100% increase)
        if (commercialUseCheckbox.isChecked()) {
            subtotal *= 2;
        }

        // Format and display
        String totalString = String.format("$%.2f", subtotal);
        totalTextView.setText(totalString);
    }
}
