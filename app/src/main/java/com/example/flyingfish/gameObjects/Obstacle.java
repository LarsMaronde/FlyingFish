package com.example.flyingfish.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject {

    private enum Orientation {
        TOP, BOTTOM;
    }


    private int showingTime;
    private Rect rectangle;
    private int color;

    private Orientation orientation;
    private int type;
    private double spawnTime;



    public Obstacle() {/*empty*/}

    public Obstacle(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public boolean collision(GameObject obj) {
        return rectangle.contains(obj.getRectangle().left, obj.getRectangle().top) ||
                rectangle.contains(obj.getRectangle().right, obj.getRectangle().top) ||
                rectangle.contains(obj.getRectangle().left, obj.getRectangle().bottom) ||
                rectangle.contains(obj.getRectangle().right, obj.getRectangle().bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    @Override
    public Rect getRectangle() {
        return this.rectangle;
    }




    public int getShowingTime() {
        return showingTime;
    }

    public void setShowingTime(int showingTime) {
        this.showingTime = showingTime;
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
}
