package com.example.akarsh.hw06;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FavoriteCity extends Weather {

    private Boolean isFavorite;
    private Date updated;

    public FavoriteCity(){}

    public FavoriteCity(Weather weatherData){
        super();
        this.setTemperature(weatherData.getTemperature());
        this.setCity(weatherData.getCity());
        this.setCountry(weatherData.getCountry());
        this.setFavorite(false);
        Date creationDate = new Date(System.currentTimeMillis());
        this.setUpdated(creationDate);
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "FavoriteCity{" +
                "isFavorite=" + isFavorite +
                '}';
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getUpdatedText(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        return dateFormat.format(this.updated);
    }
}
