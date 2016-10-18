package com.example.akarsh.hw06;


import android.database.sqlite.SQLiteDatabase;

public class FavoriteCityTable {
    static final String TABLE_NAME = "favoritecities";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_CITY = "cityname";
    static final String COLUMN_COUNTRY = "country";
    static final String COLUMN_TEMPERATURE = "temperature";
    static final String COLUMN_FAVORITE = "favorite";
    static final String COLUMN_UPDATED = "updatedon";

    public static void createTable(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement,");
        sb.append(COLUMN_CITY + " text not null, ");
        sb.append(COLUMN_COUNTRY + " text not null, ");
        sb.append(COLUMN_TEMPERATURE + " long not null, ");
        sb.append(COLUMN_FAVORITE + " integer DEFAULT 0, ");
        sb.append(COLUMN_UPDATED + " integer DEFAULT 0 ");
        sb.append(");");

        db.execSQL(sb.toString());
    }

    public static void upgradeTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        FavoriteCityTable.createTable(db);
    }
}
