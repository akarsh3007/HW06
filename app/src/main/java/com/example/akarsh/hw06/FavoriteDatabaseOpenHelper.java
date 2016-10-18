package com.example.akarsh.hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteDatabaseOpenHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "favorites.db";
    private static int DATABASE_VERSION = 2;

    public FavoriteDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        FavoriteCityTable.createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        FavoriteCityTable.upgradeTable(sqLiteDatabase);
    }
}
