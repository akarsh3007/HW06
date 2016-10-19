package com.example.akarsh.hw06;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Akarsh on 10/14/2016.
 */

public class Preferences extends PreferenceActivity {

    public static String TEMP_UNIT_C_SYMBOL = "°C";
    public static String TEMP_UNIT_F_SYMBOL = "°F";
    public static int TEMP_UNIT_CHANGED_TO_F = 1;
    public static int TEMP_UNIT_CHANGED_TO_C = 2;
    public static int TEMP_UNIT_UNCHANGED = -1;
    private String currentUnit = "";


    // invoke fragments from the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentUnit = preferences.getString(MainActivity.TEMP_PREF_KEY,"");

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!currentUnit.equals( preferences.getString(MainActivity.TEMP_PREF_KEY,""))) {
            if (currentUnit.equals(TEMP_UNIT_C_SYMBOL)){
                setResult(TEMP_UNIT_CHANGED_TO_F);
            } else {
                setResult(TEMP_UNIT_CHANGED_TO_C);
            }
        } else {
            setResult(TEMP_UNIT_UNCHANGED);
        }
        super.onBackPressed();
    }

    // Create Preference fragment to use addPreferenceFromResource
    public static class PreferencesFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }


}
