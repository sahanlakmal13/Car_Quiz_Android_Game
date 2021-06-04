package com.example.mad_assignment_1;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Random;

import static com.example.mad_assignment_1.Common.carRefs;
import static com.example.mad_assignment_1.Common.getSelectedCarName;

public class IdentifyTheCarMakeActivity extends AppCompatActivity {

    private Spinner spinner;
    private ImageView imageView;
    private Button identifyBtn;
    private final ArrayList<Integer> prevRandoms = new ArrayList<>();
    private TextView msgTextView;
    private TextView carNameTextView;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_car_make);

        Bundle extras = getIntent().getExtras();
        boolean switchState = extras.getBoolean("switchState");

        if (switchState){
            startTimer();
        }

        spinner = findViewById(R.id.spinner);
        imageView = findViewById(R.id.identifyTheCarMakeImageView);
        identifyBtn = findViewById(R.id.identifyMakeBtn);
        msgTextView =findViewById(R.id.msgTextView);
        carNameTextView = findViewById(R.id.carNameTextView);
        timerTextView=findViewById(R.id.timerTextView);

        setRandomImage(carRefs, -1);

        String[] carNames ={"Audi","Bentley","Ferrari","Lamborghini","BMW","Chevrolet","Dodge","Jaguar","Porsche"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,carNames);

        spinner.setAdapter(arrayAdapter);
        
        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(prevRandoms.get(prevRandoms.size()-1));

                if (identifyBtn.getText().equals("Next")){

                    setRandomImage(carRefs, prevRandoms.get(prevRandoms.size()-1));
                    identifyBtn.setText(R.string.identify);
                    msgTextView.setVisibility(View.INVISIBLE);
                    carNameTextView.setVisibility(View.INVISIBLE);
                    spinner.setSelection(0);
                    if (switchState){
                        startTimer();
                    }
                }

                else if (identifyBtn.getText().equals("Identify")){

                    String selectedCarMake = carNames[(int) spinner.getSelectedItemId()];
                    String selectedCar = selectedCarMake.toLowerCase();
                    String imageViewCarName = getSelectedCarName(carRefs, prevRandoms.get(prevRandoms.size()-1),imageView);

                    if (selectedCar.equals(imageViewCarName)){
                        msgTextView.setText(R.string.correct);
                        msgTextView.setTextColor(Color.parseColor("#07EF0B"));
                        msgTextView.setVisibility(View.VISIBLE);
                    }

                    else {
                        msgTextView.setText(R.string.wrong);
                        msgTextView.setTextColor(Color.RED);
                        msgTextView.setVisibility(View.VISIBLE);
                        String name = imageViewCarName.substring(0, 1).toUpperCase() + imageViewCarName.substring(1); //https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
                        carNameTextView.setText(name);
                        carNameTextView.setVisibility(View.VISIBLE);
                    }
                    identifyBtn.setText(R.string.next);
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }
            }
        });
    }

    private void startTimer() { //https://stackoverflow.com/questions/12608614/how-to-use-a-textview-to-countdown-to-zero
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText(getString(R.string.secRemain) + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTextView.setText(R.string.timeOver);
                if (identifyBtn.getText().equals("Identify")){
                    identifyBtn.performClick();
                }
            }
        };
        countDownTimer.start();
    }

    public void setRandomImage(int[] carRefs, int previousRan){

        Random rand = new Random();
        int randomNum = rand.nextInt(carRefs.length);

        if (previousRan != -1){
            for (int i : prevRandoms){
                if (randomNum == i){
                    randomNum = rand.nextInt(carRefs.length);
                }
            }
        }
        prevRandoms.add(randomNum);
        imageView.setImageResource(carRefs[randomNum]);
    }
}