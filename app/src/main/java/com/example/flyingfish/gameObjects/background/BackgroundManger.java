package com.example.flyingfish.gameObjects.background;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;

import java.util.LinkedList;

public class BackgroundManger {

    private LinkedList<Background> backgrounds;

    public BackgroundManger(ViewGroup container, double globalSpeed) {
        Context context = container.getContext();

        this.backgrounds = new LinkedList<Background>();

        Drawable bg1 = context.getResources().getDrawable(R.drawable.backbackground);
        backgrounds.add(new Background(bg1, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-500, globalSpeed, container));

        Drawable bg2 = context.getResources().getDrawable(R.drawable.middlebackround);
        backgrounds.add(new Background(bg2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-300, globalSpeed, container));

        Drawable bg3 = context.getResources().getDrawable(R.drawable.background2);
        backgrounds.add(new Background(bg3, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-200, globalSpeed, container));

        Drawable bg4 = context.getResources().getDrawable(R.drawable.frontbottom);
        backgrounds.add(new Background(bg4, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-100, globalSpeed, container));

        Drawable bg5 = context.getResources().getDrawable(R.drawable.fronttop);
        backgrounds.add(new Background(bg5, Constants.SCREEN_WIDTH, bg5.getIntrinsicHeight() , globalSpeed, container));
    }

    public void updateBackgrounds() {
        for(Background bg: backgrounds){
            bg.update();
        }
    }

    public void drawBackgrounds() {
        for(Background bg: backgrounds) {
            bg.draw();
        }
    }
}
