package com.example.akarsh.hw06;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String CITY_EXTRAS_KEY = "extras_city";
    public static String COUNTRY_EXTRAS_KEY = "extras_country";
    public static String FAVORITES_PREF_KEY = "FAVS";

    EditText txtCityName;
    EditText txtCountryName;
    Button  buttonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCityName = (EditText) findViewById(R.id.editTextCity);
        txtCountryName = (EditText) findViewById(R.id.editTextCountry);
        buttonSubmit = (Button) findViewById(R.id.buttonSearch);
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
