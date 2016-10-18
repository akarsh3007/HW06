package com.example.akarsh.hw06;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
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
        values.put(FavoriteCityTable.COLUMN_UPDATED, favoriteCity.getUpdated().getTime());

        return db.insert(FavoriteCityTable.TABLE_NAME,null,values);
    }

    public boolean updateCity (FavoriteCity favoriteCity){
        ContentValues values = new ContentValues();
        values.put(FavoriteCityTable.COLUMN_FAVORITE,favoriteCity.getFavorite());
        values.put(FavoriteCityTable.COLUMN_UPDATED,favoriteCity.getUpdated().getTime());

        StringBuilder sb = new StringBuilder();
        sb.append(FavoriteCityTable.COLUMN_COUNTRY + " = ? ");
        sb.append(" AND ");
        sb.append(FavoriteCityTable.COLUMN_CITY + " = ? ");

        return (db.update(FavoriteCityTable.TABLE_NAME,values,sb.toString(),new String[]{favoriteCity.getCountry(),favoriteCity.getCity()}) > 0);

    }

    public FavoriteCity getFavoriteCity(int id){
        //TODO Implement Function
        return null;

    }

    public List<FavoriteCity> getAll(){
        List<FavoriteCity> cityList = new ArrayList<>();

        Cursor c =  db.query(FavoriteCityTable.TABLE_NAME,
                new String[] {FavoriteCityTable.COLUMN_CITY,
                        FavoriteCityTable.COLUMN_COUNTRY,
                        FavoriteCityTable.COLUMN_TEMPERATURE,
                        FavoriteCityTable.COLUMN_FAVORITE,
                        FavoriteCityTable.COLUMN_UPDATED},null,null,null,null,null);

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
        if (c.getInt(3) == 1 ) {
            favoriteCity.setFavorite(true);
        } else {
            favoriteCity.setFavorite(false);
        }
        Date updatedDate = new Date(c.getInt(4));
        favoriteCity.setUpdated(updatedDate);
        return favoriteCity;
    }

}
