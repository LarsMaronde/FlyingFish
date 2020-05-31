package com.example.flyingfish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void levelMenue(View v){
        Intent intent = new Intent(StartActivity.this, LevelMenueActivity.class);
        startActivity(intent);
    }

    public void leaderboard(View v){
        Intent intent = new Intent(StartActivity.this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void shop(View v){
        Intent intent = new Intent(StartActivity.this, ShopActivity.class);
        startActivity(intent);
    }

    public void quit(View v){
        finish();
    }


}
