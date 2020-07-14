package com.example.flyingfish.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyingfish.R;
import com.example.flyingfish.db.FirebaseManager;
import com.example.flyingfish.gameObjects.Level;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainMenuActivity extends AppCompatActivity {

    private static MainMenuActivity instance;
    public static MainMenuActivity getInstance () {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_mainmenu);
//        fillDatabaseWithLevelInformation();
        FirebaseManager.getInstance().initializeListeners();
    }


    private String readFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toString();
    }

    /**
     * only needed when using SQL Database,
     * reads the coins from each level and updates the database
     */
    private void fillDatabaseWithLevelInformation() {

        AssetManager mgr = getResources().getAssets();
        try {
            String[] levels = mgr.list("");
            ObjectMapper om = new ObjectMapper();

            assert levels != null;
            for(int i = 0; i < levels.length; i++){
                if(levels[i].startsWith("level")){
                    String jsonLevel = readFile(getResources().getAssets().open(levels[i]));
                    Level l = om.readValue(jsonLevel, Level.class);
                    int levelNum = Integer.parseInt(levels[i].split("level")[1].split(".json")[0]);
                    int amountCoins = l.getCoins().size();
//                    DataObjectManager.getInstance().updateLevel(levelNum, amountCoins);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void levelMenue(View v){
        startActivity(new Intent(this, LevelMenuActivity.class));
    }

    public void leaderboard(View v){
        startActivity(new Intent(this, LeaderboardActivity.class));
    }

    public void startAcitivity(){
        setContentView(R.layout.activity_mainmenu);
    }

    public void shop(View v){
        startActivity(new Intent(this, ShopActivity.class));
    }

    /**
     * quits the app, does not log the user out so
     * the user does not have to log in each time he starts the app
     * @param v
     */
    public void quit(View v) {
        Intent _intentOBJ= new Intent(Intent.ACTION_MAIN);
        _intentOBJ.addCategory(Intent.CATEGORY_HOME);
        _intentOBJ.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        _intentOBJ.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(_intentOBJ);
    }

    public void backToLogin(View v) {
        FirebaseManager.getInstance().logout();
        startActivity(new Intent(this, LoginActivity.class));
    }

}
