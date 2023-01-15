package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPlayerActivity extends AppCompatActivity {

    Player p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        Button b = findViewById(R.id.createButton);
        Intent i = getIntent();
        p = PlayerMenuActivity.players[i.getIntExtra("PLAYER", 0)];
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.ecoScore == null){
                    Toast.makeText(getApplicationContext(), "I gibs au", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText name = findViewById(R.id.nameInput);
                EditText job = findViewById(R.id.jobInput);
                EditText money = findViewById(R.id.moneyInput);
                EditText salary = findViewById(R.id.salaryInput);
                EditText ecoScore = findViewById(R.id.ecoScoreInput);
                if(name.getText().toString().equals("Name")
                || job.getText().toString().equals("Beruf")
                || money.getText().toString().equals("Kontostand")
                || salary.getText().toString().equals("Gehalt")
                || ecoScore.getText().toString().equals("EcoScore")){
                    Toast.makeText(getApplicationContext(), "Bitte füllen sie alle Felder korrekt aus!", Toast.LENGTH_LONG).show();
                    return;
                }
                int moneyVal = 0;
                int salaryVal = 0;
                int ecoScoreVal = 0;
                try{
                    moneyVal = Integer.parseInt(money.getText().toString());
                    salaryVal = Integer.parseInt(salary.getText().toString());
                    ecoScoreVal = Integer.parseInt(ecoScore.getText().toString());
                }catch(NumberFormatException | NullPointerException e){
                    Toast.makeText(getApplicationContext(), "Bitte füllen sie alle Felder korrekt aus!", Toast.LENGTH_LONG).show();
                    return;
                }
                p.setName(name.getText().toString());
                p.setJob(job.getText().toString());
                p.setMoney(moneyVal);
                p.setSalary(salaryVal);
                p.setEcoScore(ecoScoreVal);
                p.completeCreation();
                Intent i = new Intent(getApplicationContext(), PlayerMenuActivity.class);
                startActivity(i);
            }
        });
    }
}