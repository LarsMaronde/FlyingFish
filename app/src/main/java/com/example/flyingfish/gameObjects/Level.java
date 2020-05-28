package com.example.flyingfish.gameObjects;

import androidx.annotation.NonNull;

import java.util.LinkedList;

public class Level {

    private int number;
    private double gravity;
    private Fish playerFish;
    private LinkedList<Obstacle> obstacles;
    private LinkedList<Coin> coins;

    public Level() {/*empty*/}

    @NonNull
    @Override
    public String toString() {
        return "Level numbers:"+ this.number + "/"+this.gravity+"/"
                +obstacles.size()+"/"+coins.size();
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
}
