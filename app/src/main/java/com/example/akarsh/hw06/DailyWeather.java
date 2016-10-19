package com.example.akarsh.hw06;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyWeather extends Weather {
    private double averageTemperature;

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public String getAverageTemperatureText(){
        String temperatureUnitText;
        if (this.getTemperatureUnit() == WEATHER_FAHRENHEIT){
            temperatureUnitText = " °F";
        } else {
            temperatureUnitText = " °C";
        }
        return String.format("%.2f " , averageTemperature) + temperatureUnitText;
    }

    @Override
    public Date getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        try {
            return dateFormat.parse(getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

