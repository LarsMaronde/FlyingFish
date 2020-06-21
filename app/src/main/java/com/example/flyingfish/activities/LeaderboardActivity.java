package com.example.flyingfish.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.R;
import com.example.flyingfish.dataObject.User;
import com.example.flyingfish.db.DatabaseManager;

import java.util.LinkedList;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        fillLeaderboard();
    }

    private void fillLeaderboard() {
        LinearLayout shopList = findViewById(R.id.leaderboardList);

        LinkedList<User> allUsers = DatabaseManager.getInstance().getAllUsersOrderByCoins();
        if(allUsers != null){
            for(User u: allUsers){
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                shopList.addView(row);

                TextView usernameTv = new TextView(this);
                usernameTv.setWidth(630);
                usernameTv.setText(u.getUsername());
                usernameTv.setTextColor(Color.rgb(255,255,255));
                usernameTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

                TextView scoreTv = new TextView(this);
                scoreTv.setText(Integer.toString(u.getCollectedCoinsFromAllLevels()));
                scoreTv.setTextColor(Color.rgb(255,255,255));
                scoreTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

                row.addView(usernameTv);
                row.addView(scoreTv);

            }
        }
    }

    public void backToMainMenu(View v) {
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}
