package com.example.flyingfish.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;
import com.example.flyingfish.db.dataObject.Item;
import com.example.flyingfish.db.dataObject.Level;
import com.example.flyingfish.db.dataObject.User;
import com.example.flyingfish.db.dataObject.management.DataObjectManager;
import com.example.flyingfish.db.dataObject.management.Observer;

import java.util.ArrayList;
import java.util.List;

public class LevelMenuActivity extends AppCompatActivity implements Observer {
    public static final String LEVEL_MESSAGE = "com.exampleflyingfish.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelmenu);

        //listen for changes
        DataObjectManager.getInstance().addObserver(this);

        //initialize with the current values the manager has
        createLevelButtons(getLevels());

    }

    private List<Level> getLevels() {
        ArrayList<Level> newList = new ArrayList<>();

        List<Level> allLevels = DataObjectManager.getInstance().getAllLevels();
        List<Level> userLevels = DataObjectManager.getInstance().getLevelsOfUser(Constants.CURRENT_USERNAME);

        for(int i = 0; i < allLevels.size(); i++) {
            Level c = allLevels.get(i);
            newList.add(new Level(c.getLevelNumber(), c.getMaxCoinAmount()));
        }

        for(int i = 0; i < newList.size(); i++) {
            Level level = newList.get(i);
            if(userLevels != null){
                for(int j = 0; j < userLevels.size(); j++) {
                    Level userLevel = userLevels.get(j);
                    if(level.getLevelNumber() == userLevel.getLevelNumber()){
                        level.setCollectedCoins(userLevel.getCollectedCoins());
                        level.setUnlocked(true);
                    }
                }
            }
        }

        DataObjectManager.bubbleSort(newList, true);
        return newList;
    }

    private void createLevelButtons(List<Level> levels) {
        GridLayout levelSelection = findViewById(R.id.levelSelectionList);
        levelSelection.removeAllViews();

        for(final Level l: levels) {
            final Button levelButton = new Button(this);

            levelButton.setBackgroundColor(Color.rgb(255,84,0));
            levelButton.setTextColor(Color.rgb(255,255,255));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20,20,20,20);
            levelButton.setLayoutParams(params);

            levelButton.setHeight(200);
            levelButton.setWidth(200);
            levelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startGame(v, l.getLevelNumber());
                }
            });

            String buttonText = Integer.toString(l.getLevelNumber())+"\n";

            if(l.isUnlocked()) {
                levelButton.setEnabled(true);
                buttonText+= "("+l.getPercentageCompleted()+"%)";
            }else{
                levelButton.setEnabled(false);
                levelButton.setBackgroundColor(Color.rgb(255,144,90));
            }
            levelButton.setText(buttonText);
            levelSelection.addView(levelButton);
        }

    }


    public void startGame(View v, int level) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(LEVEL_MESSAGE, level);
        startActivity(intent);
    }



    public void backToMainMenu(View v) {
        DataObjectManager.getInstance().removeObserver(this);
        startActivity(new Intent(this, MainMenuActivity.class));
    }


    @Override
    public void updateUsers(ArrayList<User> users) {
        createLevelButtons(getLevels());
    }

    @Override
    public void updateItems(ArrayList<Item> items) {

    }

    @Override
    public void updateLevel(ArrayList<Level> levels) {
        createLevelButtons(getLevels());
    }
}
