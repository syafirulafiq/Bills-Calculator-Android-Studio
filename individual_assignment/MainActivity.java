package com.example.individual_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private EditText unitsEditText, rebateEditText;
    private Button calculateButton, resetButton;
    private TextView resultTextView, totalChargesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        unitsEditText = findViewById(R.id.unitsEditText);
        rebateEditText = findViewById(R.id.rebateEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);
        totalChargesTextView = findViewById(R.id.totalChargesTextView);
        resultTextView = findViewById(R.id.resultTextView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBill();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle profile menu item click
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.instruction) {
            // Handle instruction menu item click
            Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void calculateBill() {

        String unitsString = unitsEditText.getText().toString().trim();
        String rebateString = rebateEditText.getText().toString().trim();

        if (unitsString.isEmpty() || rebateString.isEmpty()) {
            showAlert("Error", "Please enter valid values.");
            return;
        }
        int unitsUsed = Integer.parseInt(unitsEditText.getText().toString());
        float rebatePercentage = Float.parseFloat(rebateEditText.getText().toString());

        float totalCharges = 0.0f;

        if (unitsUsed <= 200) {
            totalCharges = unitsUsed * 0.218f;
        } else if (unitsUsed <= 300) {
            totalCharges = 200 * 0.218f + (unitsUsed - 200) * 0.334f;
        } else if (unitsUsed <= 600) {
            totalCharges = 200 * 0.218f + 100 * 0.334f + (unitsUsed - 300) * 0.516f;
        } else if (unitsUsed > 600) {
            totalCharges = 200 * 0.218f + 100 * 0.334f + 300 * 0.516f + (unitsUsed - 600) * 0.546f;
        }

        // Apply the rebate percentage
        float finalCost = totalCharges - (totalCharges * (rebatePercentage / 100.0f));

        // Display the calculated values in the output fields
        totalChargesTextView.setText(String.format("RM %.2f", totalCharges));
        resultTextView.setText(String.format("RM %.2f", finalCost));
    }

    private void resetFields() {
        unitsEditText.setText("");
        rebateEditText.setText("");
        totalChargesTextView.setText("");
        resultTextView.setText("");
    }
    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}