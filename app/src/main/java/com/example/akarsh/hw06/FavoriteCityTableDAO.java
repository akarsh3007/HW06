package com.example.akarsh.hw06;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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
        List<FavoriteCity> cityList = null;

        Cursor c =  db.query(FavoriteCityTable.TABLE_NAME,new String[] {FavoriteCityTable.COLUMN_CITY,FavoriteCityTable.COLUMN_COUNTRY,FavoriteCityTable.COLUMN_TEMPERATURE,FavoriteCityTable.COLUMN_FAVORITE},null,null,null,null,null);

        if (c != null && c.moveToFirst()){
            cityList = new ArrayList<FavoriteCity>();
            do {
                cityList.add(buildFavoriteFromCursor(c));
            } while (c.moveToNext());

            if(!c.isClosed()){
                c.close();
            }

        }
        return cityList;
    }

    private FavoriteCity buildFavoriteFromCursor(Cursor c) {
        FavoriteCity favoriteCity = new FavoriteCity();

        favoriteCity.setCity(c.getString(0));
        favoriteCity.setTemperature(c.getDouble(2));
        favoriteCity.setCountry(c.getString(1));
        if (c.getInt(3) > 1 ) {
            favoriteCity.setFavorite(true);
        } else {
            favoriteCity.setFavorite(false);
        }

        return favoriteCity;

    }

}
