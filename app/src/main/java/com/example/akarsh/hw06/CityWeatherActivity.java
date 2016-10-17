package com.example.akarsh.hw06;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        int parsedData = 0;

        DailyWeather newWeather = null;
        ArrayList<DailyWeather> dailyWeather = new ArrayList<>();
        for (Weather currentWeather: weatherData)
        {
            String currentDate = dailyDateFormat.format(currentWeather.getDate());

            if (!currentDate.equals(parsingDate)){
                // NEW DATE FOUND
                parsingDate = currentDate;
                newWeather = new DailyWeather();
                newWeather.setDate(currentDate);
                newWeather.setCityId(currentWeather.getCityId());
                newWeather.setCity(currentWeather.getCity());
                newWeather.setCountry(currentWeather.getCountry());
                newWeather.setDataPoints(1);
                newWeather.setTotalTemperature(Double.parseDouble(currentWeather.getTemperature()));
                dailyData.add(newWeather);
            } else {
                // STILL PARSING THE SAME DATE
                newWeather.setDataPoints(newWeather.getDataPoints()+1);
                newWeather.setTotalTemperature(newWeather.getTotalTemperature()+Double.parseDouble(currentWeather.getTemperature()));
            }

        }

        RecyclerView recyclerDaily = (RecyclerView) findViewById(R.id.recyclerDaily);
        recyclerDaily.getAdapter().notifyDataSetChanged();
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
