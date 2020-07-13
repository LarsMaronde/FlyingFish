/**
 * This class manages all the data coming from the database
 * and also manages the data from the app to the database
 * it automatically sends notifications to all observers that
 * need realtime updates from the database when changes happen
 */
package com.example.flyingfish.db.dataObject.management;

import com.example.flyingfish.db.FirebaseManager;
import com.example.flyingfish.db.dataObject.Item;
import com.example.flyingfish.db.dataObject.Level;
import com.example.flyingfish.db.dataObject.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataObjectManager {

    public static DataObjectManager manager;

    //stores a list of all users in the database
    private ArrayList<User> users;
    //stores a list of all levels in the database
    private ArrayList<Level> levels;
    //stores a list of all items in the database
    private ArrayList<Item> items;


    //stores all observers that need updates of the data
    private ArrayList<Observer> observers;


    public static DataObjectManager getInstance() {
        if(manager == null){
            manager = new DataObjectManager();
        }
        return manager;
    }

    public DataObjectManager() {

        this.users = new ArrayList<>();
        this.levels = new ArrayList<>();
        this.items = new ArrayList<>();

        this.observers = new ArrayList<>();

        DataObjectManager.manager = this;
    }

    /**
     * notify all observers to all changes
     */
    public void notifyAllChange() {
        notifyItemChange();
        notifyUserChange();
        notifyLevelChange();
    }

    /**
     * notifiys the observers that a change in the user collection occured
     */
    public void notifyUserChange() {
        for(Observer o: observers){
            o.updateUsers(this.users);
        }
    }

    /**
     * notifiys the observers that a change in the items collection occured
     */
    public void notifyItemChange() {
        for(Observer o: observers){
            o.updateItems(this.items);
        }
    }

    /**
     * notifiys the observers that a change in the levels collection occured
     */
    public void notifyLevelChange() {
        for(Observer o: observers){
            o.updateLevel(this.levels);
        }
    }

    /**
     * finds a user by name and returns the user data object
     * @param username
     * @return
     */
    public User getUserByName(String username) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(username)){
                return users.get(i);
            }
        }
        return null;
    }

    /**
     * finds a item by name and returns the item data object
     * @param name
     * @return
     */
    public Item getItemByName(String name) {
        for(int i = 0; i < items.size(); i++) {
            if(items.get(i).getName().equals(name)){
                return items.get(i);
            }
        }
        return null;
    }


    /**************************************
                OBSERVER ADD REMOVE
    **************************************/

    public void addObserver(Observer o){
        this.observers.add(o);
    }

    public void removeObserver(Object o) {
        this.observers.remove(o);
    }


    /**************************************
                LIST ADD REMOVE
    **************************************/

    public void addUser(User u) {
        this.users.add(u);
    }

    public void removeUser(User u){
        this.users.remove(u);
    }

    public void addLevel(Level l) {
        this.levels.add(l);
    }

    public void removeLevel(Level l){
        this.levels.remove(l);
    }

    public void addItem(Item i) {
        this.items.add(i);
    }

    public void removeItem(Item i){
        this.items.remove(i);
    }





    /**************************************
                GET FIND REMOVE
    **************************************/


    /**
     * return the currently selected skin of a player and returns the name of it
     * @param username
     * @return
     */
    public String getSelectedFishSkin(String username) {
        User u = getUserByName(username);
        if(u != null){
            for(int i = 0; i < u.getItems().size(); i++){
                if(u.getItems().get(i).isEquiped()){
                    return u.getItems().get(i).getName();
                }
            }
        }
        return null;
    }

    /**
     * return the current coin balance of the given user
     * @param username
     * @return
     */
    public int getUserCurrentCoins(String username) {
        User u = this.getUserByName(username);
        if(u != null){
            return u.getCurrentAmountCoins();
        }
        return -1;
    }

    public List<Item> getItemList() {
        return this.items;
    }

    public void removeItemByName(String name) {
        Item i = getItemByName(name);
        if(i != null){
            this.items.remove(i);
        }
    }

    public void removeUserByName(String username) {
        User u = getUserByName(username);
        if(u != null){
            this.users.remove(u);
        }
    }

    public List<User> getUserList() {
        return this.users;
    }


    public List<Level> getLevelsOfUser(String username) {
        User u = getUserByName(username);
        if(u != null) {
            return u.getLevels();
        }
        return null;
    }


    public List<Item> getItemListOfUser(String username) {
        User u = getUserByName(username);
        if(u != null){
            return u.getItems();
        }
        return null;
    }


    public Level getLevelByNumber(int levelNumber) {
        for(int i = 0; i < levels.size(); i++){
            if(levels.get(i).getLevelNumber() == levelNumber){
                return levels.get(i);
            }
        }
        return null;
    }

    public List<Level> getAllLevels() {
        return this.levels;
    }



    /**************************************
              DATABASE QUERY UPDATES
    **************************************/

    /**
     * buys the given item from the given user and equips it directly
     * if successful, all observers are notified
     * if the user does not have enough coins to buy it this method does nothing
     * @param username of the user that buys the item
     * @param itemname the name of the item the user wants to buy
     */
    public void buyItemAndEquip(String username, String itemname) {

        User user = getUserByName(username);
        Item item = getItemByName(itemname);
        Item target = user.getItemByName(itemname);

        if(user != null && item != null) {
            int itemPrice = item.getPrice();
            int userCoins = user.getCurrentAmountCoins();
            int total = userCoins-itemPrice;

            if(total >= 0 && target == null) {

                Item equiped = user.getEquipedItem();
                if(equiped != null) {
                    equiped.setEquiped(false);
                    equiped.setUsername(username);
                    FirebaseManager.getInstance().updateDocument("UserItems", equiped.getId(), equiped);
                }

                Item newUserItem = new Item(itemname, true);
                user.addItem(newUserItem);
                newUserItem.setUsername(username);
                FirebaseManager.getInstance().createDocument("UserItems", newUserItem);

                user.setCurrentAmountCoins(total);
                FirebaseManager.getInstance().updateDocument("Users", user.getId(), user);
                notifyUserChange();
                notifyItemChange();
            } else if(target != null) {
                Item equiped = user.getEquipedItem();
                equiped.setEquiped(false);
                equiped.setUsername(username);
                FirebaseManager.getInstance().updateDocument("UserItems", equiped.getId(), equiped);

                target.setEquiped(true);
                target.setUsername(username);
                FirebaseManager.getInstance().updateDocument("UserItems", target.getId(), target);
                notifyUserChange();
                notifyItemChange();
            }
        }
    }

    /**
     * adds the given coin amount to the balance of the given user
     * all observers are then notified
     * @param username
     * @param collectedCoins
     */
    public void addCoins(String username, int collectedCoins) {
        User u = getUserByName(username);
        if(u != null){
            u.setCurrentAmountCoins(u.getCurrentAmountCoins()+collectedCoins);
            FirebaseManager.getInstance().updateDocument("Users", u.getId(), u);
            notifyAllChange();
        }
    }

    /**
     * updates the given level by number from the user with the given coins
     * all observers are then notified
     * @param currentLevelNumber
     * @param username
     * @param collectedCoins
     */
    public void updatePlayerLevel(int currentLevelNumber, String username, int collectedCoins) {
        User user = getUserByName(username);
        Level l = user.getLevelByNumber(currentLevelNumber);
        if(l == null) {
            Level sourceLevel = getLevelByNumber(currentLevelNumber);
            Level userLevel = new Level(currentLevelNumber, sourceLevel.getMaxCoinAmount(), collectedCoins, true);
            user.addLevel(userLevel);
            userLevel.setUsername(username);
            userLevel.setUserLevel(true);
            FirebaseManager.getInstance().createDocument("UserLevels", userLevel);
            this.notifyAllChange();
        }else {
            if(collectedCoins > l.getCollectedCoins()){
                l.setCollectedCoins(collectedCoins);
                l.setUserLevel(true);
                l.setUsername(username);
                FirebaseManager.getInstance().updateDocument("UserLevels", l.getId(), l);
                this.notifyAllChange();
            }
        }
    }

    /**
     * unlocks the next level of for the given user
     * all observers are then notified
     * @param username
     * @param currentLevelNumber
     */
    public void unlockNextLevel(String username, int currentLevelNumber) {
        User user = getUserByName(username);
        if(user != null){
            Level nextLevel = user.getLevelByNumber(currentLevelNumber+1);
            Level reference = getLevelByNumber(currentLevelNumber+1);
            if(nextLevel == null && reference != null){
                nextLevel = new Level(currentLevelNumber+1, reference.getMaxCoinAmount(), 0, true);
                user.addLevel(nextLevel);
                nextLevel.setUserLevel(true);
                nextLevel.setUsername(username);
                FirebaseManager.getInstance().createDocument("UserLevels", nextLevel);
                this.notifyAllChange();
            }
        }
    }



    /**************************************
                UTILITY METHODS
    **************************************/

    /**
     * sorts the hashmap descending
     * @param hm the hasmap to be sorted
     * @return a sorted hashmap
     */
    public static HashMap<String, Integer> sortByValueDesc(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    /**
     * sorts the given list
     * @param list the list to sort with items or levels
     * @param desc sort descending or ascending
     */
    public static void bubbleSort(List list, boolean desc) {
        int n = list.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {

                int curVal = 0;
                int nextVal = 0;

                if(list.get(j) instanceof Item){
                    curVal = ((Item) list.get(j)).getPrice();
                    nextVal = ((Item) list.get(j+1)).getPrice();
                }else if(list.get(j) instanceof Level) {
                    curVal = ((Level) list.get(j)).getLevelNumber();
                    nextVal = ((Level) list.get(j+1)).getLevelNumber();
                }

                if(desc){
                    if (curVal > nextVal) {
                        Object temp = list.get(j);
                        list.set(j, list.get(j+1));
                        list.set(j+1, temp);
                    }
                }else {
                    if (curVal < nextVal) {
                        Object temp = list.get(j);
                        list.set(j, list.get(j+1));
                        list.set(j+1, temp);
                    }
                }
            }
        }
    }

}

