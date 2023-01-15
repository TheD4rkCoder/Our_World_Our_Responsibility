package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class CardActivity extends AppCompatActivity {

    static ArrayList<Questions> questions = new ArrayList<>();
    static ArrayList<Event> events = new ArrayList<>();
    static ArrayList<Job> jobs = new ArrayList<>();
    static ArrayList<Job> educatedJobs = new ArrayList<>();
    Chip jobsChip;
    Chip educatedJobsChip;
    Chip eventCardChip;
    Chip questionCardChip;
    TextView cardTitle;
    TextView cardText;
    TextView questionAnswers;
    Button continueButton;

    Button drawCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        if (!Questions.hasReadFile) {
            readQuestionFile();
        }
        if (!Event.hasReadFile) {
            readEventFile();
        }
        if (!Job.hasReadFile) {
            readJobFile();
        }

        drawCardButton = findViewById(R.id.drawCardButton);
        jobsChip = findViewById(R.id.jobsChip);
        educatedJobsChip = findViewById(R.id.educatedJobsChip);
        eventCardChip = findViewById(R.id.eventCardChip);
        questionCardChip = findViewById(R.id.questionCardChip);

        View.OnClickListener chipNotBold = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventCardChip.setTypeface(null, Typeface.NORMAL);
                questionCardChip.setTypeface(null, Typeface.NORMAL);
            }
        };
        View.OnClickListener eventChipBold = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventCardChip.getTypeface() != null && eventCardChip.getTypeface().isBold()) {
                    eventCardChip.setTypeface(null, Typeface.NORMAL);
                    return;
                }
                eventCardChip.setTypeface(null, Typeface.BOLD);
                questionCardChip.setTypeface(null, Typeface.NORMAL);
            }
        };
        View.OnClickListener questionChipBold = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionCardChip.getTypeface() != null && questionCardChip.getTypeface().isBold()) {
                    questionCardChip.setTypeface(null, Typeface.NORMAL);
                    return;
                }
                eventCardChip.setTypeface(null, Typeface.NORMAL);
                questionCardChip.setTypeface(null, Typeface.BOLD);
            }
        };
        jobsChip.setOnClickListener(chipNotBold);
        educatedJobsChip.setOnClickListener(chipNotBold);
        eventCardChip.setOnClickListener(eventChipBold);
        questionCardChip.setOnClickListener(questionChipBold);

        View.OnClickListener drawCardClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jobsChip.isChecked()) {
                    jobCard();
                } else if (educatedJobsChip.isChecked()) {
                    educatedJobCard();
                } else if (eventCardChip.isChecked()) {
                    eventCard();
                } else if (questionCardChip.isChecked()) {
                    questionCard();
                }
            }
        };
        drawCardButton.setOnClickListener(drawCardClick);
    }

    protected void readJobFile() {

        Job.hasReadFile = true;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("berufe.txt")))) {
            for (int i = 0; i < 29; i++) {
                String[] temp = br.readLine().split(";");
                if (temp[2].equals("T")) {
                    educatedJobs.add(new Job(true, temp[0], temp[1]));
                } else {
                    jobs.add(new Job(false, temp[0], temp[1]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Collections.shuffle(jobs);
        Collections.shuffle(educatedJobs);
    }

    protected void readEventFile() {

        Event.hasReadFile = true;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("ereignisse.txt")))) {
            for (int i = 0; i < 60; i++) {
                String[] temp = br.readLine().split(";");
                events.add(new Event(temp[0], temp[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Collections.shuffle(events);
    }

    protected void readQuestionFile() {

        Questions.hasReadFile = true;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("fragen.txt")))) {
            for (int i = 0; i < 47; i++) {
                String[] temp = br.readLine().split(";"), possibleAnswers, rewards;
                // read file into arraylist Questions
                if (!temp[6].equals("")) {
                    possibleAnswers = new String[]{temp[1], temp[4], temp[5], temp[6]};
                    rewards = new String[]{temp[2], temp[7], temp[8], temp[9]};
                } else if (!temp[4].equals("")) {
                    possibleAnswers = new String[]{temp[1], temp[4]};
                    rewards = new String[]{temp[2]};
                } else {
                    possibleAnswers = new String[]{temp[1]};
                    rewards = new String[]{temp[2]};
                }

                questions.add(new Questions(temp[0], possibleAnswers, rewards, temp[3]));
            }
            // question;answer1;reward1;penalty;answer2;answer3;answer4;reward2;reward3;reward4
        } catch (IOException e) {
            throw new RuntimeException();
        }

        Collections.shuffle(questions);
    }

    protected void eventCard() {
        setContentView(R.layout.activity_text);
        continueButton = findViewById(R.id.continueButton);
        cardTitle = findViewById(R.id.cardTitle);
        cardText = findViewById(R.id.cardText);
        questionAnswers = findViewById(R.id.answersText);

        View.OnClickListener continueClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CardActivity.class);
                startActivity(i);
            }
        };
        continueButton.setOnClickListener(continueClick);

        if (Event.currentCard >= events.size()) {
            Collections.shuffle(events);
            Event.currentCard = 0;
        }

        continueButton.setText("Weiter".toCharArray(), 0, 6);
        cardTitle.setText(events.get(Event.currentCard).title.toCharArray(), 0, events.get(Event.currentCard).title.length());
        cardTitle.setTextSize(20);
        cardText.setText(events.get(Event.currentCard).text.toCharArray(), 0, events.get(Event.currentCard).text.length());
        cardText.setTextSize(15);
        Event.currentCard++;
    }

    protected void jobCard() {
        setContentView(R.layout.activity_text);
        continueButton = findViewById(R.id.continueButton);
        cardTitle = findViewById(R.id.cardTitle);
        cardText = findViewById(R.id.cardText);
        questionAnswers = findViewById(R.id.answersText);

        View.OnClickListener continueClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CardActivity.class);
                startActivity(i);
            }
        };
        continueButton.setOnClickListener(continueClick);

        if (Job.currentCard >= jobs.size()) {
            Collections.shuffle(jobs);
            Job.currentCard = 0;
        }

        continueButton.setText("weiter".toCharArray(), 0, 6);
        cardTitle.setText(jobs.get(Job.currentCard).name.toCharArray(), 0, jobs.get(Job.currentCard).name.length());
        cardTitle.setTextSize(20);
        cardText.append("Startbereich:\n" + jobs.get(Job.currentCard).location);
        cardText.setTextSize(15);
        Job.currentCard++;
    }

    protected void educatedJobCard() {
        setContentView(R.layout.activity_text);
        continueButton = findViewById(R.id.continueButton);
        cardTitle = findViewById(R.id.cardTitle);
        cardText = findViewById(R.id.cardText);
        questionAnswers = findViewById(R.id.answersText);

        View.OnClickListener continueClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CardActivity.class);
                startActivity(i);
            }
        };
        continueButton.setOnClickListener(continueClick);

        if (Job.currentCardUni >= educatedJobs.size()) {
            Collections.shuffle(educatedJobs);
            Job.currentCardUni = 0;
        }

        continueButton.setText("weiter".toCharArray(), 0, 6);
        cardTitle.setText(educatedJobs.get(Job.currentCard).name.toCharArray(), 0, educatedJobs.get(Job.currentCard).name.length());
        cardTitle.setTextSize(20);
        cardText.append("Startbereich:\n" + educatedJobs.get(Job.currentCard).location);
        cardText.setTextSize(15);
        Job.currentCard++;
    }

    protected void questionCard() {
        setContentView(R.layout.activity_text);
        continueButton = findViewById(R.id.continueButton);
        cardTitle = findViewById(R.id.cardTitle);
        cardText = findViewById(R.id.cardText);
        questionAnswers = findViewById(R.id.answersText);

        View.OnClickListener continueClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CardActivity.class);
                startActivity(i);
            }
        };
        if (Questions.currentCard >= questions.size()) {
            Collections.shuffle(questions);
            Questions.currentCard = 0;
        }

        View.OnClickListener showResultClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questions.get(Questions.currentCard).answer.length > 2) {
                    questionAnswers.append("Belohnungen oder Strafen:\n");
                    if (!questions.get(Questions.currentCard).penalty.equals("")) {
                        questionAnswers.append("A: " + questions.get(Questions.currentCard).penalty + "\n");
                    }
                    if (!questions.get(Questions.currentCard).reward[0].equals("")) {
                        questionAnswers.append("A: " + questions.get(Questions.currentCard).reward[0] + "\n");
                    }
                    if (!questions.get(Questions.currentCard).reward[1].equals("")) {
                        questionAnswers.append("B: " + questions.get(Questions.currentCard).reward[1] + "\n");
                    }
                    if (!questions.get(Questions.currentCard).reward[2].equals("")) {
                        questionAnswers.append("C: " + questions.get(Questions.currentCard).reward[2] + "\n");
                    }
                    if (!questions.get(Questions.currentCard).reward[3].equals("")) {
                        questionAnswers.append("D: " + questions.get(Questions.currentCard).reward[3]);
                    }
                } else if (questions.get(Questions.currentCard).answer.length == 2) {
                    cardText.setText(("Mögliche Antworten: \n1. " + questions.get(Questions.currentCard).answer[0] +
                            "\n2. " + questions.get(Questions.currentCard).answer[1]).toCharArray(), 0, 26 + questions.get(Questions.currentCard).answer[0].length() + questions.get(Questions.currentCard).answer[1].length());
                    questionAnswers.setText(("Belohnung: \n" + questions.get(Questions.currentCard).reward[0]).toCharArray(), 0, 12 + questions.get(Questions.currentCard).reward[0].length());

                } else {
                    if (!questions.get(Questions.currentCard).answer[0].equals("")) {
                        cardText.setText(("Antwort: \n" + questions.get(Questions.currentCard).answer[0]).toCharArray(), 0, 10 + questions.get(Questions.currentCard).answer[0].length());
                    }
                    if (questions.get(Questions.currentCard).penalty.equals("")) {
                        questionAnswers.setText(("Belohnung: \n" + questions.get(Questions.currentCard).reward[0]).toCharArray(), 0, 12 + questions.get(Questions.currentCard).reward[0].length());
                    } else {
                        questionAnswers.setText(("Belohnung: \n" + questions.get(Questions.currentCard).reward[0]).toCharArray(), 0, 12 + questions.get(Questions.currentCard).reward[0].length());
                        questionAnswers.append("\nBestrafung: \n" + questions.get(Questions.currentCard).penalty);
                    }
                }
                continueButton.setText("weiter".toCharArray(), 0, 6);
                Questions.currentCard++;
                continueButton.setOnClickListener(continueClick);
            }
        };

        if (Questions.currentCard >= questions.size()) {
            Collections.shuffle(questions);
            Questions.currentCard = 0;
        }
        continueButton.setOnClickListener(showResultClick);
        continueButton.setText("Lösung zeigen".toCharArray(), 0, 13);
        cardTitle.setText(questions.get(Questions.currentCard).question.toCharArray(), 0, questions.get(Questions.currentCard).question.length());
        cardTitle.setTextSize(20);
        questionAnswers.setTextSize(15);
        cardText.setTextSize(15);

        if (questions.get(Questions.currentCard).answer.length > 2) {
            cardText.setText(("A: " + questions.get(Questions.currentCard).answer[0] +
                    "\nB: " + questions.get(Questions.currentCard).answer[1] +
                    "\nC: " + questions.get(Questions.currentCard).answer[2] +
                    "\nD: " + questions.get(Questions.currentCard).answer[3]).toCharArray(), 0, 15 + questions.get(Questions.currentCard).answer[0].length() + questions.get(Questions.currentCard).answer[1].length() + questions.get(Questions.currentCard).answer[2].length() + questions.get(Questions.currentCard).answer[3].length());
        }

        //questionAnswers.setText("A: B\nB: C\nC: Ä".toCharArray(), 0, 14);


        // what to do when you ran out of cards

    }
}