/** the background manager controls the parallax scroll layers
 */

package com.example.flyingfish.gameObjects.background;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;

import java.util.LinkedList;

public class BackgroundManger {

    private LinkedList<Background> backgrounds;

    public BackgroundManger(ViewGroup container, double globalSpeed) {
        Context context = container.getContext();
        container.setBackgroundColor(Color.rgb(62,121,221));

        this.backgrounds = new LinkedList<Background>();

        Drawable bg1 = context.getResources().getDrawable(R.drawable.backgroundfar);
        backgrounds.add(new Background(bg1, bg1.getIntrinsicWidth(), Constants.SCREEN_HEIGHT-400, globalSpeed * 0.4, container));

        Drawable bg2 = context.getResources().getDrawable(R.drawable.backgroundmiddle);
        backgrounds.add(new Background(bg2, bg2.getIntrinsicWidth(), Constants.SCREEN_HEIGHT-200, globalSpeed * 0.6, container));

        Drawable bg3 = context.getResources().getDrawable(R.drawable.background2);
        backgrounds.add(new Background(bg3, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT-100, globalSpeed * 0.7, container));


        Drawable bg4 = context.getResources().getDrawable(R.drawable.frontbottom);
        backgrounds.add(new Background(bg4, Constants.SCREEN_WIDTH, (int) (Constants.SCREEN_HEIGHT), globalSpeed, container));

        Drawable bg5 = context.getResources().getDrawable(R.drawable.fronttop);
        backgrounds.add(new Background(bg5, Constants.SCREEN_WIDTH, (int) (bg5.getIntrinsicHeight()), globalSpeed, container));
    }

    /**
     *  method updated background
     */
    public void updateBackgrounds() {
        for(Background bg: backgrounds){
            bg.update();
        }
    }

    /**
     *  draws background layers on the game screen
     */
    public void drawBackgrounds() {
        for(Background bg: backgrounds) {
            bg.draw();
        }
    }
}
