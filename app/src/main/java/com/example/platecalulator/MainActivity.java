package com.example.platecalulator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.TextUtils;
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
        Button clearBarBtn = (Button)findViewById(R.id. clearBarBtn);

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

        clearBarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clearBar();
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

        //gets textviews for the method
        TextView plate45 = (TextView)findViewById(R.id.weightFourtyFive);
        TextView plate35 = (TextView)findViewById(R.id.weightThirtyfive);
        TextView plate25 = (TextView)findViewById(R.id.weightTwentyFive);
        TextView plate10 = (TextView)findViewById(R.id.weightTen);
        TextView plate5 = (TextView)findViewById(R.id.weightFive);
        TextView plate2Half = (TextView)findViewById(R.id.weightTwoHalf);

        // these two arrays need to be the exact same lenght or it won't work right.  In the future
        // I will set it up so that this will be done automatically
        int plateArr[] = {0, 0, 0, 0, 0, 0};

        // To better clarify is the array that sets what weights are availabe.  this needs to be
        // sorted from largest to smallest for it to work correctly, I'm not going  to make this
        // automatic in the future, it's not hard to do.
        final double plateWeight[] = {45, 35, 25, 10, 5, 2.5};


        //used to itterate the plateWeight array
        int j = 0;

        //this loop figures out how many plates are need on each side
        for(int i = 0; i < plateArr.length; i++)
        {

            //subtracts the highest possible weight from the total weight
            while(weight>=plateWeight[j])
            {
                weight = weight - plateWeight[j];
                plateArr[i]++;
            }

            j++;
        }

        //sets the textviews based off of the counters
        plate45.setText(String.valueOf(plateArr[0]));
        plate35.setText(String.valueOf(plateArr[1]));
        plate25.setText(String.valueOf(plateArr[2]));
        plate10.setText(String.valueOf(plateArr[3]));
        plate5.setText(String.valueOf(plateArr[4]));
        plate2Half.setText(String.valueOf(plateArr[5]));

    }

    //clears the input
    private void clearBar()
    {
        EditText weightInput = (EditText)findViewById(R.id.weightInput);

        // So this mess I found on line gets the text from teh edit text and sees if it's empty
        if (TextUtils.isEmpty(weightInput.getText().toString()))
        {
            Toast.makeText(MainActivity.this, "Already empty", Toast.LENGTH_LONG).show();
        }
        else{
            weightInput.getText().clear();
        }

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
