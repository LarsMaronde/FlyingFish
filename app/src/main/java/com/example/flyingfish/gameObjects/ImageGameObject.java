package com.example.flyingfish.gameObjects;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.flyingfish.Constants;

public class ImageGameObject extends GameObject {
    protected double vx;
    private double x, y;
    private final ImageView view;
    private final double width;
    private final double height;


    public ImageGameObject(FrameLayout container, Drawable drawable, double width) {
        this.width = width;
        this.height = drawable.getIntrinsicHeight() * width / drawable.getIntrinsicWidth();
        view = new ImageView(container.getContext());
        view.setImageDrawable(drawable);
        container.addView(view);
        draw();
    }

    @Override
    public void draw() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = (int) width;
        params.height = (int) height;
        params.leftMargin = (int) x;
        params.topMargin = (int) y;
        params.gravity = Gravity.LEFT + Gravity.TOP;
        view.setLayoutParams(params);
    }

    @Override
    public void update() {
    }

    public double getWidth() {
        return width;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setCenteredPosition(double y) {
        this.x = Constants.SCREEN_WIDTH / 2 - width / 2;
        this.y = y;
    }

    @Override
    public void setVisible(boolean value) {
        view.setVisibility(View.GONE);
        view.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    public View getView() {
        return view;
    }
}
