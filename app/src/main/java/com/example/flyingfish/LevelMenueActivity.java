package com.example.flyingfish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LevelMenueActivity extends AppCompatActivity {
    public static final String LEVEL_MESSAGE = "com.exampleflyingfish.MESSAGE" ;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelmenue);
    }

    public void startGame(View v){
        level = getLevel(v);
        Intent intent = new Intent(LevelMenueActivity.this, MainActivity.class);
        intent.putExtra(LEVEL_MESSAGE, level);
        startActivity(intent);
    }

    public int getLevel(View v) {
        switch (v.getId()) {
            case R.id.level_1:
                return 1;
            case R.id.level_2:
                return 2;
            case R.id.level_3:
                return 3;
            case R.id.level_4:
                return 4;
            case R.id.level_5:
                return 5;
            case R.id.level_6:
                return 6;
        }
        return 0;
    }
}
