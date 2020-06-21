package com.example.flyingfish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.R;

public class MainMenuActivity extends AppCompatActivity {

    private static MainMenuActivity instance;
    public static MainMenuActivity getInstance () {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_mainmenu);
    }

    public void levelMenue(View v){
        startActivity(new Intent(this, LevelMenuActivity.class));
    }

    public void leaderboard(View v){
        startActivity(new Intent(this, LeaderboardActivity.class));
    }

    public void startAcitivity(){
        setContentView(R.layout.activity_mainmenu);
    }

    public void shop(View v){
        startActivity(new Intent(this, ShopActivity.class));
    }

    public void quit(View v){
        finish();
        System.exit(0);
    }

    public void backToLogin(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

}
