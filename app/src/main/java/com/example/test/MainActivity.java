package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Job> jobs = new ArrayList<>();
    static ArrayList<Job> educatedJobs = new ArrayList<>();
    static String language = null;
    /*
        if (Job.currentCard >= jobs.size()) {
            Collections.shuffle(jobs);
            Job.currentCard = 0;
        }
        Job.currentCard++;
        // and the same
     */


    private Locale locale = null;

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (locale != null)
        {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
        if(!Job.language.equals(language)){
            readJobFile();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(language == null) {
            language = Locale.getDefault().getLanguage();
        }
        if(!language.equals(getString(R.string.it))){
            language = getString(R.string.de);
        }
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

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String lang = (String)adapterView.getItemAtPosition(i);
                if(!lang.equals("Select a language")){
                    if(lang.equals(getString(R.string.de))){
                        Configuration config = getBaseContext().getResources().getConfiguration();
                        locale = new Locale("");
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                        language = lang;
                        Intent j = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(j);
                    }else if(lang.equals(getString(R.string.it))){
                        Configuration config = getBaseContext().getResources().getConfiguration();
                        locale = new Locale(lang);
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                        language = lang;
                        Intent j = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(j);
                    }
                    readJobFile();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        Configuration config = getBaseContext().getResources().getConfiguration();

        locale = new Locale("it");
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        locale = new Locale("");
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //*/
    }

    protected void readJobFile() {

        Job.hasReadFile = true;
        Job.language = language;

        if(language.equals(getString(R.string.it))) {
            jobs.clear();
            educatedJobs.clear();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("professioni.txt")))) {
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
        }else if(language.equals(getString(R.string.de))){
            jobs.clear();
            educatedJobs.clear();
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