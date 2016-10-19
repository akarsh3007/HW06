package com.example.akarsh.hw06;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyWeather extends Weather {

    public double getAverageTemperature() {
        return this.getTemperature();
    }

    public void setAverageTemperature(double averageTemperature) {
        super.setTemperature(averageTemperature);
    }

    public String getAverageTemperatureText(){
        return super.getTemperatureText();
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

