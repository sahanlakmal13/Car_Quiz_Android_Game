package com.example.mad_assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static com.example.mad_assignment_1.Common.carRefs;
import static com.example.mad_assignment_1.Common.getSelectedCarName;

public class AdvanceLevelActivity extends AppCompatActivity {

    private ImageView car1ImageView;
    private ImageView car2ImageView;
    private ImageView car3ImageView;
    private TextView carImage1TextView;
    private TextView carImage2TextView;
    private TextView carImage3TextView;
    private TextView scoreTextView;
    private EditText carImage1TextInput;
    private EditText carImage2TextInput;
    private EditText carImage3TextInput;
    private TextView msgText;
    private Button submitBtn;
    private static final ArrayList<Integer> prevRandoms = new ArrayList<>();
    private final int[] attempts = {0};
    private final int[] correctAttempts = {0};
    private final String[] setCars = new String[3];
    private TextView timerTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_level);

        Bundle extras = getIntent().getExtras();
        boolean switchState = extras.getBoolean("switchState");

        if (switchState){
            startCountdownTimer();
        }

        car1ImageView = findViewById(R.id.carImage1);
        car2ImageView = findViewById(R.id.carImage2);
        car3ImageView = findViewById(R.id.carImage3);
        carImage1TextView = findViewById(R.id.carImage1TextView);
        carImage2TextView = findViewById(R.id.carImage2TextView);
        carImage3TextView = findViewById(R.id.carImage3TextView);
        carImage1TextInput = findViewById(R.id.carImage1TextInput);
        carImage2TextInput = findViewById(R.id.carImage2TextInput);
        carImage3TextInput = findViewById(R.id.carImage3TextInput);
        submitBtn = findViewById(R.id.submitButton);
        msgText = findViewById(R.id.msgTextView);
        ColorStateList oldColor =  carImage1TextInput.getTextColors();
        ColorStateList oldColor2 =  carImage1TextInput.getHintTextColors();
        scoreTextView = findViewById(R.id.scoreTextView);
        final int[] score = {0};
        timerTextView = findViewById(R.id.timerTextView);

        setRandomImages(carRefs);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText[] carTextInputs = {carImage1TextInput, carImage2TextInput, carImage3TextInput};
                TextView[] carTextViews = {carImage1TextView,carImage2TextView,carImage3TextView};

                if (submitBtn.getText().equals("Next")) {

                    for (int i = 0; i < carTextInputs.length; i++) {
                        carTextInputs[i].setEnabled(true);
                        carTextInputs[i].setTextColor(oldColor);
                        carTextInputs[i].setHintTextColor(oldColor2);
                        carTextInputs[i].setText("");
                        carTextViews[i].setText("");
                    }

                    msgText.setText("");
                    setRandomImages(carRefs);
                    attempts[0]=0;
                    correctAttempts[0]=0;
                    submitBtn.setText(R.string.submit);

                    if (switchState){
                        startCountdownTimer();
                    }
                }

                else if (submitBtn.getText().equals("Submit")){
                    attempts[0]++;
                    for (int i=0; i<carTextInputs.length; i++){
                        if (carTextInputs[i].getText().toString().toLowerCase().equals(setCars[i])){
                            if (carTextInputs[i].isEnabled()){
                                correctAttempts[0]++;
                                score[0]++;
                            }
                            carTextInputs[i].setTextColor(Color.parseColor("#07EF0B"));
                            carTextInputs[i].setEnabled(false);
                        }
                        else {
                            if (carTextInputs[i].getText().toString().isEmpty()){

                                int c = carTextInputs[i].getCurrentHintTextColor();
                                carTextInputs[i].setHintTextColor(Color.RED);
                                int finalI = i;
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        carTextInputs[finalI].setHintTextColor(c);
                                    }
                                }, 500);
                            }
                            else {
                                carTextInputs[i].setTextColor(Color.RED);
                            }
                            if (attempts[0]==3){
                                carTextViews[i].setText(setCars[i].substring(0, 1).toUpperCase() + setCars[i].substring(1));
                            }
                        }
                    }

                    if (correctAttempts[0]==3){
                        msgText.setTextColor(Color.parseColor("#07EF0B"));
                        msgText.setText(R.string.correct);
                        submitBtn.setText(R.string.next);
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                    }
                    else if (attempts[0]==3){
                        msgText.setTextColor(Color.RED);
                        msgText.setText(R.string.wrong);
                        submitBtn.setText(R.string.next);
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                    }
                    scoreTextView.setText(getString(R.string.score)+score[0]);
                    System.out.println(correctAttempts[0]);
                    if (attempts[0]<3){
                        if (switchState){
                            startCountdownTimer();
                        }
                    }
                }
            }
        });
    }

    private void startCountdownTimer() { //https://stackoverflow.com/questions/12608614/how-to-use-a-textview-to-countdown-to-zero
        if (countDownTimer != null) {
            countDownTimer.cancel();// stopping any previous countdowns still working
        }
        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText(getString(R.string.secRemain)+ millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTextView.setText(R.string.timeOver);
                if (submitBtn.getText().equals("Submit")){
                    submitBtn.performClick();
                }
            }
        };
        countDownTimer.start();
    }

    private void setRandomImages(int[] carRefs){

        ImageView[] imageViews = {car1ImageView, car2ImageView, car3ImageView};

        for (int i=0; i<3; i++){
            Random rand = new Random();
            int randomNum = rand.nextInt(carRefs.length);
            if (prevRandoms.size() != 0){
                for (int k : prevRandoms){
                    if (randomNum == k){
                        randomNum = rand.nextInt(carRefs.length);
                    }
                }
            }
            prevRandoms.add(randomNum);
            imageViews[i].setImageResource(carRefs[randomNum]);
            setCars[i] = getSelectedCarName(carRefs,randomNum,imageViews[i]);
            if (i==2){
                if (setCars[0].equals(setCars[1]) || setCars[0].equals(setCars[2]) || setCars[1].equals(setCars[2])){
                    i=0;
                    continue;
                }
            }

        }
    }
}