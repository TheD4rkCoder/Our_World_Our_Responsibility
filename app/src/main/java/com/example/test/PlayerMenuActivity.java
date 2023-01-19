package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

public class PlayerMenuActivity extends AppCompatActivity {

    protected Chip[] playerButtons = new Chip[10];
    protected static Player[] players = new Player[10];
    private static int currentRound = 1;
    private static int currentPlayer = 0;
    public static int amountOfPlayers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_menu);
        if (players[currentPlayer] != null) {
            ((TextView) findViewById(R.id.currentPlayerText)).append(" " + players[currentPlayer].getName());
        }
        playerButtons[0] = findViewById(R.id.chipPlayer1);
        playerButtons[1] = findViewById(R.id.chipPlayer2);
        playerButtons[2] = findViewById(R.id.chipPlayer3);
        playerButtons[3] = findViewById(R.id.chipPlayer4);
        playerButtons[4] = findViewById(R.id.chipPlayer5);
        playerButtons[5] = findViewById(R.id.chipPlayer6);
        playerButtons[6] = findViewById(R.id.chipPlayer7);
        playerButtons[7] = findViewById(R.id.chipPlayer8);
        playerButtons[8] = findViewById(R.id.chipPlayer9);
        playerButtons[9] = findViewById(R.id.chipPlayer10);
        for (int i = 0; i < 10; ++i) {
            if (players[i] == null) {
                playerButtons[i].setVisibility(View.INVISIBLE);
            } else {
                if (players[i].isCreationCompleted()) {
                    playerButtons[i].setText(players[i].getName() + " (" + players[i].getMoney() / players[i].getEcoScore() + ")");
                    playerButtons[i].setVisibility(View.VISIBLE);
                } else {
                    players[i] = null;
                    --i;
                }
            }
        }
        for (int i = 0; i < 10; ++i) {
            int finalI = i;
            playerButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent j = new Intent(getApplicationContext(), PlayerActivity.class);
                    j.putExtra("PLAYER", finalI);
                    startActivity(j);
                }
            });
        }
        Button b = findViewById(R.id.addPlayerButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });
        ((Button) findViewById(R.id.nextPlayerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountOfPlayers == 0) {
                    return;
                }
                currentPlayer++;
                if (currentPlayer + 1 > amountOfPlayers) {
                    currentPlayer = 0;
                    currentRound++;
                    TextView t = findViewById(R.id.currentRoundText);
                    t.setText(getString(R.string.currentround) + " " + String.valueOf(currentRound) + "/" + 25);
                }

                TextView t = findViewById(R.id.currentPlayerText);
                if(players[currentPlayer] != null){
                    t.setText(getString(R.string.currentTurn) + " " + players[currentPlayer].getName());
                }else{
                    t.setText(getString(R.string.currentTurn));
                }

                if (players[currentPlayer].skipRounds > 0) {
                    players[currentPlayer].skipRounds--;
                } else {
                    players[currentPlayer].setMoney(players[currentPlayer].getMoney() + players[currentPlayer].getSalary());
                    playerButtons[currentPlayer].setText(players[currentPlayer].getName() + " (" + players[currentPlayer].getMoney() / players[currentPlayer].getEcoScore() + ")");
                }
            }
        });
        int ecoScoreTogether = 0, numberOfPlayers = 0;
        for (int i = 0; i < 10; i++) {
            if (players[i] != null) {
                numberOfPlayers++;
                ecoScoreTogether += players[i].getEcoScore();
            }
        }
        TextView t = findViewById(R.id.ecoScoreTogetherText);
        t.append(" " + String.valueOf(ecoScoreTogether) + "/" + String.valueOf(numberOfPlayers * 100));
        t = findViewById(R.id.currentRoundText);
        t.setText(getString(R.string.currentround) + " " + String.valueOf(currentRound) + "/" + 25);
    }

    private void onButtonClick(View v) {
        int j;
        for (j = 0; j <= 10; ++j) {
            if (j == 10) {
                v.setEnabled(false);
                v.setVisibility(View.INVISIBLE);
                return;
            }
            if (players[j] == null) {
                players[j] = new Player();
                break;
            }
        }
        Intent i = new Intent(getApplicationContext(), AddPlayerActivity.class);
        i.putExtra("PLAYER", j);
        startActivity(i);
    }
}