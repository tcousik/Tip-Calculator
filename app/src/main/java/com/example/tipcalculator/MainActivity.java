package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTotalAmount;
    private EditText editTaxAmount;
    private RadioGroup radioGroupTip;
    private RadioButton radioButton0Percent;
    private RadioButton radioButton5Percent;
    private RadioButton radioButton10Percent;
    private RadioButton radioButton20Percent;
    private TextView textTipAmount;
    private TextView textGrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTotalAmount = findViewById(R.id.editTotalAmount);
        editTaxAmount = findViewById(R.id.editTaxAmount);
        radioGroupTip = findViewById(R.id.radioGroupTip);
        radioButton0Percent = findViewById(R.id.radioButton0Percent);
        radioButton5Percent = findViewById(R.id.radioButton5Percent);
        radioButton10Percent = findViewById(R.id.radioButton10Percent);
        radioButton20Percent = findViewById(R.id.radioButton20Percent);
        textTipAmount = findViewById(R.id.textTipAmount);
        textGrandTotal = findViewById(R.id.textGrandTotal);

        // Set default values
        editTotalAmount.setText("");
        editTaxAmount.setText("");
        radioButton0Percent.setChecked(true);

        // Set click listener for the "Calculate" button
        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGrandTotal();
            }
        });

        // Set click listener for the "Clear" button
        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearClicked(v);
            }
        });
    }

    public void clearClicked(View view) {
        editTotalAmount.setText("");
        editTaxAmount.setText("");
        radioButton0Percent.setChecked(true);
        textTipAmount.setText(getString(R.string.tip_amount, "$0.00"));
        textGrandTotal.setText(getString(R.string.grand_total, "$0.00"));
    }

    private void calculateGrandTotal() {
        double totalAmount, taxAmount, tipPercentage, tipAmount, grandTotal;

        try {
            totalAmount = Double.parseDouble(editTotalAmount.getText().toString());
        } catch (NumberFormatException e) {
            totalAmount = 0.00;
        }

        try {
            taxAmount = Double.parseDouble(editTaxAmount.getText().toString());
        } catch (NumberFormatException e) {
            taxAmount = 0.00;
        }

        int selectedRadioButtonId = radioGroupTip.getCheckedRadioButtonId();
        switch (selectedRadioButtonId) {
            case R.id.radioButton0Percent:
                tipPercentage = 0.00;
                break;
            case R.id.radioButton5Percent:
                tipPercentage = 0.05;
                break;
            case R.id.radioButton10Percent:
                tipPercentage = 0.10;
                break;
            case R.id.radioButton20Percent:
                tipPercentage = 0.20;
                break;
            default:
                tipPercentage = 0.00;
        }

        tipAmount = totalAmount * tipPercentage;
        grandTotal = totalAmount + taxAmount + tipAmount;

        // Format and display results
        DecimalFormat currencyFormat = new DecimalFormat("#,###.00");
        String formattedTipAmount = "$" + currencyFormat.format(tipAmount);
        String formattedGrandTotal = "$" + currencyFormat.format(grandTotal);

        textTipAmount.setText(getString(R.string.tip_amount, formattedTipAmount));
        textGrandTotal.setText(getString(R.string.grand_total, formattedGrandTotal));
    }
}
