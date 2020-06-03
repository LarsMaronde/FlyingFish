package com.example.flyingfish.gameObjects;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;

import org.w3c.dom.Text;

public class TextGameObject extends GameObject {

    private TextView score;
    private double x;
    private double y;
    private boolean centered = false;

    public TextGameObject(ViewGroup container) {
        Context context = container.getContext();
        this.score = new TextView(context);
        container.addView(this.score);
    }

    @Override
    public void draw() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.score.getLayoutParams();
        params.leftMargin = (int) this.x;
        params.topMargin = (int) this.y;
        this.score.setLayoutParams(params);
    }

    @Override
    public void update() {

    }

    @Override
    public void setVisible(boolean value) {
        score.setVisibility(View.GONE);
        // score.setVisibility(value? View.VISIBLE:View.INVISIBLE);
        score.setVisibility(value? View.VISIBLE:View.INVISIBLE);
    }

    public void setText(String text){
        this.score.setText(text);
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setCentered(boolean centered){
        this.centered = centered;
    }

    public void setTextColor(int color){
        this.score.setTextColor(color);
    }

    public void setTextSize(float size){
        this.score.setTextSize(size);
    }

}
