package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Job> jobs = new ArrayList<>();
    static ArrayList<Job> educatedJobs = new ArrayList<>();
    /*
        if (Job.currentCard >= jobs.size()) {
            Collections.shuffle(jobs);
            Job.currentCard = 0;
        }
        Job.currentCard++;
        // and the same
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!Job.hasReadFile) {
            readJobFile();
        }
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

    protected void readJobFile() {

        Job.hasReadFile = true;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("berufe.txt")))) {
            for (int i = 0; i < 29; i++) {
                String[] temp = br.readLine().split(";");
                if (temp[2].equals("T")) {
                    educatedJobs.add(new Job(true, temp[0], temp[1], Integer.parseInt(temp[3]), Integer.parseInt(temp[4]), Integer.parseInt(temp[5])));
                } else {
                    jobs.add(new Job(false, temp[0], temp[1], Integer.parseInt(temp[3]), Integer.parseInt(temp[4]), Integer.parseInt(temp[5])));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Collections.shuffle(jobs);
        Collections.shuffle(educatedJobs);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.playerButton) {
            Intent i = new Intent(this, PlayerMenuActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.cardButton) {
            Intent i = new Intent(this, CardActivity.class);
            startActivity(i);
        }
    }
}