package com.example.flyingfish.gameObjects;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Coin implements GameObject {

    private int value;
    private double y;
    private double spawnTime;

    public Coin() {/*empty*/}

    public Coin(int value, double y) {
        this.value = value;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update() {

    }

    @Override
    public Rect getRectangle() {
        return null;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(double spawnTime) {
        this.spawnTime = spawnTime;
    }
}
