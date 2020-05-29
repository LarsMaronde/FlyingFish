package com.example.flyingfish.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;


public class Fish implements GameObject, CircleHitbox {

    private Bitmap graphic;
    private Rect rectangle;
    private final double velocityCap = -20;
    private double velocity;
    private double gravity;
    private double lift;
    private int color;
    private int x;
    private int y;
    private float width;

    public Fish(int x, int y, double gravity, double lift) {

        this.color = Color.rgb(255, 255,0);
        this.x = x;
        this.y = y;
        this.velocity = 0;
        this.gravity = gravity;
        this.lift = lift;

        BitmapFactory bf = new BitmapFactory();
        graphic = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fish1);
        this.rectangle = new Rect(0,0, (int)(graphic.getWidth()*1.5), (int)(graphic.getHeight()*1.5));
        this.width = (float) (this.graphic.getWidth()*1.5);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.color);
        paint.setAntiAlias(false);
        canvas.drawCircle(this.x, this.y, this.width/2, paint);
//        canvas.drawRect(this.rectangle, paint);
        canvas.drawBitmap(graphic, null, this.rectangle, paint);
    }

    @Override
    public void update() {
        this.velocity += this.gravity;
//        if(this.velocity >= this.velocity*-1) {
//            this.velocity = this.velocityCap*-1;
//        }
        this.y += this.velocity;
        rectangle.set(x - rectangle.width()/2, y - rectangle.height()/2,
                x + rectangle.width()/2, y + rectangle.height()/2);

        if(this.y > Constants.SCREEN_HEIGHT-rectangle.height()) {
            this.y = Constants.SCREEN_HEIGHT-rectangle.height();
            this.velocity = 0;
        }

        if(this.y < 0) {
            this.y = 0;
            this.velocity = 0;
        }

    }

    @Override
    public Rect getRectangle() {
        return this.rectangle;
    }

    public void swimUp() {
        this.velocity -= this.lift;
        if(this.velocity < this.velocityCap) {
            this.velocity = this.velocityCap;
        }
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
}
