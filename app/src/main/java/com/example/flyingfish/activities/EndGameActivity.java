package com.example.flyingfish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.R;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

    }

    public void mainActivity(View v){
        Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void startGame(){
        Intent intent = new Intent(EndGameActivity.this, LevelMenuActivity.class);
        startActivity(intent);
    }
}
