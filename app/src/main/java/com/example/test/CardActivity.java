package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    TextView cardTitle;
    TextView cardText;
    TextView questionAnswers;
    Button continueButton;

    Button questionCardButton;
    Button eventCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        if (!Questions.hasReadFile || !Questions.language.equals(MainActivity.language)) {
            readQuestionFile();
        }
        if (!Event.hasReadFile|| !Event.language.equals(MainActivity.language)) {
            readEventFile();
        }
        eventCardButton = findViewById(R.id.EventCardButton);
        questionCardButton = findViewById(R.id.QuestionCardButton);

        View.OnClickListener drawQuestionCardClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionCard();
            }
        };

        View.OnClickListener drawEventCardClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventCard();
            }
        };
        eventCardButton.setOnClickListener(drawEventCardClick);
        questionCardButton.setOnClickListener(drawQuestionCardClick);
    }


    protected void readEventFile() {

        Event.hasReadFile = true;
        Event.language = MainActivity.language;

        if(MainActivity.language.equals(getString(R.string.it))){
            events.clear();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("eventi.txt")))) {
                for (int i = 0; i < 61; i++) {
                    String[] temp = br.readLine().split(";");
                    events.add(new Event(temp[0], temp[1]));
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }else if(MainActivity.language.equals(getString(R.string.de))) {
            events.clear();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("ereignisse.txt")))) {
                for (int i = 0; i < 61; i++) {
                    String[] temp = br.readLine().split(";");
                    events.add(new Event(temp[0], temp[1]));
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }else if(MainActivity.language.equals(getString(R.string.en))) {
            events.clear();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("events.txt")))) {
                for (int i = 0; i < 61; i++) {
                    String[] temp = br.readLine().split(";");
                    events.add(new Event(temp[0], temp[1]));
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        Collections.shuffle(events);
    }

    protected void readQuestionFile() {

        Questions.hasReadFile = true;
        Questions.language = MainActivity.language;

        if(MainActivity.language.equals(getString(R.string.it))) {
            questions.clear();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("domande.txt")))) {
                for (int i = 0; i < 50; i++) {
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
        }else if(MainActivity.language.equals(getString(R.string.de))){
            questions.clear();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("fragen.txt")))) {
                for (int i = 0; i < 50; i++) {
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
        }else if(MainActivity.language.equals(getString(R.string.en))){
            questions.clear();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("questions.txt")))) {
                for (int i = 0; i < 50; i++) {
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

        continueButton.setText(getString(R.string.continuecardbutton).toCharArray(), 0, getString(R.string.continuecardbutton).length());
        cardTitle.setText(events.get(Event.currentCard).title.toCharArray(), 0, events.get(Event.currentCard).title.length());
        cardTitle.setTextSize(20);
        cardText.setText(events.get(Event.currentCard).text.toCharArray(), 0, events.get(Event.currentCard).text.length());
        cardText.setTextSize(15);
        Event.currentCard++;
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
                    questionAnswers.append(getString(R.string.reward_or_penalty) + "\n");
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
                    cardText.setText((getString(R.string.possible_answers) + "\n1. " + questions.get(Questions.currentCard).answer[0] +
                            "\n2. " + questions.get(Questions.currentCard).answer[1]).toCharArray(), 0, getString(R.string.possible_answers).length() + 3 + questions.get(Questions.currentCard).answer[0].length() + questions.get(Questions.currentCard).answer[1].length());

                    questionAnswers.setText((getString(R.string.reward) + " \n" + questions.get(Questions.currentCard).reward[0]).toCharArray(), 0, getString(R.string.reward).length()  + 2 + questions.get(Questions.currentCard).reward[0].length());

                } else {
                    if (!questions.get(Questions.currentCard).answer[0].equals("")) {
                        cardText.setText((getString(R.string.answer) + " \n" + questions.get(Questions.currentCard).answer[0]).toCharArray(), 0, getString(R.string.answer).length() + 2 + questions.get(Questions.currentCard).answer[0].length());
                    }
                    if (questions.get(Questions.currentCard).penalty.equals("")) {
                        questionAnswers.setText((getString(R.string.reward) + questions.get(Questions.currentCard).reward[0]).toCharArray(), 0, getString(R.string.reward).length() + questions.get(Questions.currentCard).reward[0].length());
                    } else {
                        if(!questions.get(Questions.currentCard).reward[0].equals(""))
                        {
                            questionAnswers.setText((getString(R.string.reward) + " \n" + questions.get(Questions.currentCard).reward[0]).toCharArray(), 0, getString(R.string.reward).length() + 2 + questions.get(Questions.currentCard).reward[0].length());
                        }
                        questionAnswers.append("\n" + getString(R.string.penalty) + "\n" + questions.get(Questions.currentCard).penalty);
                    }
                }

                continueButton.setText(getString(R.string.continuecardbutton));
                Questions.currentCard++;
                continueButton.setOnClickListener(continueClick);
            }
        };

        if (Questions.currentCard >= questions.size()) {
            Collections.shuffle(questions);
            Questions.currentCard = 0;
        }
        continueButton.setOnClickListener(showResultClick);
        continueButton.setText(getString(R.string.show_solution));
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