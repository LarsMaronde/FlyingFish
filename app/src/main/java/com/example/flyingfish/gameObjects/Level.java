package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;
import com.example.flyingfish.gameObjects.background.BackgroundManger;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Iterator;
import java.util.LinkedList;

import androidx.appcompat.app.AppCompatActivity;

public class Level {

    private BackgroundManger backgroundManger;
    private Fish playerFish;
    private TextGameObject score;
    private int number;
    private double gravity;
    private double velocity;
    private double velocityCap;
    private double globalSpeed;
    private double lift;
    private LinkedList<Obstacle> obstacles;
    private LinkedList<Coin> coins;
    public int collectedCoins;
//    private TextView countertext;

    @JsonIgnore
    private ViewGroup container;
    @JsonIgnore
    private Context context;

    public Level() {/*empty*/}


    public void update (double elapsedSeconds) {
        this.backgroundManger.updateBackgrounds();
        this.playerFish.update();

        this.updateObstacles(elapsedSeconds);
        this.updateCoins(elapsedSeconds);
    }

    private void updateCoins(double elapsedSeconds) {
        Iterator<Coin> it = coins.iterator();
        while(it.hasNext()) {
            Coin co = it.next();
            if(co.getSpawnTime() <= elapsedSeconds) {
                co.setVisible(true);
                co.update();
                if(co.getX()+co.getWidth()/2 <= 0) { //if rectangle is out of the screen
                    co.setVisible(false);
                    it.remove();
                    co = null;
                    continue;
                }
                if(co.collides(this.playerFish)) {
                    this.collectedCoins++;
                    it.remove();
                }
            }
        }
    }

    private void updateObstacles(double elapsedSeconds) {
        Iterator<Obstacle> it = obstacles.iterator();
        while(it.hasNext()) {
            Obstacle ob = it.next();
            if(ob.getSpawnTime() <= elapsedSeconds) {
                ob.setVisible(true);
                ob.update();
                if(ob.getRectangle().right <= 0) { //if rectangle is out of the screen
                    ob.setVisible(false);
                    it.remove();
                    ob = null;
                    continue;
                }
                if(ob.collides(this.playerFish)) {
                    // TODO GAME OVER
//                    ob.setColor(Color.rgb(255,0,0));
                }
            }
        }
    }

    public void initializeObjects() {
       this.backgroundManger = new BackgroundManger(this.container, this.globalSpeed);
        for(Obstacle ob: obstacles) {
            ob.initialize(container);
        }

        for(Coin co: coins) {
            co.initialize(this.container);
        }

        this.score = new TextGameObject(this.container);
        setCoinsCounter();

    }

    public void draw() {
        this.backgroundManger.drawBackgrounds();
        for(Obstacle ob: obstacles) {
            ob.draw();
        }
        for(Coin co: coins){
            co.draw();
        }
        this.playerFish.draw();
        score.draw();
        score.setText(""+ collectedCoins);
    }

    private void setCoinsCounter() {
        this.score.setPosition(Constants.SCREEN_WIDTH/2,120);
        this.score.setTextColor(Color.rgb(255, 197, 57));
        this.score.setTextSize(35);
        score.setCentered(true);
   }

    public BackgroundManger getBackgroundManger() {
        return backgroundManger;
    }

    public void setBackgroundManger(BackgroundManger backgroundManger) {
        this.backgroundManger = backgroundManger;
    }

    public Fish getPlayerFish() {
        return playerFish;
    }

    public void setPlayerFish(Fish playerFish) {
        this.playerFish = playerFish;
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

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public void setCollectedCoins(int collectedCoins) {
        this.collectedCoins = collectedCoins;
    }

    public ViewGroup getContainer() {
        return container;
    }

    public void setContainer(ViewGroup container) {
        this.container = container;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public double getVelocityCap() {
        return velocityCap;
    }

    public void setVelocityCap(double velocityCap) {
        this.velocityCap = velocityCap;
    }

    public double getGlobalSpeed() {
        return globalSpeed;
    }

    public void setGlobalSpeed(double globalSpeed) {
        this.globalSpeed = globalSpeed;
    }
}
