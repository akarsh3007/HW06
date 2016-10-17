package com.example.akarsh.hw06;

public class DailyWeather {
    private String cityId, city, country, temperatureUnit, iconImgUrl, date;
    private double totalTemperature;
    private int dataPoints;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getIconImgUrl() {
        return iconImgUrl;
    }

    public void setIconImgUrl(String iconImgUrl) {
        this.iconImgUrl = iconImgUrl;
    }

    public double getTotalTemperature() {
        return totalTemperature;
    }

    public void setTotalTemperature(double totalTemperature) {
        this.totalTemperature = totalTemperature;
    }

    public int getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(int dataPoints) {
        this.dataPoints = dataPoints;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature(){
        double averageTemperature = this.totalTemperature / this.dataPoints;
        return Double.toString(averageTemperature);
    }
}
