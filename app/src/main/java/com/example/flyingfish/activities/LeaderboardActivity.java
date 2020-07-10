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
import com.example.flyingfish.db.dataObject.Item;
import com.example.flyingfish.db.dataObject.Level;
import com.example.flyingfish.db.dataObject.User;
import com.example.flyingfish.db.dataObject.management.DataObjectManager;
import com.example.flyingfish.db.dataObject.management.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        //listen for changes
        DataObjectManager.getInstance().addObserver(this);

        //initialize with the current values the manager has
        fillLeaderboard(DataObjectManager.getInstance().getUserList());
    }

    private void fillLeaderboard(List<User> userList) {
        LinearLayout leaderboardList = findViewById(R.id.leaderboardList);
        leaderboardList.removeAllViews();

        HashMap<String, Integer> list = createLeaderBoard(userList);

        for(Map.Entry<String, Integer> entry: list.entrySet()) {
            String username = entry.getKey();
            int counter = entry.getValue();

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            leaderboardList.addView(row);

            TextView usernameTv = new TextView(this);
            usernameTv.setWidth(630);
            usernameTv.setText(username);
            usernameTv.setTextColor(Color.rgb(255,255,255));
            usernameTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

            TextView scoreTv = new TextView(this);
            scoreTv.setText(Integer.toString(counter));
            scoreTv.setTextColor(Color.rgb(255,255,255));
            scoreTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

            row.addView(usernameTv);
            row.addView(scoreTv);
        }

    }

    private HashMap<String, Integer> createLeaderBoard(List<User> users) {
        HashMap<String, Integer> leaderB = new HashMap<>();
        for(User u: users) {
            int counter = 0;
            for(Level l: u.getLevels()) {
                counter += l.getCollectedCoins();
            }
            leaderB.put(u.getUsername(), counter);
        }
        return DataObjectManager.sortByValueDesc(leaderB);
    }

    public void backToMainMenu(View v) {
        DataObjectManager.getInstance().removeObserver(this);
        startActivity(new Intent(this, MainMenuActivity.class));
    }


    @Override
    public void updateUsers(ArrayList<User> users) {
        fillLeaderboard(users);
    }

    @Override
    public void updateItems(ArrayList<Item> items) {

    }

    @Override
    public void updateLevel(ArrayList<Level> levels) {

    }
}
