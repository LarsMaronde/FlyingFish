package com.example.flyingfish.dataObject;

public class Level {

    private int number;
    private int collectedCoins;
    private int maxCoinAmount;
    private boolean unlocked;

    public Level(int number, int maxCoinAmount) {
        this.number = number;
        this.maxCoinAmount = maxCoinAmount;
        this.collectedCoins = 0;
        this.unlocked = false;
    }


    public int getPercentageCompleted() {
        return Math.round((float)this.collectedCoins/this.maxCoinAmount*100);
    }


    @Override
    public String toString() {
        return "LEVEL: "+Integer.toString(this.number)+"/"+Integer.toString(this.collectedCoins)+"/"+Integer.toString(this.maxCoinAmount);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

}
