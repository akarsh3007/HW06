package com.example.akarsh.hw06;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CityWeatherActivity extends AppCompatActivity implements IWeatherDataHandler {

    private ProgressDialog progressLoadingData;
    private static String API_KEY = "8b54506bb9c5b49d25146fac98e0f8f0";

    private String city;
    private String country;
    private ArrayList<Weather> weatherData;
    private ArrayList<DailyWeather> dailyData;
    private TextView textViewCurrentLocation;
    String maximumTemperature;
    String minimumTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        // Create Progress Dialog
        progressLoadingData = new ProgressDialog(this);
        progressLoadingData.setMessage(getString(R.string.progressDataLodingText));
        progressLoadingData.setCancelable(false);

        // Validation done in Main Activity, You will get the data as intent.
        city = getIntent().getStringExtra(MainActivity.CITY_EXTRAS_KEY);
        country = getIntent().getStringExtra(MainActivity.COUNTRY_EXTRAS_KEY);

        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+city+","+country+"&units=metric"+"&APPID="+API_KEY;
        url = url.replaceAll(" ","%20");
        progressLoadingData.show();
        new GetWeatherForecastDataAysnc(this).execute(url);

        weatherData = new ArrayList<>();
        dailyData = new ArrayList<>();

        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerDaily);
        rv.setAdapter(new DailyWeatherListAdapter(this,dailyData));
        LinearLayoutManager lm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        RecyclerView recyclerHourly = (RecyclerView) findViewById(R.id.recyclerHourly);
        recyclerHourly.setAdapter(new HourlyWeatherListAdapter(this,weatherData));
        recyclerHourly.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cityweather_actions,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.settings:
                invokeSettings();
                return true;

            case R.id.actionSaveCity:
                saveCity();
                return true;
        }
        return true;
    }

    private void invokeSettings() {
        Intent intent = new Intent(this,Preferences.class);
        startActivity(intent);
    }

    private void saveCity() {
        FavoriteCity favoriteCity = new FavoriteCity(weatherData.get(0));

        FavoriteCityDatabaseManager dbManager = new FavoriteCityDatabaseManager(this);
        dbManager.addFavoriteCity(favoriteCity);
        dbManager.close();
    }

    @Override
    public void weatherDataUpdated(ArrayList<Weather> weathers) {
        weatherData.clear();
        weatherData.addAll(weathers);
        progressLoadingData.dismiss();

        RecyclerView recyclerHourly = (RecyclerView) findViewById(R.id.recyclerHourly);
        recyclerHourly.getAdapter().notifyDataSetChanged();


        dailyData.clear();
        // Try to parse daily data here???
        SimpleDateFormat dailyDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String parsingDate = "";

        ArrayList<Weather> dailyWeatherData = null;
        for (Weather currentWeather: weatherData)
        {
            String currentDate = dailyDateFormat.format(currentWeather.getDate());
            if (!currentDate.equals(parsingDate)){
                if (dailyWeatherData != null) {
                    dailyData.add(calculateDailyWeatherData(dailyWeatherData));
                    dailyWeatherData.clear();
                }

                dailyWeatherData = new ArrayList<>();
                parsingDate = currentDate;
                dailyWeatherData.add(currentWeather);
            } else {
                // STILL PARSING THE SAME DATE
                dailyWeatherData.add(currentWeather);
            }

        }
        // Generate the last daily data
        if (dailyWeatherData != null) {
            dailyData.add(calculateDailyWeatherData(dailyWeatherData));
        }


        RecyclerView recyclerDaily = (RecyclerView) findViewById(R.id.recyclerDaily);
        recyclerDaily.getAdapter().notifyDataSetChanged();
    }

    public DailyWeather calculateDailyWeatherData(ArrayList<Weather> weatherData){
        // Calculate the average temperature
        double temperature = 0;
        for (Weather hourlyData:weatherData
             ) {
            temperature += hourlyData.getTemperature();
        }
        double averageTemperature = temperature / weatherData.size();

        // Find the median item to use the icon
        int medianData = weatherData.size()/2;
        String medianImageURL = weatherData.get(medianData).getIconImgUrl();

        // For all other properties we use the first weather data
        Weather currentWeather = weatherData.get(0);

        // Create the DailyWeather object
        DailyWeather dailyWeatherData = new DailyWeather();

        // Simplify the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        dailyWeatherData.setTime(dateFormat.format(currentWeather.getDate()));

        // Fill the remaining properties
        dailyWeatherData.setCityId(currentWeather.getCityId());
        dailyWeatherData.setCity(currentWeather.getCity());
        dailyWeatherData.setCountry(currentWeather.getCountry());
        dailyWeatherData.setAverageTemperature(temperature / weatherData.size());
        dailyWeatherData.setIconImgUrl(medianImageURL);

        // return the generated Data
        return dailyWeatherData;

    }

    @Override
    public void onError(String errorMessage) {
        progressLoadingData.dismiss();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 5000);
    }
}
