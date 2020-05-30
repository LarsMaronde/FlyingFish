package com.example.flyingfish;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.flyingfish.gameObjects.Fish;
import com.example.flyingfish.gameObjects.Level;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private long startingTime;
    private long elapsedSeconds; //since start

    private MainThread thread;


    private Level currentLevel;

    public GamePanel(Context context) {
        super(context);

        Constants.CURRENT_CONTEXT = context;
        getHolder().addCallback(this);

        this.loadLevel(context, 1);

        this.thread = new MainThread(getHolder(), this);

        this.startingTime = System.currentTimeMillis();


        //place the player in the middle of the level
        this.currentLevel.setPlayerFish(new Fish(
                Constants.SCREEN_WIDTH/5,
                Constants.SCREEN_HEIGHT/2,
                this.currentLevel.getGravity(),
                this.currentLevel.getLift()));


        setFocusable(true);
    }


    private void loadLevel(Context context, int levelNumber) {
        ObjectMapper om = new ObjectMapper();

        try {
            String jsonLevel =  readFile(getResources().getAssets().open("level"+levelNumber+".json"));
            this.currentLevel = om.readValue(jsonLevel, Level.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.currentLevel.setRectangles();
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

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                this.thread.setRunning(false);
                this.thread.join();
            }catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println(event.getX()+"/"+event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.currentLevel.getPlayerFish().swimUp();
                break;
        }

        return true;
    }

    public void update() {
        this.elapsedSeconds = (System.currentTimeMillis()-this.startingTime)/1000;
        this.currentLevel.update(this.elapsedSeconds);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(62, 121, 221);
        this.currentLevel.draw(canvas);
    }
}
