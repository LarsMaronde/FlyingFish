package com.example.flyingfish.dataObject;

public class User {

    private String username;
    private int collectedCoinsFromAllLevels;

    public User(String username){
        this.username = username;
    }

    public User(String username, int collectedCoinsFromAllLevels) {
        this.username = username;
        this.collectedCoinsFromAllLevels = collectedCoinsFromAllLevels;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCollectedCoinsFromAllLevels() {
        return collectedCoinsFromAllLevels;
    }

    public void setCollectedCoinsFromAllLevels(int collectedCoinsFromAllLevels) {
        this.collectedCoinsFromAllLevels = collectedCoinsFromAllLevels;
    }
}
