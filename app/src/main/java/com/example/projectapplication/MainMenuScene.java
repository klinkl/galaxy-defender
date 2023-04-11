package com.example.projectapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuScene extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_scene);
        Intent intent = getIntent();
        String message = intent.getStringExtra(UserInput.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.wellcome);
        textView.setText(message);
        System.out.println("we welcomed "+message);
    }

    public void startGame(View view) {
        startActivity(new Intent(this, MainActivity.class));
        Intent intent = getIntent();
        String message = intent.getStringExtra(UserInput.EXTRA_MESSAGE);
        System.out.println(message+" entered the game");
        finish();
    }
    public void exitGame(View view) {
        finish();
    }
}
