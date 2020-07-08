package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;
import com.example.flyingfish.gameObjects.interfaces.CircleHitbox;
import com.example.flyingfish.gameObjects.interfaces.Interactable;
import com.example.flyingfish.gameObjects.interfaces.RectHitbox;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Obstacle extends GameObject implements Interactable, RectHitbox {

    private enum Orientation {
        TOP, BOTTOM;
    }

    @JsonIgnore
    private ImageView imageView;
    @JsonIgnore
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


    public void initialize(ViewGroup container) {
        float widthRatio = (float) Constants.SCREEN_WIDTH/Constants.GOAL_WIDTH_RATIO;
        float heightRatio = (float) Constants.SCREEN_HEIGHT/Constants.GOAL_HEIGHT_RATIO;
        this.width = (int) (widthRatio * this.width);
        this.height = (int) (heightRatio * this.height);

        Context context = container.getContext();
        Drawable graphic = null;
        if(this.orientation.equals(Orientation.TOP)) {
            if(this.width <= 100) {
                graphic = context.getResources().getDrawable(R.drawable.obstacletop100px);
            }else {
                graphic = context.getResources().getDrawable(R.drawable.obstacletop200px);
            }
            this.rectangle = new Rect(Constants.SCREEN_WIDTH, 0, Constants.SCREEN_WIDTH+this.width, this.height);

        }else if(this.orientation.equals(Orientation.BOTTOM)) {
            if(this.width <= 100) {
                graphic = context.getResources().getDrawable(R.drawable.obstaclebottom100px);
            }else {
                graphic = context.getResources().getDrawable(R.drawable.obstaclebottom200px);
            }
            this.rectangle = new Rect(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-this.height, Constants.SCREEN_WIDTH+this.width, Constants.SCREEN_HEIGHT);
        }

        Bitmap bitmap = ((BitmapDrawable) graphic).getBitmap();
        Drawable d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, this.width, this.height, true));

        this.visible = false;
        this.imageView = new ImageView(context);
        this.imageView.setVisibility(View.VISIBLE);
        this.imageView.setImageDrawable(d);

        this.imageView.setAdjustViewBounds(true);
        container.addView(this.imageView);
    }


    public void draw() {
        if(!this.visible) {
            this.imageView.setVisibility(View.GONE);
            return;
        }

        this.imageView.setVisibility(View.VISIBLE);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.imageView.getLayoutParams();
        params.width = this.width;
        params.height = this.height;
        params.leftMargin = this.rectangle.left;
        params.topMargin = this.rectangle.top;
        this.imageView.setLayoutParams(params);
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
        this.imageView.setVisibility(View.GONE);
    }

    public ImageView getImageView() {
        return imageView;
    }


}
