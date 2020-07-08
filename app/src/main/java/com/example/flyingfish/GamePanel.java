package com.example.flyingfish;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.flyingfish.activities.MainActivity;
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

    private static GamePanel instance;
    private Level currentLevel;
    private ViewGroup container;
    private Context context;
    private ScheduledExecutorService executor;
    private long startingTime;

    private boolean running;
    private boolean firstStart;

    private int level;

    public static GamePanel getInstance() {
        return instance;
    }

    public GamePanel(final ViewGroup container, MainActivity mainActivity, int level) {
        context = container.getContext();
        this.container = container;
        this.container.removeAllViews();
        instance = this;
        this.level = level;
        this.firstStart = true;

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!firstStart && !running) {
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    currentLevel.getPlayerFish().swimUp();
                    currentLevel.getPlayerFish().setLook2();
                    startLevel();
                    if(firstStart) {
                        firstStart = false;
                        startingTime = System.currentTimeMillis();
                    }
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
                Constants.SCREEN_WIDTH / 4 ,
                Constants.SCREEN_HEIGHT / 3,
                this.currentLevel.getGravity(),
                this.currentLevel.getLift(),
                this.currentLevel.getVelocityCap(),
                container));
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(startMainLoop, 0, 5, TimeUnit.MILLISECONDS);
        this.startingTime = System.currentTimeMillis();
    }

    private Runnable startMainLoop = new Runnable() {
        @Override
        public void run() {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
            if (running) {
                update();
            }
            if(firstStart){
                currentLevel.getPlayerFish().startUpdate();
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
        if(running){
            long elapsedTime = System.currentTimeMillis() - this.startingTime;
            this.currentLevel.update((float) elapsedTime/1000);
        }
    }

    public void startLevel() {
        this.running = true;
    }

    public void draw() {
        this.currentLevel.draw();
    }

    public void gameOver() {
        this.running = false;
        currentLevel.getGameOverPanel().setCollectedCoins(this.currentLevel.getCollectedCoins());
        currentLevel.gameOver();
        currentLevel.getScore().setVisible(false);
    }
    public void stopRunning() {
        executor.shutdown();
    }

    public int getLevel(){
        return level;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
