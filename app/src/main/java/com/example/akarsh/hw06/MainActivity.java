package com.example.akarsh.hw06;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String CITY_EXTRAS_KEY = "extras_city";
    public static String COUNTRY_EXTRAS_KEY = "extras_country";
    public static String TEMP_PREF_KEY = "list_preference_temp_key";

    EditText txtCityName;
    EditText txtCountryName;
    Button  buttonSubmit;

    private List<FavoriteCity> favoriteCityList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCityName = (EditText) findViewById(R.id.editTextCity);
        txtCountryName = (EditText) findViewById(R.id.editTextCountry);
        buttonSubmit = (Button) findViewById(R.id.buttonSearch);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Weather App");

        FavoriteCityDatabaseManager dbManager = new FavoriteCityDatabaseManager(this);
        favoriteCityList = dbManager.getAll();

        // TEST CODE ONLY
        favoriteCityList = new ArrayList<FavoriteCity>();
        favoriteCityList.add(new FavoriteCity());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerFavorites);
        recyclerView.setAdapter(new FavoriteCityListAdapter(this,favoriteCityList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //------

    }

    @Override
    protected void onResume() {
        super.onResume();

        FavoriteCityDatabaseManager dbManager = new FavoriteCityDatabaseManager(this);
        favoriteCityList.clear();
        favoriteCityList.addAll(dbManager.getAll());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerFavorites);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.settings:
                invokeSettings();
                return true;
        }
        return true;
    }

    private void invokeSettings() {
        Intent intent = new Intent(this,Preferences.class);
        startActivity(intent);
    }

    // Check internet connection
    protected boolean isOnline()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // Submit button click

    public void onSubmit(View view)
    {
        if(isOnline())
        {
            // User clicks on submit
            EditText textCity = (EditText) findViewById(R.id.editTextCity);
            EditText textCountry = (EditText) findViewById(R.id.editTextCountry);

            // Check input
            boolean isValidate = false;
            String city = textCity.getText().toString();
            String state = textCountry.getText().toString();

            if(city!=null && !city.isEmpty() && state!=null && !state.isEmpty())
                isValidate = true;
            if(isValidate) {
                Intent intent = new Intent(this, CityWeatherActivity.class);
                intent.putExtra(CITY_EXTRAS_KEY, city);
                intent.putExtra(COUNTRY_EXTRAS_KEY, state);
                startActivity(intent);
            }
            else
            {
                if (city.isEmpty() || city==null)
                {
                    textCity.setError(getString(R.string.errorNoCity));
                }
                if (state.isEmpty() || state==null)
                {
                    textCountry.setError(getString(R.string.errorNoState));
                }
            }

        }
        else
        {
            Toast.makeText(this,"Not connected to Internet",Toast.LENGTH_LONG).show();
        }

    }
}
