package com.example.flyingfish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

public class MainActivity extends Activity {

    private int level;
    private GamePanel gm;
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
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


}
