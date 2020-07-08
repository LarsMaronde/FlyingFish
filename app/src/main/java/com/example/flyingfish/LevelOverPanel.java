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

public class LevelOverPanel {

    ImageGameObject gameOverImage, homeImage;
    TextGameObject collectedCoins;

    public LevelOverPanel(ViewGroup container) {
        gameOverImage = new ImageGameObject((FrameLayout) container, container.getResources().getDrawable(R.drawable.success), 650);
        gameOverImage.setCenteredPosition(400);

        homeImage = new ImageGameObject((FrameLayout) container, container.getResources().getDrawable(R.drawable.home), 650);
        homeImage.setCenteredPosition(820);

        collectedCoins = new TextGameObject(container);
        collectedCoins.setText("");
        collectedCoins.setTextColor(Color.rgb(255, 197, 57));
        collectedCoins.setTextSize(36);
        collectedCoins.setPosition(320,620);

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
        this.homeImage.update();
        this.collectedCoins.update();
    }

    public void draw() {
        this.gameOverImage.draw();
        this.homeImage.draw();
        this.collectedCoins.draw();
    }

    public void setVisible(boolean val) {
        this.gameOverImage.setVisible(val);
        this.homeImage.setVisible(val);
        this.collectedCoins.setVisible(val);
    }

    public void setCollectedCoins(int collectedCoins) {
        this.collectedCoins.setText("SCORE " + collectedCoins);
    }
}
