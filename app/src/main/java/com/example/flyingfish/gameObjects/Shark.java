package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.graphics.Rect;
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

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

public class Shark extends GameObject implements Interactable, RectHitbox {

    @JsonIgnore
    private Rect rectangle;
    @JsonIgnore
    private ImageView imageView;
    @JsonIgnore
    private GifDrawable gif;

    private double spawnTime;
    private int height;
    private int width;
    private double speed;
    private boolean visible;
    private int y;

    public Shark() {
        //empty
    }

    public void initialize(ViewGroup container) {


        Context context = container.getContext();

        try {
            this.gif = new GifDrawable(context.getResources(), R.drawable.shark);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.width = gif.getIntrinsicWidth();
        this.height = gif.getIntrinsicHeight();

        //dynamic scaling for different sceen sizes

        this.width = (int) (Constants.SCREEN_WIDTH / 1.9);

        this.height = (int) (Constants.SCREEN_HEIGHT / 8.4);

        System.out.println("::: :::: ::::"+this.width +" /"+this.height+" /"+Constants.SCREEN_HEIGHT);

        this.rectangle = new Rect(Constants.SCREEN_WIDTH, y, Constants.SCREEN_WIDTH+this.width, this.height+y);

        this.imageView = new ImageView(context);
        this.imageView.setImageDrawable(this.gif);
        this.imageView.setAdjustViewBounds(true);
        container.addView(this.imageView);

    }

    @Override
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

    @Override
    public boolean collides(GameObject obj) {

        // because the collision box of the shark is a rectangle,
        // it is better so make the collision box smaller for a better user experience because
        // the shark image is shaped like an oval
        int margin = 80;
        Rect newRect = new Rect(rectangle.left-margin, rectangle.top+margin, rectangle.right-margin, rectangle.bottom-margin);

        if(obj instanceof CircleHitbox) {
            float radius = ((CircleHitbox) obj).getWidth()/2;
            float x = ((CircleHitbox) obj).getX();
            float y = ((CircleHitbox) obj).getY();

            for(int i = 0; i < 360; i++) {
                double cX = Math.cos(i) * radius + x;
                double cY = Math.sin(i) * radius + y;
                if(newRect.contains((int)cX, (int)(cY))){
                    return true;
                }
            }
            return false;
        }else if(obj instanceof RectHitbox) {
            return Rect.intersects(newRect, ((RectHitbox) obj).getRectangle());
        }
        return false;
    }

    @Override
    public Rect getRectangle() {
        return this.rectangle;
    }



    //GETTER SETTER
    public void setRectangle(Rect rectangle) {
        this.rectangle = rectangle;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public GifDrawable getGif() {
        return gif;
    }

    public void setGif(GifDrawable gif) {
        this.gif = gif;
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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
