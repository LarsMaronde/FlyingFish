/**
 * Represents the user document in the database
 * each user has a connection to levels and items which are also referenced in this class
 */
package com.example.flyingfish.db.dataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends DataObject {

    private String username;
    private int currentAmountCoins;
    private String password;

    private ArrayList<Item> purchasedItems;
    private ArrayList<Level> solvedLevels;

    private int collectedCoinsFromAllLevels;

    public User() {
        this.purchasedItems = new ArrayList<>();
        this.solvedLevels = new ArrayList<>();
    }

    public User(String username, String password, int currentAmountCoins) {
        this();
        this.username = username;
        this.password = password;
        this.currentAmountCoins = currentAmountCoins;
    }

    public User(String username, int collectedCoinsFromAllLevels) {
        this();
        this.username = username;
        this.collectedCoinsFromAllLevels = collectedCoinsFromAllLevels;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("currentAmountCoins", currentAmountCoins);
        return map;
    }


    public void addLevel(Level l) {
        this.solvedLevels.add(l);
    }

    public void addItem(Item i) {
        this.purchasedItems.add(i);
    }

    public void removeLevel(Level l) {
        this.solvedLevels.remove(l);
    }

    public void removeItem(Item i) {
        this.purchasedItems.remove(i);
    }


    // GETTER SETTER

    public int getCollectedCoinsFromAllLevels() {
        return collectedCoinsFromAllLevels;
    }

    public void setCollectedCoinsFromAllLevels(int collectedCoinsFromAllLevels) {
        this.collectedCoinsFromAllLevels = collectedCoinsFromAllLevels;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCurrentAmountCoins() {
        return currentAmountCoins;
    }

    public void setCurrentAmountCoins(int currentAmountCoins) {
        this.currentAmountCoins = currentAmountCoins;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Item> getItems(){
        return this.purchasedItems;
    }

    public Level getLevelByNumber(int number) {
        for(int i = 0; i < this.solvedLevels.size(); i++){
            if(solvedLevels.get(i).getLevelNumber() == number){
                return solvedLevels.get(i);
            }
        }
        return null;
    }

    public void removeLevelByNumber(int number) {
        Level l = getLevelByNumber(number);
        if(l != null){
            this.solvedLevels.remove(l);
        }
    }

    public List<Level> getLevels() {
        return this.solvedLevels;
    }

    public Item getItemByName(String name) {
        for (int i = 0; i < this.purchasedItems.size(); i++){
            if(purchasedItems.get(i).getName().equals(name)){
                return purchasedItems.get(i);
            }
        }
        return null;
    }

    public void removeItemByName(String name) {
        Item i = getItemByName(name);
        if(i != null){
            this.purchasedItems.remove(i);
        }
    }

    public Item getEquipedItem() {
        for(int i = 0; i < purchasedItems.size(); i++){
            if(purchasedItems.get(i).isEquiped()){
                return purchasedItems.get(i);
            }
        }
        return null;
    }
}
