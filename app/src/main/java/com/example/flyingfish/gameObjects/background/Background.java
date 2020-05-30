package com.example.flyingfish.gameObjects.background;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.flyingfish.Constants;
import com.example.flyingfish.gameObjects.GameObject;

public class Background extends GameObject {

    private Bitmap graphic;
    private int x, y, x2;
    private double speed;

    public Background(Bitmap graphic, int x, int y, double speed) {
        this.graphic = graphic;
        this.x = x;
        this.x2 = 10;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(false);

        Rect bound = new Rect(this.x,this.y-this.graphic.getHeight(),this.x+this.graphic.getWidth(),this.y);
        canvas.drawBitmap(this.graphic,null, bound, paint);

        Rect bound2 = new Rect(this.x2,this.y-this.graphic.getHeight(),this.x2+this.graphic.getWidth(),this.y);
        canvas.drawBitmap(this.graphic,null, bound2, paint);
    }

    @Override
    public void update() {
        this.x -= speed;
        this.x2 -= speed;
        if(this.x+this.graphic.getWidth() < 0) {
            this.x = Constants.SCREEN_WIDTH;
        }
        if(this.x2+this.graphic.getWidth() < 0) {
            this.x2 = Constants.SCREEN_WIDTH;
        }
    }
}
