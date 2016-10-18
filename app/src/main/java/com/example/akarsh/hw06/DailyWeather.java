package com.example.akarsh.hw06;

public class DailyWeather extends Weather {
    private double averageTemperature;

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public String getAverageTemperatureText(){
        return String.format("%.2f " , averageTemperature) + getTemperatureUnit();
    }
}

