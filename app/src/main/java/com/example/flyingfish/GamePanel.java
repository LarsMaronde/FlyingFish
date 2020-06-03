package com.example.flyingfish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.flyingfish.gameObjects.Fish;
import com.example.flyingfish.gameObjects.Level;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GamePanel extends Activity {

    private ViewGroup container;
    private long startingTime;
    private double elapsedSeconds; //since start
    private Level currentLevel;
    private boolean gameOver = false;
    private static GamePanel instance;
    private Context context;

    public static GamePanel getInstance () {
        return instance;
    }

    public GamePanel(final ViewGroup container, MainActivity mainActivity, int level) {
        context = container.getContext();
        instance = this;
        this.container = container;
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    currentLevel.getPlayerFish().swimUp();
                    currentLevel.getPlayerFish().setLook2();
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    currentLevel.getPlayerFish().setLook1();
                    return true;
                }
                return false;
            }
        });

        this.loadLevel(container.getContext(), level);
        //place the player in the middle of the level
        this.currentLevel.setPlayerFish(new Fish(
                Constants.SCREEN_WIDTH / 5,
                Constants.SCREEN_HEIGHT / 2,
                this.currentLevel.getGravity(),
                this.currentLevel.getLift(),
                this.currentLevel.getVelocityCap(),
                container));


        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(startMainLoop, 0, 5, TimeUnit.MILLISECONDS);
        this.startingTime = System.currentTimeMillis();
    }

    private Runnable startMainLoop = new Runnable() {
        @Override
        public void run() {
            // final Handler handler = new Handler();
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        if(!gameOver){
                            update();
                        }
                        draw();
                }
            });
        }
    };

    private void loadLevel(Context context, int levelNumber) {
        ObjectMapper om = new ObjectMapper();
        try {
            String jsonLevel = readFile(context.getResources().getAssets().open("level" + levelNumber + ".json"));
            this.currentLevel = om.readValue(jsonLevel, Level.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.currentLevel.setContext(context);
        this.currentLevel.setContainer(this.container);
        this.currentLevel.initializeObjects();
    }

    private String readFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    public void update() {
        this.elapsedSeconds = (System.currentTimeMillis() - this.startingTime) / 1000;
        this.currentLevel.update(this.elapsedSeconds);
    }

    public void draw() {
        this.currentLevel.draw();
    }

    public void gameOver(){
        this.gameOver = true;
        currentLevel.gameOver();
    }

}
