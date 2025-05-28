package com.example.bpproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class FillFormActivity extends AppCompatActivity {

    RadioGroup radioGroupUsage, radioGroupShare;
    Button buttonUploadFile, buttonAddLink, buttonPickDate, buttonSubmit, buttonQuoteCalculator;
    LinearLayout layoutUploadedItems;
    TextView textPickedDate;

    EditText editExtraInfo;

    private String pickedDate = null;

    // To hold uploaded file URIs or added links
    private LinearLayout.LayoutParams itemLayoutParams;

    private ActivityResultLauncher<String> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_form);

        radioGroupUsage = findViewById(R.id.radioGroupUsage);
        radioGroupShare = findViewById(R.id.radioGroupShare);
        buttonUploadFile = findViewById(R.id.buttonUploadFile);
        buttonAddLink = findViewById(R.id.buttonAddLink);
        buttonPickDate = findViewById(R.id.buttonPickDate);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        layoutUploadedItems = findViewById(R.id.layoutUploadedItems);
        textPickedDate = findViewById(R.id.textPickedDate);
        editExtraInfo = findViewById(R.id.editExtraInfo);
        buttonQuoteCalculator = findViewById(R.id.buttonOpenCalculator);
        itemLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        itemLayoutParams.setMargins(0, 8, 0, 8);

        // File picker launcher for picking any file type
        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                addUploadedItem("File: " + uri.getLastPathSegment(), uri);
            }
        });

        buttonUploadFile.setOnClickListener(v -> {
            // Open system file picker
            filePickerLauncher.launch("*/*");
        });

        buttonQuoteCalculator.setOnClickListener(v -> {
            Intent intent = new Intent(FillFormActivity.this, QuoteCalculatorActivity.class);
            startActivity(intent);
        });

        buttonAddLink.setOnClickListener(v -> {
            showAddLinkDialog();
        });

        buttonPickDate.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        buttonSubmit.setOnClickListener(v -> {
            submitForm();
        });
    }

    private void addUploadedItem(String text, Uri uri) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setLayoutParams(itemLayoutParams);
        tv.setTag(uri); // store URI for later if needed
        layoutUploadedItems.addView(tv);
    }

    private void addUploadedItem(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setLayoutParams(itemLayoutParams);
        layoutUploadedItems.addView(tv);
    }

    private void showAddLinkDialog() {
        // Simple prompt dialog to input a link
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Add Link")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String link = input.getText().toString().trim();
                    if (!link.isEmpty()) {
                        addUploadedItem("Link: " + link);
                    } else {
                        Toast.makeText(this, "Link cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    // month1 is zero-based
                    pickedDate = String.format("%02d/%02d/%04d", month1 + 1, dayOfMonth, year1);
                    textPickedDate.setText(pickedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void submitForm() {
        int usageId = radioGroupUsage.getCheckedRadioButtonId();
        int shareId = radioGroupShare.getCheckedRadioButtonId();

        if (usageId == -1) {
            Toast.makeText(this, "Please select how you will be using this work", Toast.LENGTH_SHORT).show();
            return;
        }
        if (shareId == -1) {
            Toast.makeText(this, "Please select sharing permission", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton usageRadio = findViewById(usageId);
        RadioButton shareRadio = findViewById(shareId);

        String usage = usageRadio.getText().toString();
        String share = shareRadio.getText().toString();
        String extraInfo = editExtraInfo.getText().toString().trim();

        StringBuilder filesAndLinks = new StringBuilder();
        int count = layoutUploadedItems.getChildCount();
        for (int i = 0; i < count; i++) {
            TextView item = (TextView) layoutUploadedItems.getChildAt(i);
            filesAndLinks.append(item.getText()).append("\n");
        }

        // You can now send this data to your backend or next activity
        String summary = "Usage: " + usage + "\n" +
                "Share: " + share + "\n" +
                "Deadline: " + (pickedDate == null ? "None" : pickedDate) + "\n" +
                "Files/Links:\n" + filesAndLinks.toString() +
                "Extra Info: " + extraInfo;

        Toast.makeText(this, "Form submitted:\n" + summary, Toast.LENGTH_LONG).show();

        // TODO: Implement backend submission or next steps
    }
}
