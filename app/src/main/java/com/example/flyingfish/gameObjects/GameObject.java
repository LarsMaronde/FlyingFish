package com.example.flyingfish.gameObjects;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface GameObject {

    public void draw(Canvas canvas);
    public void update();
    public Rect getRectangle();

}
