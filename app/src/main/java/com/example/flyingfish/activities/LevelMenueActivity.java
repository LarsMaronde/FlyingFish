package com.example.flyingfish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.flyingfish.R;
import com.example.flyingfish.db.DbPlayerLevel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class LevelMenueActivity extends AppCompatActivity {
    public static final String LEVEL_MESSAGE = "com.exampleflyingfish.MESSAGE";
    private int level;
    protected static DbPlayerLevel dbPlayerLevel;
    private int maxUnlockedLevelNumber;
    private ArrayList buttons = new ArrayList<Button>();
    private Button btl1;
    private Button btl2;
    private Button btl3;
    private Button btl4;
    private Button btl5;
    private Button btl6;

    private static LevelMenueActivity instance;

    public static LevelMenueActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_levelmenu);
        dbPlayerLevel = new DbPlayerLevel(this);
        maxUnlockedLevelNumber = dbPlayerLevel.getMaxLevelNumber();

        btl1 = (Button) findViewById(R.id.level_1);
        btl2 = (Button) findViewById(R.id.level_2);
        btl3 = (Button) findViewById(R.id.level_3);
        btl4 = (Button) findViewById(R.id.level_4);
        btl5 = (Button) findViewById(R.id.level_5);
        btl6 = (Button) findViewById(R.id.level_6);
        buttons.add(btl1);
        buttons.add(btl2);
        buttons.add(btl3);
        buttons.add(btl4);
        buttons.add(btl5);
        buttons.add(btl6);

        setEnabledlevels();

    }

    public void startGame(View v) {
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

    public DbPlayerLevel getPlayerDB() {
        return dbPlayerLevel;
    }

    public void setEnabledlevels() {
        for (int i = 0; i < buttons.size(); i++) {
            if (i <= maxUnlockedLevelNumber) {
                Button b = (Button) buttons.get(i);
                b.setEnabled(true);
            }
        }
    }
}
