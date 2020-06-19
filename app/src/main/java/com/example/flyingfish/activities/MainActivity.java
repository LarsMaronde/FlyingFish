package com.example.flyingfish.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;

import com.example.flyingfish.Constants;
import com.example.flyingfish.DbPlayerLevel;
import com.example.flyingfish.GamePanel;
import com.example.flyingfish.R;

public class MainActivity extends Activity {

    private int level;
    private GamePanel gm;
    private static MainActivity instance;
    protected static DbPlayerLevel dbPlayerLevel;

    public static MainActivity getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        dbPlayerLevel = new DbPlayerLevel(this);
        setContentView(R.layout.activity_main);

        //store the screen size in the Constants class
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCREEN_WIDTH = dm.widthPixels;

        Intent intent = getIntent();
        level = intent.getIntExtra(LevelMenueActivity.LEVEL_MESSAGE, 0);

        gm = new GamePanel((ViewGroup) findViewById(R.id.container),this, level);
    }

    public void restart() {
        gm.stopRunning();
        gm.finish();
        Intent intent = getIntent();
        level = intent.getIntExtra(LevelMenueActivity.LEVEL_MESSAGE, 0);
        gm = new GamePanel((ViewGroup) findViewById(R.id.container),this, level);
    }

    public DbPlayerLevel getPlayerDB() {
        return this.dbPlayerLevel;
    }
}
