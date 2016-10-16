package com.example.akarsh.hw06;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class FavoriteCityTableDAO {

    private SQLiteDatabase db;

    public FavoriteCityTableDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long addCity (FavoriteCity favoriteCity){
        ContentValues values = new ContentValues();
        values.put(FavoriteCityTable.COLUMN_CITY, favoriteCity.getCity());
        values.put(FavoriteCityTable.COLUMN_COUNTRY, favoriteCity.getCountry());
        values.put(FavoriteCityTable.COLUMN_TEMPERATURE, favoriteCity.getTemperature());

        return db.insert(FavoriteCityTable.TABLE_NAME,null,values);
    }

    public void updateCity (FavoriteCity favoriteCity){
        //TODO Implement Function

    }

    public FavoriteCity getFavoriteCity(int id){
        //TODO Implement Function
        return null;

    }

    public List<FavoriteCity> getAll(){
        //TODO Implement Function
        return null;
    }

}
