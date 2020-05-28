package com.example.flyingfish.gameObjects;

import android.graphics.Canvas;

import androidx.annotation.NonNull;

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


    public Level() {/*empty*/}


    public void update (long elapsedSeconds) {
        this.playerFish.update();

        Iterator<Obstacle> it = obstacles.iterator();
        while(it.hasNext()) {
            Obstacle ob = it.next();
            if(ob.getSpawnTime() <= elapsedSeconds) {
                ob.setVisible(true);
                ob.update();
                if(ob.getRectangle().right <= 0) { //if rectangle is out of the screen
                    System.out.println("removed AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    it.remove();
                }
            }
        }
    }

    public void setRectangles() {
        for(Obstacle ob: obstacles) {
            ob.createRectangle();
        }
    }

    public void draw(Canvas canvas) {
        this.playerFish.draw(canvas);
        for(Obstacle ob: obstacles) {
            ob.draw(canvas);
        }
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
