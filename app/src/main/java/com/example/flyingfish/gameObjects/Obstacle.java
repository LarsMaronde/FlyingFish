package com.example.flyingfish.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.flyingfish.Constants;

public class Obstacle implements GameObject {


    private enum Orientation {
        TOP, BOTTOM;
    }

    private Rect rectangle;
    private int color;

    private Orientation orientation;
    private int type;
    private double spawnTime;
    private int height;
    private int width;
    private double speed;


    private boolean visible;


    public Obstacle() {/*empty*/}

    public boolean collides(GameObject obj) {
        if(obj instanceof CircleHitbox) {
            float radius = ((CircleHitbox) obj).getWidth()/2;
            float x = ((CircleHitbox) obj).getX();
            float y = ((CircleHitbox) obj).getY();

            for(int i = 0; i < 360; i++) {
                double cX = Math.cos(i) * radius + x;
                double cY = Math.sin(i) * radius + y;
                if(rectangle.contains((int)cX, (int)(cY))){
                    return true;
                }
            }
            return false;
        }else {
            return Rect.intersects(rectangle, obj.getRectangle());
        }
    }


    public void createRectangle() {
        if(this.orientation.equals(Orientation.TOP)) {
            this.rectangle = new Rect(Constants.SCREEN_WIDTH, 0, Constants.SCREEN_WIDTH+this.width, this.height);
            this.color = Color.rgb(0,255,0);
        }else if(this.orientation.equals(Orientation.BOTTOM)) {
            this.rectangle = new Rect(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-this.height, Constants.SCREEN_WIDTH+this.width, Constants.SCREEN_HEIGHT);
            this.color = Color.rgb(0,0,255);
        }
    }


    @Override
    public void draw(Canvas canvas) {
        if(!this.visible){
            return;
        }
        if(rectangle != null) {
            Paint paint = new Paint();
            paint.setColor(this.color);
            canvas.drawRect(rectangle, paint);
        }else {
            System.err.println("Rectangle is null ");
        }
    }

    @Override
    public void update() {
        rectangle.left -= speed;
        rectangle.right -= speed;
    }








    @Override
    public Rect getRectangle() {
        return this.rectangle;
    }

    public void setRectangle(Rect rectangle) {
        this.rectangle = rectangle;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(double spawnTime) {
        this.spawnTime = spawnTime;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
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


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
