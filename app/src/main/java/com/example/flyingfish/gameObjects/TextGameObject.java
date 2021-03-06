/**
 * a positionable graphical object (sprite) displaying a string object as a text on game screen,
 * size and color of the text are configurable,
 * background of the text is translucent
 */

package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TextGameObject extends GameObject {

    private TextView text;
    private double x;
    private double y;

    public TextGameObject(ViewGroup container) {
        Context context = container.getContext();
        this.text = new TextView(context);
        container.addView(this.text);
    }

    @Override
    public void draw() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.text.getLayoutParams();
        params.leftMargin = (int) this.x;
        params.topMargin = (int) this.y;
        this.text.setLayoutParams(params);
    }

    @Override
    public void update() {

    }

    public void setVisible(boolean value) {
        text.setVisibility(View.GONE);
        text.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setTextColor(int color) {
        this.text.setTextColor(color);
    }

    public void setTextSize(float size) {
        this.text.setTextSize(size);
    }

    public int getWidth() {
        text.measure(0, 0);
        return text.getMeasuredWidth();
    }
}
