package com.example.flyingfish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private static StartActivity instance;
    public static StartActivity getInstance () {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
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

    public void startAcitivity(){
        setContentView(R.layout.activity_start);
    }

    public void shop(View v){
        Intent intent = new Intent(StartActivity.this, ShopActivity.class);
        startActivity(intent);
    }

    public void quit(View v){
        finish();
    }


}
