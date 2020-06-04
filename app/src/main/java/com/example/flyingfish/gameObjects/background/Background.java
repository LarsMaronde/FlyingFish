package com.example.flyingfish.gameObjects.background;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.flyingfish.Constants;
import com.example.flyingfish.gameObjects.GameObject;

public class Background extends GameObject {

    private Drawable graphic;
    private ImageView imageView;
    private ImageView imageView2;
    private int x, y, x2;
    private double speed;

    public Background(Drawable graphic, int x, int y, double speed, ViewGroup container) {
        this.x = x;
        this.x2 = 10;
        this.y = y;
        this.speed = speed;
        this.graphic = graphic;
        this.imageView = new ImageView(container.getContext());
        this.imageView2 = new ImageView(container.getContext());

        this.imageView.setImageDrawable(graphic);
        this.imageView2.setImageDrawable(graphic);

        container.addView(this.imageView);
        container.addView(this.imageView2);
    }

    @Override
    public void draw() {
        int width = this.graphic.getIntrinsicWidth();
        int height = this.graphic.getIntrinsicHeight();

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.imageView.getLayoutParams();
        params.width = width;
        params.height = height;
        params.leftMargin = this.x;
        params.topMargin = this.y-height;
        this.imageView.setLayoutParams(params);


        FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) this.imageView2.getLayoutParams();
        params2.width = width;
        params2.height = height;
        params2.leftMargin = this.x2;
        params2.topMargin = this.y-height;
        this.imageView2.setLayoutParams(params2);

    }

    @Override
    public void update() {
        this.x -= speed;
        this.x2 -= speed;
        if(this.x+this.graphic.getIntrinsicWidth()-20 < 0) {
            this.x = Constants.SCREEN_WIDTH;
        }
        if(this.x2+this.graphic.getIntrinsicWidth()-20 < 0) {
            this.x2 = Constants.SCREEN_WIDTH;
        }
    }
}
