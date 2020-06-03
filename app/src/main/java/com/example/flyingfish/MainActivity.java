package com.example.flyingfish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;

public class MainActivity extends Activity {
    private int level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //store the screen size in the Constants class
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCREEN_WIDTH = dm.widthPixels;

        Intent intent = getIntent();
        level = intent.getIntExtra(LevelMenueActivity.LEVEL_MESSAGE, 0);

        GamePanel gp = new GamePanel((ViewGroup) findViewById(R.id.container), this, level);
    }

}
