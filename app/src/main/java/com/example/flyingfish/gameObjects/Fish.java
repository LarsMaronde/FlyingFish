package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.flyingfish.Constants;
import com.example.flyingfish.GamePanel;
import com.example.flyingfish.R;


public class Fish extends GameObject implements CircleHitbox {

    private ImageView imageView;
    private Drawable graphic1;
    private Rect rectangle;
    private double velocityCap; //sets the maximum value of velocity the player can make
    private double velocity;
    private double gravity;
    private double lift;
    private int x, y;
    private float width;

    public Fish(int x, int y, double gravity, double lift, double velocityCap, ViewGroup container) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
        this.velocityCap = velocityCap;
        this.gravity = gravity;
        this.lift = lift;
        Context context = container.getContext();
        graphic1 = context.getResources().getDrawable(R.drawable.fish1);
        this.imageView = new ImageView(context);
        this.imageView.setImageDrawable(graphic1);
        container.addView(this.imageView);
        this.width = graphic1.getIntrinsicWidth() * 2;
        this.rectangle = new Rect(0, 0, (int) this.width, graphic1.getIntrinsicHeight() * 2);
    }

    @Override
    public void draw() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.imageView.getLayoutParams();
        params.width = this.rectangle.width();
        params.height = this.rectangle.height();
        params.leftMargin = this.rectangle.left;
        params.topMargin = this.rectangle.top;
        this.imageView.setLayoutParams(params);
    }

    @Override
    public void update() {
        this.velocity += this.gravity;
//        if(this.velocity >= this.velocity*-1) {
//            this.velocity = this.velocityCap*-1;
//        }

        this.y += this.velocity;
        rectangle.set(x - rectangle.width() / 2, y - rectangle.height() / 2,
                x + rectangle.width() / 2, y + rectangle.height() / 2);

        if (this.y > Constants.SCREEN_HEIGHT - rectangle.height()) {
            this.y = Constants.SCREEN_HEIGHT - rectangle.height();
            this.velocity = 0;
        }

        if (this.y < 0) {
            this.y = 0;
            this.velocity = 0;
        }

    }

    public Rect getRectangle() {
        return this.rectangle;
    }

    public void swimUp() {
        this.velocity -= this.lift;
        if (this.velocity < this.velocityCap) {
            this.velocity = this.velocityCap;
        }
    }

    public void die() {
        this.gravity = 5;
        this.velocity += this.gravity;
        if (this.velocity > Constants.SCREEN_HEIGHT) {
           this.velocity = Constants.SCREEN_HEIGHT -20 ;
        }
        GamePanel.getInstance().gameOver();
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    public void setLook1() {
        this.imageView.setImageResource(R.drawable.fish1);
    }

    public void setLook2() {
        this.imageView.setImageResource(R.drawable.fish3);
    }
}
