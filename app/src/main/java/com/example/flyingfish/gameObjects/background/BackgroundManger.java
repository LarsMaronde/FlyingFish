package com.example.flyingfish.gameObjects.background;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;

import java.util.LinkedList;

public class BackgroundManger {

    private LinkedList<Background> backgrounds;

    public BackgroundManger() {
        this.backgrounds = new LinkedList<Background>();
        backgrounds.add(new Background(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.backbackground),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-500, 1));
        backgrounds.add(new Background(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.middlebackround),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-300, 2));
        backgrounds.add(new Background(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.background2),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-200, 3));
        backgrounds.add(new Background(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.frontbottom),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-100, 5));
        backgrounds.add(new Background(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fronttop),
                Constants.SCREEN_WIDTH, 0, 5));

    }

    public void updateBackgrounds() {
        for(Background bg: backgrounds){
            bg.update();
        }
    }

    public void drawBackgrounds(Canvas canvas){
        for(Background bg: backgrounds) {
            bg.draw(canvas);
        }
    }
}
