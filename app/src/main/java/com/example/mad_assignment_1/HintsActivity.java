package com.example.mad_assignment_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.example.mad_assignment_1.Common.carRefs;
import static com.example.mad_assignment_1.Common.getSelectedCarName;

public class HintsActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button submitBtn;
    private TextView carNameAsHints;
    private TextView correctNameTextView;
    private TextView hintsMsgTextView;
    private TextView attemptsTextView;
    private EditText editText;
    private static final ArrayList<Integer> prevRandoms = new ArrayList<>();
    private ArrayList<String> nameList;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private final int[] count = {3,0};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        Bundle extras = getIntent().getExtras();
        boolean switchState = extras.getBoolean("switchState");

        if (switchState){
            startCountdownTimer();
        }

        imageView = findViewById(R.id.hintsImageView);
        submitBtn = findViewById(R.id.hintsSubmitButton);
        carNameAsHints =findViewById(R.id.hintsTextView);
        correctNameTextView =findViewById(R.id.correctNameTextView);
        hintsMsgTextView =findViewById(R.id.hintsMsgTextView);
        editText = findViewById(R.id.hintsEditText);
        nameList = new ArrayList<>();
        //textView8=findViewById(R.id.textView8);
        attemptsTextView =findViewById(R.id.attemptsTextView);
        timerTextView = findViewById(R.id.timerTextView);

        setRandomImage(carRefs, -1);

        String carName = getSelectedCarName(carRefs, prevRandoms.get(prevRandoms.size()-1),imageView);

        setCarNameAsHints(carName);

        attemptsTextView.setText(R.string.incAttempts);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (submitBtn.getText().equals("Next")){

                        attemptsTextView.setText(R.string.incAttempts);
                        editText.setVisibility(View.VISIBLE);
                        nameList.clear();
                        System.out.println(nameList.size());
                        setRandomImage(carRefs, prevRandoms.get(prevRandoms.size()-1));
                        String carName = getSelectedCarName(carRefs, prevRandoms.get(prevRandoms.size()-1),imageView);
                        System.out.println(carName);
                        setCarNameAsHints(carName);
                        submitBtn.setText(R.string.submit);
                        hintsMsgTextView.setVisibility(View.INVISIBLE);
                        correctNameTextView.setVisibility(View.INVISIBLE);
                        count[0]=3;
                        if (switchState){
                            startCountdownTimer();
                        }
                    }

                    else if (submitBtn.getText().equals("Submit")){

                        count[1]++;
                        String name = String.valueOf(editText.getText()).toLowerCase();
                        editText.setText("");

                        if (count[0] > 1){
                            if (name.isEmpty()){
                                count[0]--;
                                int c = editText.getCurrentHintTextColor();
                                editText.setHintTextColor(Color.RED);
                                attemptsTextView.setText("You have "+count[0]+" incorrect attempts left!");
                                new Handler().postDelayed(new Runnable() { //https://stackoverflow.com/questions/18712955/i-want-to-change-the-color-of-a-button-for-a-few-seconds-than-change-it-back

                                    public void run() {
                                        editText.setHintTextColor(c);
                                    }
                                }, 500);
                            }
                            else {
                                boolean flag = checkEnteredLetter(getSelectedCarName(carRefs, prevRandoms.get(prevRandoms.size()-1),imageView), name);
                                if (!flag){
                                    count[0]--;
                                    attemptsTextView.setText("You have "+count[0]+" incorrect attempts left!");
                                }
                            }
                        }
                        else {
                            attemptsTextView.setText("Incorrect attempts are over,try again!");
                            submitBtn.setText(R.string.next);
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            editText.setVisibility(View.INVISIBLE);
                            hintsMsgTextView.setText(R.string.wrong);
                            hintsMsgTextView.setTextColor(Color.RED);
                            hintsMsgTextView.setVisibility(View.VISIBLE);
                            String carName = getSelectedCarName(carRefs, prevRandoms.get(prevRandoms.size()-1),imageView);
                            correctNameTextView.setText(carName.substring(0, 1).toUpperCase() + carName.substring(1));
                            correctNameTextView.setVisibility(View.VISIBLE);
                            nameList.clear();
                        }

                        if (count[0] > 0 && (submitBtn.getText().equals("Submit"))){
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
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText("seconds remaining: " + millisUntilFinished / 1000);
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

    private boolean checkEnteredLetter(String name, String letter){

        String[] carName = new String[name.length()];
        boolean flag = false;

        for (int i = 0; i < name.length(); i++) {
            carName[i] = String.valueOf(name.charAt(i));
        }

        for (int i = 0; i < name.length(); i++){
            if (carName[i].equals(letter)){
                nameList.set(i, letter);
                flag = true;
            }
        }

        int correctLetters = 0;

        for (int k=0; k < carName.length; k++){
            if (carName[k].equals(nameList.get(k))) {
                correctLetters++;
            }
        }

        if (flag){
            String car = "";
            for (String i: nameList){
                car += i+" ";
            }
            carNameAsHints.setText(car);
        }

        if (correctLetters==carName.length){
            hintsMsgTextView.setText(R.string.correct);
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            hintsMsgTextView.setTextColor(Color.parseColor("#07EF0B"));
            hintsMsgTextView.setVisibility(View.VISIBLE);
            submitBtn.setText(R.string.next);
            editText.setVisibility(View.INVISIBLE);
            nameList.clear();
            count[0] = 0;
        }
        return flag;
    }

    public void setCarNameAsHints(String name){

        String[] carName = new String[name.length()];

        for (int i = 0; i < name.length(); i++) {
            carName[i] = String.valueOf(name.charAt(i));
        }

        String line="";

        for (String i: carName){
            nameList.add("_");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                line = String.join("", Collections.nCopies(carName.length, "_ "));
            }
        }

        nameList.trimToSize();
        carNameAsHints.setText(line);
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