package com.example.projectapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverScene  extends AppCompatActivity {
    TextView playerScore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_scene);
        /*Intent intent2 = getIntent();
        String message = intent2.getStringExtra(UserInput.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.playerName1);
        textView.setText(message);*/

        int score = getIntent().getExtras().getInt("score");
        playerScore = findViewById(R.id.playerScore);
        playerScore.setText("" + score);
    }

    public void restart(View view) {
        Intent intent = new Intent(GameOverScene.this, UserInput.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}
