package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        ((Button) findViewById(R.id.confirmChangeMoney)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e = findViewById(R.id.changeMoneyInput);
                int i = (int) new Expression(String.valueOf(p.getMoney()) + e.getText().toString()).calculate();
                p.setMoney(i);
                reload();
                e.setText("");
            }
        });
        ((Button) findViewById(R.id.confirmChangeSalary)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e = findViewById(R.id.changeSalaryInput);
                int i = (int) new Expression(String.valueOf(p.getSalary()) + e.getText().toString()).calculate();
                p.setSalary(i);
                reload();
                e.setText("");
            }
        });
        ((Button) findViewById(R.id.confirmChangeEcoScore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e = findViewById(R.id.changeEcoScoreInput);
                int i = (int) new Expression(String.valueOf(p.getEcoScore()) + e.getText().toString()).calculate();
                if (i < 1) {
                    i = 1;
                }
                p.setEcoScore(i);
                reload();
                e.setText("");
            }
        });
        ((Button) findViewById(R.id.skipRoundButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.skipRounds++;
                reload();
            }
        });
        ((Button) findViewById(R.id.changeJobButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.university = true;
                if (Job.currentCardUni >= MainActivity.educatedJobs.size()) {
                    Collections.shuffle(MainActivity.educatedJobs);
                    Job.currentCardUni = 0;
                }
                Job j = MainActivity.educatedJobs.get(Job.currentCardUni);
                p.skipRounds += 4;
                Job.currentCardUni++;
                p.setSalary(j.salary);
                p.setLocation(j.location);
                p.setJob(j.name);
                reload();
            }
        });

        ((Button) findViewById(R.id.changeHelpButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getText(R.string.change_help_toast), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void reload() {
        TextView t = findViewById(R.id.nameView);
        t.setText(p.getName());
        t.setTextSize(40);
        t = findViewById(R.id.jobView);
        t.setText(p.getJob());
        t = findViewById(R.id.jobLocationText);
        t.setText(getString(R.string.locationOfJob) +" " + p.getLocation());
        t = findViewById(R.id.moneyView);
        String s = p.getMoney() + "€";
        t.setText(s);
        t = findViewById(R.id.salaryView);
        s = p.getSalary() + "€/"+ getString(R.string.month);
        t.setText(s);
        t = findViewById(R.id.ecoScoreView);
        s = String.valueOf(p.getEcoScore());
        t.setText(s);
        t = findViewById(R.id.skipRoundCounterText);
        if (p.skipRounds > 0) {
            t.setText(getString(R.string.you_get_in_the_next) + p.skipRounds + getString(R.string.rounds_no_salary));
            // you_get_in_the_next
            // rounds_no_salary
            t.setVisibility(View.VISIBLE);
        } else {
            t.setVisibility(View.INVISIBLE);
        }
    }
}