package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.cardButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });
        findViewById(R.id.playerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });

    }

    public void onButtonClick (View v){
        if(v.getId() == R.id.playerButton){
            Intent i = new Intent(this, PlayerMenuActivity.class);
            startActivity(i);
        }else if(v.getId() == R.id.cardButton){
            Intent i = new Intent(this, CardActivity.class);
            startActivity(i);
        }
    }
}