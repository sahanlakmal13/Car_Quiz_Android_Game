package com.example.mad_assignment_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static com.example.mad_assignment_1.Common.carRefs;
import static com.example.mad_assignment_1.Common.getSelectedCarName;

public class IdentifyTheCarImageActivity extends AppCompatActivity {

    private ImageView carImage1;
    private ImageView carImage2;
    private ImageView carImage3;
    private TextView carMake;
    private TextView msgCarMake;
    private Button submitBtn;
    private static final ArrayList<Integer> prevRandoms = new ArrayList<>();
    private final String[] selectedCarName = new String[1];
    private final String[] setCarNames = new String[3];
    private TextView carImage1TextView;
    private TextView carImage2TextView;
    private TextView carImage3TextView;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_car_image);

        Bundle extras = getIntent().getExtras();
        boolean switchState = extras.getBoolean("switchState");

        carImage1 = findViewById(R.id.carImage1);
        carImage2 = findViewById(R.id.carImage2);
        carImage3 = findViewById(R.id.carImage3);
        carMake = findViewById(R.id.makeTextView);
        submitBtn = findViewById(R.id.submitButton);
        msgCarMake = findViewById(R.id.msgTextView);
        carImage1TextView = findViewById(R.id.carImage1TextView);
        carImage2TextView = findViewById(R.id.carImage2TextView);
        carImage3TextView = findViewById(R.id.carImage3TextView);
        timerTextView = findViewById(R.id.timerTextView);

        if (switchState){
            startCountTimer();
        }

        setRandomImages(carRefs);
        final String[] setCarName = {setCarName()};
        carMake.setText(setCarName[0]);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if (submitBtn.getText().equals("Next")){
                    msgCarMake.setText("");
                    carImage1TextView.setText("");
                    carImage2TextView.setText("");
                    carImage3TextView.setText("");
                    carImage1.setBackground(null);
                    carImage2.setBackground(null);
                    carImage3.setBackground(null);
                    selectedCarName[0] = null;
                    setRandomImages(carRefs);
                    setCarName[0] = setCarName();
                    carMake.setText(setCarName[0]);
                    submitBtn.setText(R.string.submit);
                    timerTextView.setText("");
                    if (switchState){
                        startCountTimer();
                    }
                }

                else if (submitBtn.getText().equals("Submit")){

                    if (setCarName[0].equals(selectedCarName[0])){
                        msgCarMake.setTextColor(Color.parseColor("#07EF0B"));
                        msgCarMake.setText(R.string.correct);
                    }
                    else {
                        msgCarMake.setTextColor(Color.RED);
                        msgCarMake.setText(R.string.wrong);
                        carImage1TextView.setText(setCarNames[0]);
                        carImage2TextView.setText(setCarNames[1]);
                        carImage3TextView.setText(setCarNames[2]);

                        if (selectedCarName[0] == null){
                            Drawable highlightBorder = getResources().getDrawable(R.drawable.image_border_red);
                            carImage1.setBackground(highlightBorder);
                            carImage2.setBackground(highlightBorder);
                            carImage3.setBackground(highlightBorder);
                        }
                    }
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    submitBtn.setText("Next");
                }
            }
        });
    }

    private void startCountTimer() { //https://stackoverflow.com/questions/12608614/how-to-use-a-textview-to-countdown-to-zero
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText(getString(R.string.secRemain) + millisUntilFinished / 1000);
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

    private String setCarName() {
        Random rand = new Random();
        int randomNum = rand.nextInt(setCarNames.length);
        String car="";
        switch (randomNum){
            case 0:
                car = setCarNames[0];
                break;
            case 1:
                car = setCarNames[1];
                break;
            case 2:
                car = setCarNames[2];
                break;
        }
        return (car.substring(0, 1).toUpperCase() + car.substring(1));
    }

    private void setRandomImages(int[] carRefs){

        ImageView[] imageViews = {carImage1,carImage2,carImage3};

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
            String car =  getSelectedCarName(carRefs,randomNum,imageViews[i]);
            setCarNames[i] = car.substring(0, 1).toUpperCase() + car.substring(1);
            if (i==2){
                if (setCarNames[0].equals(setCarNames[1]) || setCarNames[0].equals(setCarNames[2]) || setCarNames[1].equals(setCarNames[2])){
                    i=0;
                    continue;
                }
            }
        }
    }

    public void imageClicked(View view) {

        Drawable highlightBorder = getResources().getDrawable(R.drawable.image_border);

        carImage1.setBackground(null);
        carImage2.setBackground(null);
        carImage3.setBackground(null);

        if (view.getId() == R.id.carImage1){
                carImage1.setBackground(highlightBorder);
                selectedCarName[0] = setCarNames[0];
        }
        else if(view.getId() == R.id.carImage2){
                carImage2.setBackground(highlightBorder);
                selectedCarName[0] = setCarNames[1];
        }
        else if(view.getId() == R.id.carImage3){
                carImage3.setBackground(highlightBorder);
                selectedCarName[0] = setCarNames[2];
        }
    }
}