package com.example.akarsh.hw06;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Weather {

    public static int WEATHER_CELSIUS = 1;
    public static int WEATHER_FAHRENHEIT = 2;


    private String cityId, city, country,
    windSpeed,windDirection,pressure,humidity,condition,time,iconImgUrl;

    private double temperature;

    public Weather(String cityId, String city, String country, double temperature, String windSpeed, String windDirection, String pressure, String humidity, String condition, String time, String iconImgUrl) {
        this.cityId = cityId;
        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.humidity = humidity;
        this.condition = condition;
        this.time = time;
        this.iconImgUrl = iconImgUrl;
    }

    public Weather() {
        // Empty constructor for subclassing
    }

    public String getIconImgUrl() {
        return iconImgUrl;
    }

    public void setIconImgUrl(String iconImgUrl) {
        this.iconImgUrl = iconImgUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public double getTemperature() {
            return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getPressure() {
        return pressure + " hPa";
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity + "%";
    }

    public String getWindText(){
        return windSpeed + " mps, " + windDirection +"°";
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCondition() {
        StringBuilder upperCaseBuilder = new StringBuilder();
        upperCaseBuilder.append(Character.toUpperCase(condition.charAt(0)));

        for (int i = 1 ; i < condition.length() ; i++){
            if (Character.isSpaceChar(condition.charAt(i-1))){
                upperCaseBuilder.append(Character.toUpperCase(condition.charAt(i)));
            } else {
                upperCaseBuilder.append(Character.toLowerCase(condition.charAt(i)));
            }
        }

        return upperCaseBuilder.toString();
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Date getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return dateFormat.parse(getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTemperatureText(){
        String temperatureUnitText;

        double temperature = this.getTemperature();

        if (MainActivity.TEMP_UNIT == WEATHER_FAHRENHEIT){
            temperatureUnitText = " °F";
            temperature = temperature *9 /5 + 32;
        } else {
            temperatureUnitText = " °C";
        }
        return String.format("%.2f " , temperature) + temperatureUnitText;
    }
}
