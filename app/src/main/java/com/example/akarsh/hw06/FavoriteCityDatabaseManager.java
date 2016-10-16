package com.example.akarsh.hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class FavoriteCityDatabaseManager {

    private Context mContext;
    private SQLiteDatabase db;
    private FavoriteCityTableDAO dao;

    public FavoriteCityDatabaseManager(Context mContext) {
        this.mContext = mContext;
        FavoriteDatabaseOpenHelper dbOpenHelper = new FavoriteDatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        dao = new FavoriteCityTableDAO(db);
    }

    public long addFavoriteCity(FavoriteCity city){
        return dao.addCity(city);
    }

    public void close(){
        if(db.isOpen()){
            db.close();
        }
    }

    public List<FavoriteCity> getAll() {
        return dao.getAll();
    }
}
