package com.example.flyingfish.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.flyingfish.Constants;

import java.util.Iterator;
import java.util.LinkedList;

public class Level {

    private Fish playerFish;

    private int number;
    private double gravity;
    private double velocity;
    private double lift;
    private LinkedList<Obstacle> obstacles;
    private LinkedList<Coin> coins;

    private int collectedCoins;


    public Level() {/*empty*/}


    public void update (long elapsedSeconds) {
        this.playerFish.update();

        this.updateObstacles(elapsedSeconds);
        this.updateCoins(elapsedSeconds);

    }

    private void updateCoins(long elapsedSeconds) {
        Iterator<Coin> it = coins.iterator();
        while(it.hasNext()) {
            Coin co = it.next();
            if(co.getSpawnTime() <= elapsedSeconds) {
                co.setVisible(true);
                co.update();
                if(co.getX()+co.getWidth()/2 <= 0) { //if rectangle is out of the screen
                    it.remove();
                }
                if(co.collides(this.playerFish)) {
                    co.setColor(Color.rgb(255,0,0));
                    this.collectedCoins++;
                    co.setVisible(false);
                    it.remove();
                }
            }
        }
    }

    private void updateObstacles(long elapsedSeconds) {
        Iterator<Obstacle> it = obstacles.iterator();
        while(it.hasNext()) {
            Obstacle ob = it.next();
            if(ob.getSpawnTime() <= elapsedSeconds) {
                ob.setVisible(true);
                ob.update();
                if(ob.getRectangle().right <= 0) { //if rectangle is out of the screen
                    it.remove();
                }
                if(ob.collides(this.playerFish)) {
                    // TODO GAME OVER
                    ob.setColor(Color.rgb(255,0,0));
                }
            }
        }
    }

    public void setRectangles() {
        for(Obstacle ob: obstacles) {
            ob.initialize();
        }

        for(Coin co: coins) {
            co.initialize();
        }
    }

    public void draw(Canvas canvas) {
        for(Obstacle ob: obstacles) {
            ob.draw(canvas);
        }
        for(Coin co: coins){
            co.draw(canvas);
        }
        this.playerFish.draw(canvas);

        this.drawCoinsCounter(canvas);
    }

    private void drawCoinsCounter(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100);


        canvas.drawText(Integer.toString(this.collectedCoins), Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/9, paint);

    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public Fish getPlayerFish() {
        return playerFish;
    }

    public void setPlayerFish(Fish playerFish) {
        this.playerFish = playerFish;
    }

    public LinkedList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(LinkedList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public LinkedList<Coin> getCoins() {
        return coins;
    }

    public void setCoins(LinkedList<Coin> coins) {
        this.coins = coins;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getLift() {
        return lift;
    }

    public void setLift(double lift) {
        this.lift = lift;
    }


}
