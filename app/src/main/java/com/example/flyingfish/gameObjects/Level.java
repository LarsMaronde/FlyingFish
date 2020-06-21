package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.example.flyingfish.Constants;
import com.example.flyingfish.GameOverPanel;
import com.example.flyingfish.GamePanel;
import com.example.flyingfish.db.DatabaseManager;
import com.example.flyingfish.gameObjects.background.BackgroundManger;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Iterator;
import java.util.LinkedList;

public class Level {

    @JsonIgnore
    private BackgroundManger backgroundManger;
    @JsonIgnore
    private Fish playerFish;
    @JsonIgnore
    private TextGameObject score;

    private int number;
    private double gravity;
    private double velocity;
    private double velocityCap;
    private double globalSpeed;
    private double lift;
    private LinkedList<Obstacle> obstacles;
    private LinkedList<Coin> coins;
    private int collectedCoins;

    @JsonIgnore
    private ViewGroup container;
    @JsonIgnore
    private Context context;

    @JsonIgnore
    private GameOverPanel gameOverPanel;

    public Level() {/*emty*/}


    public void update(float elapsedTime) {
        this.backgroundManger.updateBackgrounds();
        this.playerFish.update();
        this.gameOverPanel.update();
        this.updateObstacles(elapsedTime);
        this.updateCoins(elapsedTime);

        if(checkIfLevelWon()){
            GamePanel.getInstance().setRunning(false);
            return;
        }
    }

    private boolean checkIfLevelWon() {
        if(this.obstacles.size() == 0) {
            DatabaseManager.getInstance().addCoins(Constants.CURRENT_USERNAME, collectedCoins);
            DatabaseManager.getInstance().updatePlayerLevel(this.number, Constants.CURRENT_USERNAME, collectedCoins);
            DatabaseManager.getInstance().unlockNextLevel(Constants.CURRENT_USERNAME, this.number);
            gameOver();
            return true;
        }
        return false;

    }

    private void updateCoins(float elapsedTime) {
        Iterator<Coin> it = coins.iterator();
        while (it.hasNext()) {
            Coin co = it.next();
            if (co.getSpawnTime() <= elapsedTime) {
                co.setVisible(true);
                co.update();
                if (co.getX() + co.getWidth() / 2 <= 0) { //if rectangle is out of the screen
                    co.setVisible(false);
                    it.remove();
                    continue;
                }
                if (co.collides(this.playerFish)) {
                    this.collectedCoins++;
                    it.remove();
                }
            }
        }
    }

    private void updateObstacles(float elapsedTime) {
        Iterator<Obstacle> it = obstacles.iterator();
        while (it.hasNext()) {
            Obstacle ob = it.next();
            if (ob.getSpawnTime() <= elapsedTime) {
                ob.setVisible(true);
                ob.update();
                if (ob.getRectangle().right <= 0) { //if rectangle is out of the screen
                    ob.setVisible(false);
                    it.remove();
                    continue;
                }
                if (ob.collides(this.playerFish)) {
                    this.playerFish.die();
                }
            }
        }
    }

    public void initializeObjects() {
        container.removeAllViews();
        this.backgroundManger = new BackgroundManger(this.container, this.globalSpeed);
        for (Obstacle ob : obstacles) {
            ob.initialize(container);
        }

        for (Coin co : coins) {
            co.initialize(this.container);
        }

        this.score = new TextGameObject(this.container);
        setCoinsCounter();

        this.gameOverPanel = new GameOverPanel(this.container);
    }

    public void draw() {
        this.backgroundManger.drawBackgrounds();
        for (Obstacle ob : obstacles) {
            ob.draw();
        }
        for (Coin co : coins) {
            co.draw();
        }
        this.playerFish.draw();
        this.gameOverPanel.draw();
        score.draw();
        score.setText("" + collectedCoins);
    }

    private void setCoinsCounter() {
        this.score.setPosition(Constants.SCREEN_WIDTH / 2, 120);
        this.score.setTextColor(Color.rgb(255, 197, 57));
        this.score.setTextSize(40);
    }

    public void gameOver() {
        this.gameOverPanel.setVisible(true);
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

    public TextGameObject getScore() {
        return score;
    }

    public void setScore(TextGameObject score) {
        this.score = score;
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

    public GameOverPanel getGameOverPanel() {
        return gameOverPanel;
    }

    public void setGameOverPanel(GameOverPanel gameOverPanel) {
        this.gameOverPanel = gameOverPanel;
    }
}
