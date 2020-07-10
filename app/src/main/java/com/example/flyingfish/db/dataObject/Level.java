package com.example.flyingfish.db.dataObject;

import java.util.HashMap;
import java.util.Map;

public class Level extends DataObject {

    private int levelNumber;
    private int collectedCoins;
    private int maxCoinAmount;
    private boolean unlocked;

    private boolean userLevel;
    private String username;

    public Level() {
        //empty
    }

    public Level(int levelNumber, int maxCoinAmount, int collectedCoins){
        this.levelNumber = levelNumber;
        this.maxCoinAmount = maxCoinAmount;
        this.collectedCoins = collectedCoins;
    }

    public Level(int levelNumber, int maxCoinAmount, int collectedCoins, boolean unlocked){
        this.levelNumber = levelNumber;
        this.maxCoinAmount = maxCoinAmount;
        this.collectedCoins = collectedCoins;
        this.unlocked = true;
    }

    public Level(int levelNumber, int maxCoinAmount) {
        this.levelNumber = levelNumber;
        this.maxCoinAmount = maxCoinAmount;
        this.collectedCoins = 0;
        this.unlocked = false;
    }

    public Level(int levelNumber, boolean unlocked, int collectedCoins) {
        this.levelNumber = levelNumber;
        this.unlocked = unlocked;
        this.collectedCoins = collectedCoins;
    }


    public int getPercentageCompleted() {
        return Math.round((float)this.collectedCoins/this.maxCoinAmount*100);
    }


    @Override
    public String toString() {
        return "LEVEL: "+ this.levelNumber +"/"+ this.collectedCoins +"/"+ this.maxCoinAmount;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public void setCollectedCoins(int collectedCoins) {
        this.collectedCoins = collectedCoins;
    }

    public int getMaxCoinAmount() {
        return maxCoinAmount;
    }

    public void setMaxCoinAmount(int maxCoinAmount) {
        this.maxCoinAmount = maxCoinAmount;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isUserLevel() {
        return userLevel;
    }

    public void setUserLevel(boolean userLevel) {
        this.userLevel = userLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Object> toMap() {
        if(userLevel){
            Map<String, Object> map = new HashMap<>();
            map.put("collectedCoins", collectedCoins);
            map.put("levelNumber", levelNumber);
            map.put("username", username);
            return map;
        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("number", levelNumber);
            map.put("maxCoinAmount", maxCoinAmount);
            return map;
        }
    }

}
