package com.example.projectapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WinnerScene extends AppCompatActivity {
    TextView playerScore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_scene);
        int scores = getIntent().getExtras().getInt("Your Score is");
        playerScore = findViewById(R.id.playerScore);
        playerScore.setText("" + scores);
        Intent intent = getIntent();
        String message = intent.getStringExtra(UserInput.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.winnerName);
        textView.setText(message);
    }

    public void restart(View view) {
        Intent intent = new Intent(WinnerScene.this, MainMenuScene.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}
