package com.example.flyingfish.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;

public class Obstacle extends GameObject implements Interactable, RectHitbox {


    private enum Orientation {
        TOP, BOTTOM;
    }

    private Bitmap graphic;
    private Paint paint;
    private Rect rectangle;

    private Orientation orientation;
    private int type;
    private double spawnTime;
    private int height;
    private int width;
    private double speed;

    private boolean visible;



    public Obstacle() {/*empty*/}

    @Override
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
        }else if(obj instanceof RectHitbox) {
            return Rect.intersects(rectangle, ((RectHitbox) obj).getRectangle());
        }
        return false;
    }


    public void initialize() {
        if(this.orientation.equals(Orientation.TOP)) {
            if(this.width <= 100) {
                this.graphic = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.obstacletop100px);
            }else {
                this.graphic = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.obstacletop200px);
            }
            this.rectangle = new Rect(Constants.SCREEN_WIDTH, 0, Constants.SCREEN_WIDTH+this.width, this.height);
        }else if(this.orientation.equals(Orientation.BOTTOM)) {
            if(this.width <= 100) {
                this.graphic = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.obstaclebottom100px);
            }else {
                this.graphic = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.obstaclebottom200px);
            }
            this.rectangle = new Rect(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-this.height, Constants.SCREEN_WIDTH+this.width, Constants.SCREEN_HEIGHT);
        }
        this.visible = false;

        this.paint = new Paint();
    }


    @Override
    public void draw(Canvas canvas) {
        if(!this.visible){
            return;
        }
        canvas.drawBitmap(graphic, null, this.rectangle, this.paint);
    }

    @Override
    public void update() {
        rectangle.left -= speed;
        rectangle.right -= speed;
    }


    public Rect getRectangle() {
        return this.rectangle;
    }

    public void setRectangle(Rect rectangle) {
        this.rectangle = rectangle;
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
