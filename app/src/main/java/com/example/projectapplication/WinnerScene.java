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
        int score = getIntent().getExtras().getInt("score");
        playerScore = findViewById(R.id.WinScore);
        playerScore.setText("" + score);
    }

    public void restart(View view) {
        Intent intent = new Intent(WinnerScene.this, UserInput.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}
