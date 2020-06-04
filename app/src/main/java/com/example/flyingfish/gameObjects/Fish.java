package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.flyingfish.Constants;
import com.example.flyingfish.GamePanel;
import com.example.flyingfish.R;


public class Fish extends GameObject implements CircleHitbox {

    private ImageView imageView;
    private Rect rectangle;
    private double velocityCap; //sets the maximum value of velocity the player can make
    private double velocity;
    private double gravity;
    private double lift;
    private int x, y;
    private float width;
    private enum State {ALIVE, ROTTEN, DEAD}
    private State state = State.ALIVE;

    public Fish(int x, int y, double gravity, double lift, double velocityCap, ViewGroup container) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
        this.velocityCap = velocityCap;
        this.gravity = gravity;
        this.lift = lift;
        Context context = container.getContext();
        Drawable graphic1 = context.getResources().getDrawable(R.drawable.fish1);
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
        this.y += this.velocity;

        if(this.checkCollisionWithTopOrBottom()){
            this.die();
        }

        rectangle.set(x - rectangle.width() / 2, y - rectangle.height() / 2,
                x + rectangle.width() / 2, y + rectangle.height() / 2);

        if (this.y > Constants.SCREEN_HEIGHT - rectangle.height()) {
//            this.y = Constants.SCREEN_HEIGHT - rectangle.height();
            this.velocity = 0;
            if(state == State.DEAD){
                state = State.ROTTEN;
                GamePanel.getInstance().gameOver();
            }
        }

        if (this.y < 0) {
//            this.y = 0;
            this.velocity = 0;
        }

    }

    public Rect getRectangle() {
        return this.rectangle;
    }

    public void swimUp() {
        if(state != State.ALIVE) return;
        this.velocity -= this.lift;
        if (this.velocity < this.velocityCap) {
            this.velocity = this.velocityCap;
        }
    }

    public void die() {
        if(state != State.ALIVE) return;
        velocity = 0;
        gravity = 0.06;
        state = State.DEAD;
    }

    public boolean checkCollisionWithTopOrBottom() {
        return this.y + this.width >=Constants.SCREEN_HEIGHT ||
                this.y - this.width <= 0;
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
