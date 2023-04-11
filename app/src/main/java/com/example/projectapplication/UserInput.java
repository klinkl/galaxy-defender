package com.example.projectapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserInput extends AppCompatActivity {
    public static final String EXTRA_MESSAGE ="com.example.projectapplication.MESSAGE";
    TextView textView;
    EditText inputText;
    public static final int TEXT_REQUEST = 1;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("The Player entered the game");
        setContentView(R.layout.user_input);
        //setContentView(R.layout.main_menu_scene);
        inputText =(EditText) findViewById(R.id.inputText);
        textView = (TextView) findViewById(R.id.welcome);
    }
    public void submition(View view) {
        Intent intent =new Intent(this,MainMenuScene.class);
        String message = inputText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
        System.out.println("It's "+message);
        finish();
    }
}
