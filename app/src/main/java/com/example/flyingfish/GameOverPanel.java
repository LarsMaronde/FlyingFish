package com.example.flyingfish;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.flyingfish.activities.MainActivity;
import com.example.flyingfish.activities.MainMenuActivity;
import com.example.flyingfish.gameObjects.ImageGameObject;

public class GameOverPanel {

    ImageGameObject gameOverImage, retryImage, homeImage;


    public GameOverPanel(ViewGroup container) {
        gameOverImage = new ImageGameObject((FrameLayout) container, container.getResources().getDrawable(R.drawable.gameover), 800);
        gameOverImage.setCenteredPosition(300);

        retryImage = new ImageGameObject((FrameLayout) container, container.getResources().getDrawable(R.drawable.retry), 360);
        retryImage.setPosition(560, 800);

        homeImage = new ImageGameObject((FrameLayout) container, container.getResources().getDrawable(R.drawable.home), 360);
        homeImage.setPosition(180, 800);

        retryImage.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().restart();
            }
        });

        homeImage.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.getInstance(), MainMenuActivity.class);
                MainActivity.getInstance().startActivity(intent);
            }
        });

        this.setVisible(false);
    }

    public void update() {
        this.gameOverImage.update();
        this.retryImage.update();
        this.homeImage.update();
    }

    public void draw() {
        this.gameOverImage.draw();
        this.retryImage.draw();
        this.homeImage.draw();
    }

    public void setVisible(boolean val) {
        this.gameOverImage.setVisible(val);
        this.homeImage.setVisible(val);
        this.retryImage.setVisible(val);
    }
}
