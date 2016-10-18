package com.example.akarsh.hw06;

public class FavoriteCity extends Weather {

    private Boolean isFavorite;

    public FavoriteCity(){}

    public FavoriteCity(Weather weatherData){
        super();
        this.setTemperature(weatherData.getTemperature());
        this.setCity(weatherData.getCity());
        this.setCountry(weatherData.getCountry());
        this.setFavorite(false);
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
}
