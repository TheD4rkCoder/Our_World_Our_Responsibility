package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;

public class AddPlayerActivity extends AppCompatActivity {

    Player p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        TextView helpText = findViewById(R.id.universityHelpText);
        Button help = findViewById(R.id.universityHelpButton);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helpText.getVisibility() == View.INVISIBLE) {
                    helpText.setVisibility(View.VISIBLE);
                } else {
                    helpText.setVisibility(View.INVISIBLE);
                }
            }
        });
        Button b = findViewById(R.id.createButton);
        Intent i = getIntent();
        p = PlayerMenuActivity.players[i.getIntExtra("PLAYER", 0)];
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = findViewById(R.id.nameInput);
                Switch s = findViewById(R.id.universitySwitch);
                if(name.getText().toString().equals("Name")){
                    Toast.makeText(getApplicationContext(), "Bitte füllen sie alle Felder korrekt aus!", Toast.LENGTH_LONG).show();
                    return;
                }
                Job j;
                if(s.isChecked()){
                    p.university = true;
                    j = MainActivity.educatedJobs.get(Job.currentCardUni);
                    if (Job.currentCardUni >= MainActivity.educatedJobs.size()) {
                        Collections.shuffle(MainActivity.educatedJobs);
                        Job.currentCardUni = 0;
                    }
                    Job.currentCardUni++;
                    p.skipRounds = 4;
                }else{
                    if (Job.currentCard >= MainActivity.jobs.size()) {
                        Collections.shuffle(MainActivity.jobs);
                        Job.currentCard = 0;
                    }
                    j = MainActivity.jobs.get(Job.currentCard);
                    Job.currentCard++;
                }
                p.setName(name.getText().toString());
                p.setMoney(j.money);
                p.setSalary(j.salary);
                p.setEcoScore(j.ecoScore);
                p.university = j.needsUniversity;
                p.setJob(j.name);
                p.setLocation(j.location);

                PlayerMenuActivity.amountOfPlayers++;

                p.completeCreation();
                Intent i = new Intent(getApplicationContext(), PlayerMenuActivity.class);
                startActivity(i);
            }
        });
    }
}