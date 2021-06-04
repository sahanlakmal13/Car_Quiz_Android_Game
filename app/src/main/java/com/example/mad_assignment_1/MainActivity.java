package com.example.mad_assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Button identifyCarMakeBtn;
    private Button hintsBtn;
    private Button identifyCarImageBtn;
    private Button advanceLevelBtn;
    private Switch switchTimeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        identifyCarMakeBtn = findViewById(R.id.idCarMake);
        hintsBtn = findViewById(R.id.hints);
        identifyCarImageBtn = findViewById(R.id.idCarImage);
        advanceLevelBtn = findViewById(R.id.advanceLevel);
        switchTimeMode = findViewById(R.id.switchTimeMode);

        Boolean[] switchState = {false};

        switchTimeMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
                switchState[0] = state;
            }
        });

        Bundle switchBundle = new Bundle();

        identifyCarMakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IdentifyTheCarMakeActivity.class);
                switchBundle.putBoolean("switchState", switchState[0]);
                intent.putExtras(switchBundle);
                startActivity(intent);
            }
        });

        hintsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HintsActivity.class);
                switchBundle.putBoolean("switchState", switchState[0]);
                intent.putExtras(switchBundle);
                startActivity(intent);
            }
        });

        identifyCarImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IdentifyTheCarImageActivity.class);
                switchBundle.putBoolean("switchState", switchState[0]);
                intent.putExtras(switchBundle);
                startActivity(intent);
            }
        });

        advanceLevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdvanceLevelActivity.class);
                switchBundle.putBoolean("switchState", switchState[0]);
                intent.putExtras(switchBundle);
                startActivity(intent);
            }
        });

    }
}