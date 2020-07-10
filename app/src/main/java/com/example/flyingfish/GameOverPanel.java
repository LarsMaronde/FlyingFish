package com.example.flyingfish;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.flyingfish.activities.MainActivity;
import com.example.flyingfish.activities.MainMenuActivity;
import com.example.flyingfish.gameObjects.ImageGameObject;
import com.example.flyingfish.gameObjects.SoundPlayer;
import com.example.flyingfish.gameObjects.TextGameObject;

public class GameOverPanel {

    private ImageGameObject gameOverImage, retryImage, homeImage;
    private TextGameObject collectedCoins;

    public GameOverPanel(ViewGroup container) {
        gameOverImage = new ImageGameObject((FrameLayout) container, container.getResources().getDrawable(R.drawable.gameover), 650);
        gameOverImage.setCenteredPosition(400);

        homeImage = new ImageGameObject((FrameLayout) container, container.getResources().getDrawable(R.drawable.home), 650);
        homeImage.setCenteredPosition(820);

        retryImage = new ImageGameObject((FrameLayout) container, container.getResources().getDrawable(R.drawable.retry), 650);
        retryImage.setCenteredPosition(990);

        collectedCoins = new TextGameObject(container);
        collectedCoins.setText("");
        collectedCoins.setTextColor(Color.rgb(255, 197, 57));
        collectedCoins.setTextSize(36);

        int width = this.collectedCoins.getWidth()/2;
        // System.out.println(width);
        collectedCoins.setPosition(Constants.SCREEN_WIDTH/2-width,620);

        retryImage.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().restart();
            }
        });

        homeImage.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.getInstance().stopBackground();
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
        collectedCoins.update();
    }

    public void draw() {
        this.gameOverImage.draw();
        this.retryImage.draw();
        this.homeImage.draw();
        collectedCoins.draw();
    }

    public void setVisible(boolean val) {
        this.gameOverImage.setVisible(val);
        this.homeImage.setVisible(val);
        this.retryImage.setVisible(val);
        this.collectedCoins.setVisible(val);
    }

    public void setCollectedCoins(int collectedCoins){
        this.collectedCoins.setText("SCORE " + collectedCoins);
        int width = this.collectedCoins.getWidth()/2;
        this.collectedCoins.setPosition(Constants.SCREEN_WIDTH/2-width,620);
    }
}
