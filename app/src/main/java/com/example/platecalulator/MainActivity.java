package com.example.platecalulator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public boolean bar = true;
    public double barWeight =45;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculateBtn = (Button)findViewById(R.id.calculateBtn);
        Button changeBarBtn = (Button)findViewById(R.id.changeBarBtn);


        //calculate button
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText weightInput = (EditText)findViewById(R.id.weightInput);

                String weightStr = weightInput.getText().toString();

                if (weightStr.isEmpty())
                    {
                        Toast.makeText(MainActivity.this, "Enter a Number", Toast.LENGTH_LONG).show();
                    }

                else
                    {
                        double weightIn = Double.parseDouble(weightStr);

                        splitWeight(weightIn, barWeight, bar);
                    }

                closeKeyboard();

            }
        });

        //change bar button
        changeBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(MainActivity.this, barSelector.class);

                startActivityForResult(intent, 1);

            }
        });
    }

    //Overrides  the activityResult so it can pass the data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //checks request code
        if (requestCode == 1)
        {
            //checks result code
            if (resultCode == RESULT_OK)
            {
                bar = data.getBooleanExtra("bar", true);
                barWeight = data.getDoubleExtra("barWeight", 45);
            }
        }
    }

    //splits the weight
    private void splitWeight(double weight, double barWeight, boolean bar)
    {
        double passWeight=weight;

        //checks to see if it needs a bar
        if(bar == true)
        {
            //subtracts bar weight then divides it by two
            passWeight = (weight-barWeight)/2;
            setPlates(passWeight);
        }
        else if (bar == false)
        {
            setPlates(passWeight);
        }

    }

    //figures out the plates
    private void setPlates(double weight)
    {
        //initalizes plate counters
        int plate45Int=0;
        int plate35Int=0;
        int plate25Int=0;
        int plate10Int=0;
        int plate5Int=0;
        int plate2HalfInt=0;

        while(weight>=45)
        {
            weight = weight - 45;
            plate45Int++;
        }

        while(weight>=35)
        {
            weight = weight - 35;
            plate35Int++;
        }

        while(weight>=25)
        {
            weight = weight - 25;
            plate25Int++;
        }

        while(weight>=10)
        {
            weight = weight - 10;
            plate10Int++;
        }

        while(weight>=5)
        {
            weight = weight - 5;
            plate5Int++;
        }

        while(weight>=2.5)
        {
            weight = weight -2.5;
            plate2HalfInt++;
        }

        //gets textviews for the method
        TextView plate45 = (TextView)findViewById(R.id.weightFourtyFive);
        TextView plate35 = (TextView)findViewById(R.id.weightThirtyfive);
        TextView plate25 = (TextView)findViewById(R.id.weightTwentyFive);
        TextView plate10 = (TextView)findViewById(R.id.weightTen);
        TextView plate5 = (TextView)findViewById(R.id.weightFive);
        TextView plate2Half = (TextView)findViewById(R.id.weightTwoHalf);

        //sets the textvies based off of the counters
        plate45.setText(String.valueOf(plate45Int));
        plate35.setText(String.valueOf(plate35Int));
        plate25.setText(String.valueOf(plate25Int));
        plate10.setText(String.valueOf(plate10Int));
        plate5.setText(String.valueOf(plate5Int));
        plate2Half.setText(String.valueOf(plate2HalfInt));
    }

    //hides the keyboard
    public void  closeKeyboard()
    {
        View v = this.getCurrentFocus();
        if(v != null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}
