package com.example.akarsh.hw06;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Akarsh on 10/14/2016.
 */

public class GetWeatherForecastDataAysnc extends AsyncTask<String,Void,ArrayList<Weather>> {

    private IWeatherDataHandler dataHandler;
    private String errorMessage = null;

    public GetWeatherForecastDataAysnc(IWeatherDataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    @Override
    protected ArrayList<Weather> doInBackground(String... params) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            String JSONResponse = stringBuilder.toString();
            return parseJSON(JSONResponse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        if(weathers!=null)
        {
            dataHandler.weatherDataUpdated(weathers);

        }
    }

    private ArrayList<Weather> parseJSON(String jsonResponse)
    {
        ArrayList<Weather> weatherList = new ArrayList<>();

        try {
            JSONObject rootJSON = new JSONObject(jsonResponse);

            // TODO Check for error

            JSONObject forcastJSONObject = rootJSON.getJSONObject("city");
            String cityId = forcastJSONObject.getString("id");
            String cityName = forcastJSONObject.getString("name");
            String country = forcastJSONObject.getString("country");
            JSONArray forecastJSONList = rootJSON.getJSONArray("list");
            for(int index = 0; index < forecastJSONList.length(); index++)
            {
                JSONObject currentForecast = forecastJSONList.getJSONObject(index);
                String dateTime = currentForecast.getString("dt_txt");
                JSONObject currentMain = currentForecast.getJSONObject("main");
                JSONArray currentWeather = currentForecast.getJSONArray("weather");
                JSONObject currentWind = currentForecast.getJSONObject("wind");
                String time = currentForecast.getString("dt_txt");
                double temp = currentMain.getDouble("temp");
                String maxTemp = currentMain.getString("temp_max");
                String minTemp = currentMain.getString("temp_min");
                String pressure = currentMain.getString("pressure");
                String humidity = currentMain.getString("humidity");
                String weatherType = currentWeather.getJSONObject(0).getString("description");
                String imageIconUrl =  "http://api.openweathermap.org/img/w/" + currentWeather.getJSONObject(0).getString("icon");
                String windSpeed = currentWind.getString("speed");
                String windDir = currentWind.getString("deg");

                Weather weather = new Weather(cityId,cityName,country,temp,Weather.WEATHER_CELSIUS,maxTemp,minTemp,windSpeed,windDir,pressure,humidity,weatherType,dateTime,imageIconUrl);
                weatherList.add(weather);
            }
            return weatherList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}


interface IWeatherDataHandler
{
    void weatherDataUpdated(ArrayList<Weather> weathers);
    void onError(String errorMessage);

}