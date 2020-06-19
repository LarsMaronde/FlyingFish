package com.example.flyingfish.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DbPlayerLevel extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PlayerLevels.db";
    public static final String TABLE_NAME = "PlayerLevels_table";
    public static final String COL_1 = "LEVEL_NUMBER";
    public static final String COL_2 = "PLAYER_ID";
    public static final String COL_3 = "COLLECTED_COINS";

    public static final String TABLE_PLAYER = "Player_table";
    public static final String COLP_1 = "PLAYER_ID";
    public static final String COLP_2 = "USER_NAME";
    public static final String COLP_3 = "CURRENT_AMOUNT_COINS";

    public static final String TABLE_LEVEL = "Level_table";
    public static final String COLL_1 = "LEVEL_NUMBER";
    public static final String COLL_2 = "MAX_COIN_AMOUNT";

    public static final String TABLE_ITEMS = "Items_table";
    public static final String COLI_1 = "ITEM_ID";
    public static final String COLI_2 = "PLAYER_ID";
    public static final String COLI_3 = "PURCHASED";


    public DbPlayerLevel(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (LEVEL_NUMBER INT PRIMARY KEY AUTOINCREMENT NOT NULL,  PLAYER_ID INT, COLLECTED_COINS INT)");
        db.execSQL("create table " + TABLE_PLAYER + " (PLAYER_ID PRIMARY KEY AUTOINCREMENT NOT NULL,  USER_NAME STRING, CURRENT_AMOUNT_COINS INT)");
        db.execSQL("create table " + TABLE_LEVEL + " (LEVEL_NUMBER INT, MAX_COIN_AMOUNT INT)");
        db.execSQL("create table " + TABLE_ITEMS + " (ITEM_ID INT PRIMARY KEY AUTOINCREMENT NOT NULL,  PLAYER_ID INT, PURCHASED String)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPlayerLevelesData(int levelNUmber, int playerId, int coins){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, levelNUmber);
        contentValues.put(COL_2, playerId);
        contentValues.put(COL_3, coins);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            db.close();
            return true;
        }
    }

    public int getMaxLevelNumber() {
        int level = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor cur = db.query(TABLE_NAME, new String [] {"MAX(LEVEL_NUMBER)"}, null, null, null, null, null);
        if (cur != null){
            cur.moveToFirst();
            level = cur.getInt(0);
            cur.close();
        }
        return level;
    }

    public boolean insertPlayerLevelesData(String userName, int currentAmountCoins){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLP_1, userName);
        contentValues.put(COLP_2, currentAmountCoins);
        long result = db.insert(TABLE_PLAYER, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            db.close();
            return true;
        }
    }

    public boolean insertLeveleData(String levelNumber, int maxAmountCoins){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLL_1, levelNumber);
        contentValues.put(COLL_2, maxAmountCoins);
        long result = db.insert(TABLE_LEVEL, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            db.close();
            return true;
        }
    }

    public boolean insertPlayerData(String username, int currentAmountCoins){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLL_1, username);
        contentValues.put(COLL_2, currentAmountCoins);
        long result = db.insert(TABLE_LEVEL, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            db.close();
            return true;
        }
    }

    public boolean insertItemsData(String playerId, int purchased){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLL_1, playerId);
        contentValues.put(COLL_2, purchased);
        long result = db.insert(TABLE_ITEMS, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            db.close();
            return true;
        }
    }

}
