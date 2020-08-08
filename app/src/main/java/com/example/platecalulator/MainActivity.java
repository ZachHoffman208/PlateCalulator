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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //make sure to make this match the arrays in setPlates
    public static final int NUMBEROFPLATES = 6;

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

    //splits the weight then calls the function to figure out the plates
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

        //this is the number of each plates that are set
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

        //clears the images of the plates that are zero
        hidePlates(plateArr);

    }

    private void hidePlates(int [] plateArr)
    {
        //45 stuff
        TextView plate45 = (TextView)findViewById(R.id.weightFourtyFive);
        ImageView plate45XImg = (ImageView)findViewById(R.id.x45Img);
        ImageView plate45Img = (ImageView)findViewById(R.id.fourtyfivePlateImg);

        //35 stuff
        TextView plate35 = (TextView)findViewById(R.id.weightThirtyfive);
        ImageView plate35XImg = (ImageView)findViewById(R.id.x35Img);
        ImageView plate35Img = (ImageView)findViewById(R.id.thirtyfivePlateImg);

        //25 stuff
        TextView plate25 = (TextView)findViewById(R.id.weightTwentyFive);
        ImageView plate25XImg = (ImageView)findViewById(R.id.x25Img);
        ImageView plate25Img = (ImageView)findViewById(R.id.twentyfivePlateImg);

        //10 stuff
        TextView plate10 = (TextView)findViewById(R.id.weightTen);
        ImageView plate10XImg = (ImageView)findViewById(R.id.x10Img);
        ImageView plate10Img = (ImageView)findViewById(R.id.tenPlateImg);

        //5 stuff
        TextView plate5 = (TextView)findViewById(R.id.weightFive);
        ImageView plate5XImg = (ImageView)findViewById(R.id.x5Img);
        ImageView plate5Img = (ImageView)findViewById(R.id.fivePlateImg);

        //2.5 stuff
        TextView plate2Half = (TextView)findViewById(R.id.weightTwoHalf);
        ImageView plate2HalfXImg = (ImageView)findViewById(R.id.x2HalfImg);
        ImageView plate2HalfImg = (ImageView)findViewById(R.id.twoHalfPlateImg);

        // this is going to have to be clunky for this to work since I'm working with assests that
        // are in the main display

        //ifelse statement 45
        if(plateArr[0] == 0)
        {
            plate45.setVisibility(View.GONE);
            plate45XImg.setVisibility(View.GONE);
            plate45Img.setVisibility(View.GONE);
        }
        else
        {
            plate45.setVisibility(View.VISIBLE);
            plate45XImg.setVisibility(View.VISIBLE);
            plate45Img.setVisibility(View.VISIBLE);
        }

        //ifelse statement 35
        if(plateArr[1] == 0)
        {
            plate35.setVisibility(View.GONE);
            plate35XImg.setVisibility(View.GONE);
            plate35Img.setVisibility(View.GONE);
        }
        else
        {
            plate35.setVisibility(View.VISIBLE);
            plate35XImg.setVisibility(View.VISIBLE);
            plate35Img.setVisibility(View.VISIBLE);
        }

        //ifelse statement 25
        if(plateArr[2] == 0)
        {
            plate25.setVisibility(View.GONE);
            plate25XImg.setVisibility(View.GONE);
            plate25Img.setVisibility(View.GONE);
        }
        else
        {
            plate25.setVisibility(View.VISIBLE);
            plate25XImg.setVisibility(View.VISIBLE);
            plate25Img.setVisibility(View.VISIBLE);
        }

        //ifelse statement 10
        if(plateArr[3] == 0)
        {
            plate10.setVisibility(View.GONE);
            plate10XImg.setVisibility(View.GONE);
            plate10Img.setVisibility(View.GONE);
        }
        else
        {
            plate10.setVisibility(View.VISIBLE);
            plate10XImg.setVisibility(View.VISIBLE);
            plate10Img.setVisibility(View.VISIBLE);
        }

        //ifelse statement 5
        if(plateArr[4] == 0)
        {
            plate5.setVisibility(View.GONE);
            plate5XImg.setVisibility(View.GONE);
            plate5Img.setVisibility(View.GONE);
        }
        else
        {
            plate5.setVisibility(View.VISIBLE);
            plate5XImg.setVisibility(View.VISIBLE);
            plate5Img.setVisibility(View.VISIBLE);
        }

        //ifelse statement 2.5
        if(plateArr[5] == 0)
        {
            plate2Half.setVisibility(View.GONE);
            plate2HalfXImg.setVisibility(View.GONE);
            plate2HalfImg.setVisibility(View.GONE);
        }
        else
        {
            plate2Half.setVisibility(View.VISIBLE);
            plate2HalfXImg.setVisibility(View.VISIBLE);
            plate2HalfImg.setVisibility(View.VISIBLE);
        }

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
