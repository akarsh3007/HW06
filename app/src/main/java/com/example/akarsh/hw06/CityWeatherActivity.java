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

public class CityWeatherActivity extends AppCompatActivity implements IWeatherDataHandler, DailyWeatherListAdapter.IHourlyDateListener {

    private ProgressDialog progressLoadingData;
    private static String API_KEY = "8b54506bb9c5b49d25146fac98e0f8f0";

    private HourlyWeatherListAdapter hourlyWeatherListAdapter;
    private DailyWeatherListAdapter dailyWeatherListAdapter;

    private String city;
    private String country;
    private ArrayList<Weather> weatherData;
    private ArrayList<Weather> hourlyData;
    private ArrayList<DailyWeather> dailyData;

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

        // Show location on UI
        ((TextView) findViewById(R.id.textLocation)).setText("Daily Forecast for " +city + ", " + country);

        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+city+","+country+"&units=metric"+"&APPID="+API_KEY;
        url = url.replaceAll(" ","%20");
        progressLoadingData.show();
        new GetWeatherForecastDataAysnc(this).execute(url);

        weatherData = new ArrayList<>();
        dailyData = new ArrayList<>();
        hourlyData = new ArrayList<>();

        // Setup recycler views
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerDaily);
        dailyWeatherListAdapter = new DailyWeatherListAdapter(this,dailyData,this);
        rv.setAdapter(dailyWeatherListAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        RecyclerView recyclerHourly = (RecyclerView) findViewById(R.id.recyclerHourly);
        hourlyWeatherListAdapter = new HourlyWeatherListAdapter(this,hourlyData);
        recyclerHourly.setAdapter(hourlyWeatherListAdapter);
        recyclerHourly.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    protected void onResume() {
        dailyWeatherListAdapter.notifyDataSetChanged();
        hourlyWeatherListAdapter.notifyDataSetChanged();
        super.onResume();
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
        startActivityForResult(intent,1);
    }

    private void saveCity() {
        FavoriteCityDatabaseManager dbManager = new FavoriteCityDatabaseManager(this);

        FavoriteCity favoriteCity = new FavoriteCity(dailyData.get(0));

        FavoriteCity existingFavorite =  dbManager.getFavorite(favoriteCity);
        if (existingFavorite != null){
            existingFavorite.setTemperature(favoriteCity.getTemperature());
            existingFavorite.setUpdated(new Date(System.currentTimeMillis()));
            dbManager.updateCity(existingFavorite);

            Toast.makeText(this,getString(R.string.toastCityUpdated),Toast.LENGTH_SHORT).show();
        } else {
            dbManager.addFavoriteCity(favoriteCity);
            Toast.makeText(this,getString(R.string.toastCityAdded),Toast.LENGTH_SHORT).show();
        }

        dbManager.close();

    }

    @Override
    public void weatherDataUpdated(ArrayList<Weather> weathers) {
        weatherData.clear();
        weatherData.addAll(weathers);
        progressLoadingData.dismiss();

        showHourlyDataOn(weathers.get(0).getDate());

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

        // Let's update the location to show what was returned from the API
        ((TextView) findViewById(R.id.textLocation)).setText(getString(R.string.dailyForecastTitle)
                + " " + dailyWeatherData.get(0).getCity()
            + ", " + dailyWeatherData.get(0).getCountry());

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

    public void showHourlyDataOn(Date date){
        SimpleDateFormat shortDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        ((TextView) findViewById(R.id.textMainDate)).setText(getString(R.string.hourlyForecastTitle) + " " + shortDateFormat.format(date));

        hourlyData.clear();

        SimpleDateFormat dateFormat = new SimpleDateFormat("mmddyyyy");

        String dateString = dateFormat.format(date);

        for (Weather currentWeather: weatherData
             ) {
                String currentWeatherDate = dateFormat.format(currentWeather.getDate());
                if (currentWeatherDate.equals( dateString)){
                    hourlyData.add(currentWeather);
                }
        }

        ((RecyclerView) findViewById(R.id.recyclerHourly)).getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Preferences.TEMP_UNIT_CHANGED_TO_C) {
            MainActivity.TEMP_UNIT = Weather.WEATHER_CELSIUS;
            Toast.makeText(this, "Temperature Unit has been changed to " + Preferences.TEMP_UNIT_C_SYMBOL, Toast.LENGTH_SHORT).show();
        } else if (resultCode == Preferences.TEMP_UNIT_CHANGED_TO_F) {
            MainActivity.TEMP_UNIT = Weather.WEATHER_FAHRENHEIT;
            Toast.makeText(this, "Temperature Unit has been changed to " + Preferences.TEMP_UNIT_F_SYMBOL, Toast.LENGTH_SHORT).show();
        }
    }
}
