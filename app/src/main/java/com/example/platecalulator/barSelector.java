package com.example.platecalulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class barSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_selector);

        setTitle("Choose Bar");

        //gets buttons in her
        Button olympicBtn = (Button)findViewById(R.id.olympicBtn);
        Button ezBarBtn = (Button)findViewById(R.id.ezBarBtn);
        Button noBarBtn = (Button)findViewById(R.id.noBarBtn);
        Button noBarNoSplitBtn = (Button)findViewById(R.id.noBarNoSplitBtn);

        // all the buttons do is set the bar weight, and the bar boolean
        olympicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain(true, 45);
            }
        });

        ezBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain(true, 25);
            }
        });

        noBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain(true, 0);
            }
        });

        noBarNoSplitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain(false, 0);
            }
        });

    }

    //this passes the information back
    private void goBackToMain(boolean bar, double barWeight)
    {
        Intent intent = new Intent(barSelector.this, MainActivity.class);
        intent.putExtra("bar", bar);
        intent.putExtra("barWeight", barWeight);

        setResult(RESULT_OK, intent);
        finish();
    }

}
