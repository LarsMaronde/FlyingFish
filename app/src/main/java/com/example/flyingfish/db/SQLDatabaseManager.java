/**
 * this class was used for testing purposes as a database to store user data
 * in production, firestore is used as database
 */
package com.example.flyingfish.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.flyingfish.db.dataObject.Item;
import com.example.flyingfish.db.dataObject.Level;
import com.example.flyingfish.db.dataObject.User;

import java.util.LinkedList;

public class SQLDatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FlyingFish.db";

    private static final String u_Users = "Users";
    private static final String u_username = "username";
    private static final String u_password = "password";
    private static final String u_currentAmountCoins = "currentAmountCoins";

    private static final String ul_UserLevels = "UserLevels";
    private static final String ul_levelNumber = "levelNumber";
    private static final String ul_username = "username";
    private static final String ul_collectedCoins = "collectedCoins";

    private static final String l_Levels = "Levels";
    private static final String l_levelNumber = "levelNumber";
    private static final String l_maxCoinAmount = "maxCoinAmount";

    private static final String i_Items = "Items";
    private static final String i_itemName = "itemName";
    private static final String i_price = "price";

    private static final String ui_UserItems = "UserItems";
    private static final String ui_itemName = "itemName";
    private static final String ui_username = "username";
    private static final String ui_equiped = "equiped";

    private static SQLDatabaseManager dbManger;

    public SQLDatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 23);
        dbManger = this;
    }

    public static SQLDatabaseManager getInstance() {
        if(dbManger != null){
            return dbManger;
        }
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + u_Users + " (" +
                u_username + " TEXT PRIMARY KEY ," +
                u_password + " TEXT ," +
                u_currentAmountCoins + " INT "+ ")" );

        db.execSQL("create table " + l_Levels + " (" +
            l_levelNumber + " INT PRIMARY KEY, " +
            l_maxCoinAmount + " INT " + ")" );

        db.execSQL("create table " + i_Items + " (" +
                i_itemName +" INT PRIMARY KEY, " +
            i_price + " INT " + ")" );

        db.execSQL("create table " + ul_UserLevels + " (" +
                ul_levelNumber + " INT , " +
                ul_username + " TEXT , " +
                ul_collectedCoins + " INT, " +
                "PRIMARY KEY (" + ul_levelNumber + ", " + ul_username + "));" );

        db.execSQL("create table " + ui_UserItems + " (" +
            ui_itemName + " TEXT , " +
            ui_username + " TEXT , " +
            ui_equiped + " BOOL , " +
            "PRIMARY KEY (" + ui_itemName + ", " + ui_username +"));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + u_Users);
        db.execSQL("DROP TABLE IF EXISTS " + ul_UserLevels);
        db.execSQL("DROP TABLE IF EXISTS " + l_Levels);
        db.execSQL("DROP TABLE IF EXISTS " + i_Items);
        db.execSQL("DROP TABLE IF EXISTS " + ui_UserItems);

        onCreate(db);
    }

    public void insertData() {

        SQLiteDatabase db = this.getWritableDatabase();

        String userQuery = "INSERT INTO " + u_Users + "(" + u_username + "," + u_password + "," + u_currentAmountCoins+") VALUES ( ?, ?, ?)";
        db.execSQL(userQuery, new String[]{"testuser", "ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae", "120"}); //pw = test123


//        String levelInsertQuery = "INSERT INTO " + l_Levels + "(" + l_levelNumber + "," + l_maxCoinAmount + ") VALUES ( ?, ?)";
//        db.execSQL(levelInsertQuery, new String[]{"1", "42"});
//        db.execSQL(levelInsertQuery, new String[]{"2", "45"});
//        db.execSQL(levelInsertQuery, new String[]{"3", "50"});
//        db.execSQL(levelInsertQuery, new String[]{"4", "60"});

        String itemInsertQuery = "INSERT INTO " + i_Items + "(" + i_itemName + "," + i_price + ") VALUES ( ?, ?)";
        db.execSQL(itemInsertQuery, new String[]{"Standard Fisch", "0"});
        db.execSQL(itemInsertQuery, new String[]{"Roter Fisch", "50"});
        db.execSQL(itemInsertQuery, new String[]{"Blauer Fisch", "100"});
        db.execSQL(itemInsertQuery, new String[]{"Lila Fisch", "200"});
        db.execSQL(itemInsertQuery, new String[]{"Gruener Fisch", "500"});


        String userItemInsertQuery = "INSERT INTO " + ui_UserItems + "(" + i_itemName + "," + ui_username + "," + ui_equiped + ") VALUES ( ?, ?, ?)";
        db.execSQL(userItemInsertQuery, new String[]{"Standard Fisch", "testuser", "true"});
        db.execSQL(userItemInsertQuery, new String[]{"Blauer Fisch", "testuser", "false"});

        String userLevelQuery = "INSERT INTO " + ul_UserLevels + "(" + ul_username + "," + ul_levelNumber + "," + ul_collectedCoins + ") VALUES ( ?, ?, ?)";
        db.execSQL(userLevelQuery, new String[]{"testuser", "1", "15"});
        db.execSQL(userLevelQuery, new String[]{"testuser", "2", "22"});
        db.execSQL(userLevelQuery, new String[]{"testuser", "3", "0"});

    }

    public void printAllTables() {
        SQLiteDatabase db = this.getReadableDatabase();
        this.printAllTables(db);
    }

    public void printAllTables(SQLiteDatabase db) {
        System.out.println(tableToString(db, u_Users));
        System.out.println("_____________________________");
        System.out.println(tableToString(db, l_Levels));
        System.out.println("_____________________________");
        System.out.println(tableToString(db, i_Items));
        System.out.println("_____________________________");
        System.out.println(tableToString(db, ul_UserLevels));
        System.out.println("_____________________________");
        System.out.println(tableToString(db, ui_UserItems));
        System.out.println("_____________________________");
    }

    public String tableToString(SQLiteDatabase db, String tableName) {
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        tableString += cursorToString(allRows);
        allRows.close();
        return tableString;
    }

    public String cursorToString(Cursor cursor){
        String cursorString = "";
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            for (String name: columnNames)
                cursorString += String.format("[%s]\t\t", name);
            cursorString += "\n";
            do {
                for (String name: columnNames) {
                    cursorString += String.format("[%s]\t\t",
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                cursorString += "\n";
            } while (cursor.moveToNext());
        }
        return cursorString;
    }

    public LinkedList<Item> getAllItems(String username) {
        LinkedList<Item> items = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery("SELECT * FROM " + i_Items, null);
        if (cursor.moveToFirst()) {
            do {
               items.add(new Item(cursor.getString(cursor.getColumnIndex(i_itemName)),
                        cursor.getInt(cursor.getColumnIndex(i_price))));
            } while (cursor.moveToNext());
        }
        cursor.close();

        for(Item i: items) {
            Cursor cursor2  = db.rawQuery("SELECT * FROM " + ui_UserItems +
                    " WHERE "+ ui_username +" = \"" + username +"\" AND "+
                    ui_itemName + " = " + "\""+i.getName()+"\"", null);
            if(cursor2.moveToFirst()) {
                i.setBought(true);
                if(cursor2.getString(cursor2.getColumnIndex(ui_equiped)).equals("true")) {
                    i.setEquiped(true);
                }
            }
            cursor2.close();
        }
        return items;
    }

    public int getCurrentUserCoins(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery("SELECT "+u_currentAmountCoins+" FROM " + u_Users +" WHERE "+u_username +" = \"" + username +"\"", null);
        if(cursor.moveToFirst()){
            int i = cursor.getInt(cursor.getColumnIndex(u_currentAmountCoins));
            cursor.close();
            return i;
        }
        cursor.close();
        return -1;
    }

    public Item getUserItem(String username, String itemName) {
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor  = db.rawQuery("SELECT * FROM " + ui_UserItems +
                " WHERE "+ ui_username +" = \"" + username +"\" AND "+
                ui_itemName + " = " + "\""+itemName+"\"", null);
        if(cursor.moveToFirst()) {
            //name price equiped bought
            Item i =  new Item(cursor.getString(cursor.getColumnIndex(ui_itemName)),
                    getPriceOfItem(itemName),
                    Boolean.getBoolean(cursor.getString(cursor.getColumnIndex(ui_equiped))),
                    true);
            cursor.close();
            return i;
        }
        cursor.close();
        return null;
    }


    public boolean buyItemAndEquip(String username, String itemName) {

        SQLiteDatabase db= this.getWritableDatabase();

        //check if item already bought
        Item userItem = getUserItem(username, itemName);
        if(userItem != null) {
            db.execSQL("UPDATE "+ui_UserItems+" SET "+ui_equiped+"=\"false\" WHERE "+ui_itemName + "=\""+ getEquipedItemName(username)+"\"");
            db.execSQL("UPDATE "+ui_UserItems+" SET "+ui_equiped+"=\"true\" WHERE "+ui_itemName + "=\""+itemName+"\"");
            printAllTables();
            return true;
        }

        int currentCoins = getCurrentUserCoins(username);
        int itemPrice = getPriceOfItem(itemName);
        int newBalance = currentCoins - itemPrice;

        if(itemPrice != -1 && newBalance >= 0) {
            db.execSQL("UPDATE "+u_Users+" SET "+u_currentAmountCoins+"=\""+ newBalance +"\" WHERE "+u_username + "=\""+username+"\"");

            //set the equiped item to false
            db.execSQL("UPDATE "+ui_UserItems+" SET "+ui_equiped+"=\"false\" WHERE "+ui_itemName + "=\""+ getEquipedItemName(username)+"\"");

            //check if the new item is already in the table
            Item userItem2 = getUserItem(username, itemName);
            if(userItem2 != null) {
                //if yes update
                db.execSQL("UPDATE "+ui_UserItems+" SET "+ui_equiped+"=\"true\" WHERE "+ui_itemName + "=\""+itemName+"\"");
            }else{
                //if not insert it
                String userItemInsertQuery = "INSERT INTO " + ui_UserItems + "(" + i_itemName + "," + ui_username + "," + ui_equiped + ") VALUES ( ?, ?, ?)";
                db.execSQL(userItemInsertQuery, new String[]{itemName, username, "true"});
            }
            return true;
        }
        return false;
    }

    private String getEquipedItemName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery("SELECT "+ui_itemName+
                " FROM " + ui_UserItems +
                " WHERE "+ui_username +" = \"" + username +"\""+
                " AND " + ui_equiped + " = " + "\"true\"", null);
        if(cursor.moveToFirst()){
            String s = cursor.getString(cursor.getColumnIndex(ui_itemName));
            cursor.close();
            return s;
        }
        cursor.close();
        return null;
    }

    private int getPriceOfItem(String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery("SELECT "+i_price+" FROM " + i_Items +" WHERE "+i_itemName +" = \"" + itemName +"\"", null);
        if(cursor.moveToFirst()){
            int i = cursor.getInt(cursor.getColumnIndex(i_price));
            cursor.close();
            return i;
        }
        cursor.close();
        return -1;
    }

    public LinkedList<Level> getAllLevelsFromUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        LinkedList<Level> levels = new LinkedList<Level>();

        //store all the levels
        Cursor allLevelsCursor = db.rawQuery("SELECT * FROM "+l_Levels, null);
        if(allLevelsCursor.moveToFirst()) {
            do {
                levels.add(new Level(allLevelsCursor.getInt(allLevelsCursor.getColumnIndex(l_levelNumber)),
                        allLevelsCursor.getInt(allLevelsCursor.getColumnIndex(l_maxCoinAmount))));
            } while (allLevelsCursor.moveToNext());
        }
        allLevelsCursor.close();

        //check the levels the user did
        Cursor userLevelCursor = db.rawQuery("SELECT * FROM " + l_Levels + " JOIN " + ul_UserLevels +" ON "+
                l_Levels+"."+l_levelNumber+"="+ul_UserLevels+"."+ul_levelNumber+
                " WHERE "+ul_username +"=\""+username+"\"",null);

        if(userLevelCursor.moveToFirst()) {
            do {
                Level l = getLevel(levels, userLevelCursor.getInt(userLevelCursor.getColumnIndex(l_levelNumber)));
                if(l != null) {
                    l.setUnlocked(true);
                    l.setCollectedCoins(userLevelCursor.getInt(userLevelCursor.getColumnIndex(ul_collectedCoins)));
                }
            } while (userLevelCursor.moveToNext());
        }
        userLevelCursor.close();
        return levels;
    }

    private Level getLevel(LinkedList<Level> levels, int levelNumber) {
        for(Level l: levels) {
            if(l.getLevelNumber() == levelNumber){
                return l;
            }
        }
        return null;
    }

    public void addCoins(String username, int collectedCoins) {
        SQLiteDatabase db = this.getWritableDatabase();
        int newBalance = getCurrentUserCoins(username) + collectedCoins;
        db.execSQL("UPDATE "+u_Users+" SET "+u_currentAmountCoins+"=\""+ newBalance +
                "\" WHERE "+u_username + "=\"" + username + "\"");
    }

    public void updatePlayerLevel(int levelnumber, String username, int collectedCoins) {
        SQLiteDatabase db = this.getWritableDatabase();
        int currentBest = getCoinsOfLevel(levelnumber, username);
        if(currentBest != -1 && currentBest < collectedCoins) {
            db.execSQL("UPDATE " + ul_UserLevels + " SET " + ul_collectedCoins + "=\""+ collectedCoins +
                "\" WHERE " + ul_username + "=\"" + username + "\"" +
                " AND " + ul_levelNumber + "=\"" + levelnumber + "\"");
        }
    }

    private int getCoinsOfLevel(int levelnumber, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ul_collectedCoins +
                " FROM " + ul_UserLevels +
                " WHERE " + ul_username + "=\"" +username+ "\"" +
                " AND "  + ul_levelNumber + "=\"" +levelnumber+ "\"",null);

        if(cursor.moveToFirst()) {
            int i = cursor.getInt(cursor.getColumnIndex(ul_collectedCoins));
            cursor.close();
            return i;
        }
        cursor.close();
        return -1;
    }

    public void unlockNextLevel(String username, int levelnumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        //check if next level exists
        Cursor cursor = db.rawQuery("SELECT " + l_levelNumber +
                " FROM " + l_Levels +
                " WHERE " + l_levelNumber + "=\"" + (levelnumber+1) + "\"", null);

        //check if level is already unlocked
        Cursor cursor2 = db.rawQuery("SELECT " + ul_username +
                " FROM " + ul_UserLevels +
                " WHERE " + ul_username + "=\"" + username + "\"" +
                " AND " + ul_levelNumber + "=\"" + (levelnumber+1) + "\"", null);

        if(cursor.moveToFirst() && !cursor2.moveToFirst()) {
            //if exists, make new entry in playerlevels
            String userLevelQuery = "INSERT INTO " + ul_UserLevels + "(" + ul_username + "," + ul_levelNumber + "," + ul_collectedCoins + ") VALUES ( ?, ?, ?)";
            db.execSQL(userLevelQuery, new String[]{username, Integer.toString(levelnumber+1), "0"});
        }
        cursor.close();
        cursor2.close();
    }

    public String getSelectedFishSkin(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT "+ ui_itemName + " FROM "+ ui_UserItems +
                " WHERE " + ui_username + "=\"" + username +"\""+
                " AND " + ui_equiped+ "=\"true\"", null);

        if(cursor.moveToFirst()){
            String s = cursor.getString(cursor.getColumnIndex(ui_itemName));
            cursor.close();
            return s;
        }
        cursor.close();
        return null;
    }

    public LinkedList<User> getAllUsersOrderByCoins() {
        SQLiteDatabase db = this.getReadableDatabase();
        LinkedList<User> users = new LinkedList<User>();
        Cursor cursor = db.rawQuery("SELECT SUM("+ul_collectedCoins+") as "+ul_collectedCoins
                + " , "+ul_username+" FROM "+ul_UserLevels +
                " GROUP BY "+ul_username+" ORDER BY "+ul_collectedCoins +" DESC", null);
        if(cursor.moveToFirst()){
            do{
                String username = cursor.getString(cursor.getColumnIndex(ul_username));
                int coinCount = cursor.getInt(cursor.getColumnIndex(ul_collectedCoins));
                users.add(new User(username, coinCount));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public boolean checkCredentials(String username, String passwordHash) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + u_Users +
                " WHERE " + u_username +"=\""+username+"\""+
                " AND " + u_password  +"=\""+passwordHash+"\"", null);
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public String getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + u_username + " FROM " + u_Users +
                " WHERE " + u_username +"=\""+username+"\"", null);
        if(cursor.moveToFirst()){
            String s = cursor.getString(cursor.getColumnIndex(u_username));
            cursor.close();
            return s;
        }
        cursor.close();
        return null;

    }

    public void createUser(String username, String passwordHash) {
        SQLiteDatabase db = this.getWritableDatabase();
        String userQuery = "INSERT INTO " + u_Users + "(" + u_username + "," + u_password + "," + u_currentAmountCoins+") VALUES ( ?, ?, ?)";
        db.execSQL(userQuery, new String[]{username, passwordHash, "0"});

        String userLevelQuery = "INSERT INTO " + ul_UserLevels + "(" + ul_username + "," + ul_levelNumber + "," + ul_collectedCoins + ") VALUES ( ?, ?, ?)";
        db.execSQL(userLevelQuery, new String[]{username, "1", "0"});

        String userItemInsertQuery = "INSERT INTO " + ui_UserItems + "(" + i_itemName + "," + ui_username + "," + ui_equiped + ") VALUES ( ?, ?, ?)";
        db.execSQL(userItemInsertQuery, new String[]{"Standard Fisch", username, "true"});
    }

    public void insertOrUpdateLevel(int levelNum, int amountCoins) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL( "INSERT OR REPLACE INTO " + l_Levels + "(" + l_levelNumber + " , " + l_maxCoinAmount + ") VALUES ( "+levelNum+", "+amountCoins+")");
    }


}
