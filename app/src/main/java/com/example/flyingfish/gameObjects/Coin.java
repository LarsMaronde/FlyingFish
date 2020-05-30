package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;


public class Coin extends GameObject implements Interactable, CircleHitbox {


    private ImageView imageView;

    private int value;
    private int x, y;
    private double spawnTime;
    private int width;
    private double speed;
    private boolean visible;

    public Coin() {/*empty*/}


    public void initialize(ViewGroup container) {
        this.visible = false;
        this.x = Constants.SCREEN_WIDTH+this.width/2;

        Context context = container.getContext();
        Drawable graphic = context.getResources().getDrawable(R.drawable.coin);
        this.width = 80;
        this.imageView = new ImageView(context);
        this.imageView.setImageDrawable(graphic);
        this.imageView.setVisibility(View.GONE);
        container.addView(this.imageView);
    }

    @Override
    public boolean collides(GameObject obj) {
        if(obj != null) {
            if(obj instanceof CircleHitbox) {
                int x2 = ((CircleHitbox) obj).getX();
                int y2 = ((CircleHitbox) obj).getY();
                double dist = Math.sqrt(Math.pow(x2-this.x,2) + Math.pow(y2-this.y,2));

                return dist < (this.width/2 + ((CircleHitbox) obj).getWidth()/2);

            }else if(obj instanceof RectHitbox) {
                return false;
            }
        }
        return false;
    }


    @Override
    public void draw() {
        if(this.visible){
            this.imageView.setVisibility(View.VISIBLE);
            int r = this.width/2;
//            Rect bound = new Rect(this.x-r, this.y-r,this.x+r, this.y+r);
//            canvas.drawBitmap(graphic, null, bound, paint);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.imageView.getLayoutParams();
            params.width = this.width;
            params.height = this.width;
            params.leftMargin = this.x-r;
            params.topMargin = this.y-r;
            this.imageView.setLayoutParams(params);

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
        this.imageView.setVisibility(View.GONE);
    }

    public boolean isVisible() {
        return this.visible;
    }
}
