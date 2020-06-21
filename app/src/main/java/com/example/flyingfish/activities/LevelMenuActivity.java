package com.example.flyingfish.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.example.flyingfish.Constants;
import com.example.flyingfish.R;
import com.example.flyingfish.dataObject.Level;
import com.example.flyingfish.db.DatabaseManager;

import java.util.LinkedList;

import androidx.appcompat.app.AppCompatActivity;

public class LevelMenuActivity extends AppCompatActivity {
    public static final String LEVEL_MESSAGE = "com.exampleflyingfish.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelmenu);

        createLevelButtons();

    }

    private void createLevelButtons() {
        GridLayout levelSelection = findViewById(R.id.levelSelectionList);

        LinkedList<Level> levels = DatabaseManager.getInstance().getAllLevelsFromUser(Constants.CURRENT_USERNAME);
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
                    startGame(v, l.getNumber());
                }
            });

            String buttonText = Integer.toString(l.getNumber())+"\n";

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
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}
