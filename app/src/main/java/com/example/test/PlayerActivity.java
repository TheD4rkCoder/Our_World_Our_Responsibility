package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.Collections;

public class PlayerActivity extends AppCompatActivity {

    Player p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent i = getIntent();
        p = PlayerMenuActivity.players[i.getIntExtra("PLAYER", 0)];
        reload();
        ((Button)findViewById(R.id.confirmChangeMoney)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e = findViewById(R.id.changeMoneyInput);
                int i = (int) new Expression(String.valueOf(p.getMoney()) + e.getText().toString()).calculate();
                p.setMoney(i);
                reload();
                e.setText("");
            }
        });
        ((Button)findViewById(R.id.confirmChangeSalary)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e = findViewById(R.id.changeSalaryInput);
                int i = (int) new Expression(String.valueOf(p.getSalary()) + e.getText().toString()).calculate();
                p.setSalary(i);
                reload();
                e.setText("");
            }
        });
        ((Button)findViewById(R.id.confirmChangeEcoScore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e = findViewById(R.id.changeEcoScoreInput);
                int i = (int) new Expression(String.valueOf(p.getEcoScore()) + e.getText().toString()).calculate();
                p.setEcoScore(i);
                reload();
                e.setText("");
            }
        });
        ((Button)findViewById(R.id.skipRoundButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.skipRounds = 4;
                reload();
            }
        });
        ((Button)findViewById(R.id.changeJobButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.university = true;
                Job j = MainActivity.educatedJobs.get(Job.currentCardUni);
                if (Job.currentCardUni >= MainActivity.educatedJobs.size()) {
                    Collections.shuffle(MainActivity.educatedJobs);
                    Job.currentCardUni = 0;
                }
                Job.currentCardUni++;
                p.setSalary(j.salary);
                p.setLocation(j.location);
                p.setJob(j.name);
                reload();
            }
        });
    }
    private void reload (){
        TextView t = findViewById(R.id.nameView);
        t.setText(p.getName());
        t.setTextSize(40);
        t = findViewById(R.id.jobView);
        t.setText(p.getJob());
        t = findViewById(R.id.jobLocationText);
        t.setText("Ort des Berufes: " + p.getLocation());
        t = findViewById(R.id.moneyView);
        String s = p.getMoney() + "€";
        t.setText(s);
        t = findViewById(R.id.salaryView);
        s = p.getSalary() + "€/Monat";
        t.setText(s);
        t = findViewById(R.id.ecoScoreView);
        s = String.valueOf(p.getEcoScore());
        t.setText(s);
        t = findViewById(R.id.skipRoundCounterText);
        if (p.skipRounds > 0) {
            t.setText("Du erhälst in den nächsten " + p.skipRounds + " Runden kein Gehalt");
        } else {
            t.setVisibility(View.INVISIBLE);
        }
    }
}