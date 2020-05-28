package com.example.flyingfish.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;


public class Fish implements GameObject {

    Bitmap graphic;
    private Rect rectangle;
    private int color;
    private int x;
    private int y;
    private double velocity;
    private double gravity;
    private double lift;

    private final int width = 150;

    public Fish(int x, int y, double gravity, double lift) {
        this.rectangle = new Rect(0,0, width, width);
        this.color = Color.rgb(255, 255,0);
        this.x = x;
        this.y = y;
        this.velocity = 0;
        this.gravity = gravity;
        this.lift = lift;

        BitmapFactory bf = new BitmapFactory();
        graphic = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fish);

    }

    @Override
    public void draw(Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setColor(this.color);
//        canvas.drawRect(this.rectangle, paint);
        canvas.drawBitmap(graphic, null, this.rectangle, new Paint());
    }

    @Override
    public void update() {
        this.velocity += this.gravity;
        this.y += this.velocity;
        rectangle.set(x - rectangle.width()/2, y - rectangle.height()/2,
                x + rectangle.width()/2, y + rectangle.height()/2);

        if(this.y > Constants.SCREEN_HEIGHT-rectangle.height()*2) {
            this.y = Constants.SCREEN_HEIGHT-rectangle.height()*2;
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
    }


}
