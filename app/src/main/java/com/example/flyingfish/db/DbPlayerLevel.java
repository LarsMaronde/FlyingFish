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
    public static final String TABLE_NAME = "playerLevels_table";
    public static final String COL_1 = "LEVEL_NUMBER";
    public static final String COL_2 = "PLAYER_ID";
    public static final String COL_3 = "COLLECTED_COINS";

    public DbPlayerLevel(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (LEVEL_NUMBER INT,  PLAYER_ID INT, COLLECTED_COINS INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int levelNUmber, int playerId, int coins){
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

}
