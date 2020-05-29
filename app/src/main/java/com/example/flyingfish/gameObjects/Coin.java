package com.example.flyingfish.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.flyingfish.Constants;


public class Coin extends GameObject implements Interactable, CircleHitbox {

    private int value;
    private int x, y;
    private double spawnTime;
    private int color;
    private int width;
    private double speed;
    private boolean visible;

    public Coin() {/*empty*/}


    public void initialize() {
        this.color = Color.rgb(122,122,122);
        this.width = 50;
        this.visible = false;
        this.x = Constants.SCREEN_WIDTH+this.width/2;
    }

    @Override
    public boolean collides(GameObject obj) {

        if(obj instanceof CircleHitbox) {
            int x2 = ((CircleHitbox) obj).getX();
            int y2 = ((CircleHitbox) obj).getY();
            double dist = Math.sqrt(Math.pow(x2-this.x,2) + Math.pow(y2-this.y,2));

            return dist < (this.width/2 + ((CircleHitbox) obj).getWidth()/2);

        }else if(obj instanceof RectHitbox){
            return false;
        }
        return false;
    }


    @Override
    public void draw(Canvas canvas) {
        if(this.visible){
            Paint paint = new Paint();
            paint.setColor(this.color);
            paint.setAntiAlias(false);
            canvas.drawCircle(x, y, this.width/2, paint);
        }
    }

    @Override
    public void update() {
        this.x -= speed;
    }



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(double spawnTime) {
        this.spawnTime = spawnTime;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }
}
