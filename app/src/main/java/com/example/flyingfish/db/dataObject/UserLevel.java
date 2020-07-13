/**
 * Represents the UserLevel document in the database
 */
package com.example.flyingfish.db.dataObject;

import androidx.annotation.NonNull;

import java.util.Map;

public class UserLevel extends DataObject {

    private int collectedCoins;
    private int levelNumber;
    private String username;

    public UserLevel() {
        //empty
    }

    @NonNull
    @Override
    public String toString() {
        return "USER LEVEL: "+ this.levelNumber +"/"+ this.collectedCoins +"/"+ this.username;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public void setCollectedCoins(int collectedCoins) {
        this.collectedCoins = collectedCoins;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = this.levelNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void updateDatabase() {

    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }
}
