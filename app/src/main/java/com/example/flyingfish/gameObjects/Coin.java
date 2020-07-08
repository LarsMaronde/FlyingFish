package com.example.flyingfish.gameObjects;

import android.content.Context;
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


        float yRatio = (float) Constants.SCREEN_HEIGHT/Constants.GOAL_HEIGHT_RATIO;
        this.y = (int) (this.y*yRatio);

        Context context = container.getContext();
        Drawable graphic = context.getResources().getDrawable(R.drawable.coin);
        this.width = 80;

        //dynamic scaling for different sceen sizes
        this.width = Constants.SCREEN_HEIGHT / 23;

        this.imageView = new ImageView(context);
        this.imageView.setImageDrawable(graphic);
        this.imageView.setVisibility(View.GONE);
        container.addView(this.imageView);
    }

    @Override
    public boolean collides(GameObject obj) {
        if(obj != null) {
            if(obj instanceof CircleHitbox) {

                double x2 = ((CircleHitbox) obj).getX();
                double y2 = ((CircleHitbox) obj).getY();

                //accurate method but compute intensive
                double dist = Math.sqrt(Math.pow(x2-this.x,2) + Math.pow(y2-this.y,2));
                return dist < (this.width/2 + ((CircleHitbox) obj).getWidth()/2);


                //works 2x faster but not as accurate
//                double r = ((CircleHitbox) obj).getWidth()/2;
//                boolean xinter = this.x > x2-r  && this.x < x2+r;
//                boolean yinter = this.y < y2+r && this.y > y2-r;
//                return xinter && yinter;

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
