package com.example.weather5days.screens.options;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather5days.R;
import com.example.weather5days.screens.about.AboutActivity;
import com.example.weather5days.screens.weather.WeatherActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class OptionsActivity extends AppCompatActivity {
        private ConstraintLayout constraintLayoutOptions;
        private TextView textViewOptionsLabel;
        private TextView textViewUnitSettings;
        private TextView textViewColors;
        private ImageView imageViewExtendUnits;
        private ImageView imageViewExtendColors;
        private TextView textViewTemperatureUnitLabel;
        private TextView textViewWindUnitLabel;
        private TextView textViewPressureUnitLabel;
        private RadioButton radioButtonCelsius;
        private RadioButton radioButtonFahrenheit;
        private RadioButton radioButtonMeterPerSec;
        private RadioButton radioButtonMilePerHour;
        private RadioButton radioButtonMmHg;
        private RadioButton radioButtonMBar;
        SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        constraintLayoutOptions = findViewById(R.id.constraintLayoutOptions);
        textViewOptionsLabel = findViewById(R.id.textViewOptionsLabel);
        textViewUnitSettings = findViewById(R.id.textViewUnitSettings);
        textViewColors = findViewById(R.id.textViewColors);
        imageViewExtendUnits = findViewById(R.id.imageViewExtendUnits);
        imageViewExtendColors = findViewById(R.id.imageViewExtendColors);
        textViewTemperatureUnitLabel = findViewById(R.id.textViewTemperatureUnitLabel);
        textViewWindUnitLabel = findViewById(R.id.textViewWindUnitLabel);
        textViewPressureUnitLabel = findViewById(R.id.textViewPressureUnitLabel);
        radioButtonCelsius = findViewById(R.id.radioButtonCelsius);
        radioButtonFahrenheit = findViewById(R.id.radioButtonFahrenheit);
        radioButtonMeterPerSec = findViewById(R.id.radioButtonMeterPerSec);
        radioButtonMilePerHour = findViewById(R.id.radioButtonMilePerHour);
        radioButtonMmHg = findViewById(R.id.radioButtonMmHg);
        radioButtonMBar = findViewById(R.id.radioButtonMBar);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(Objects.equals(preferences.getString("celsiusOrFahrenheit", "C"), "C")){
            radioButtonCelsius.setChecked(true);
            radioButtonFahrenheit.setChecked(false);
        }else {
            radioButtonCelsius.setChecked(false);
            radioButtonFahrenheit.setChecked(true);
        }
        if(Objects.equals(preferences.getString("windSpeedUnit", "м/с"), "м/с")){
            radioButtonMeterPerSec.setChecked(true);
            radioButtonMilePerHour.setChecked(false);
        }else {
            radioButtonMeterPerSec.setChecked(false);
            radioButtonMilePerHour.setChecked(true);
        }
        if(Objects.equals(preferences.getString("pressureUnit", "мм рт.ст."), "мм рт.ст.")){
            radioButtonMmHg.setChecked(true);
            radioButtonMBar.setChecked(false);
        }else {
            radioButtonMmHg.setChecked(false);
            radioButtonMBar.setChecked(true);
        }
        constraintLayoutOptions.setBackgroundColor(getResources().getColor(R.color.blue4));
        textViewOptionsLabel.setBackgroundColor(getResources().getColor(R.color.blue5));
        textViewUnitSettings.setBackgroundColor(getResources().getColor(R.color.blue5));
        textViewColors.setBackgroundColor(getResources().getColor(R.color.blue5));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemWeather:
                Intent intentWeather = new Intent(this, WeatherActivity.class);
                startActivity(intentWeather);
                break;
            case R.id.itemOptions:
                Intent intentOptions = new Intent(this, OptionsActivity.class);
                startActivity(intentOptions);
                break;
            case R.id.itemAbout:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickColors(View view) {
        Intent intentColors = new Intent(this, ChooseBackgroundActivity.class);
        startActivity(intentColors);
    }

    public void onClickTempUnitChange(View view) {
        RadioButton button = (RadioButton)view;
        int id = button.getId();
        if(id == R.id.radioButtonCelsius){
            preferences.edit().putString("celsiusOrFahrenheit", "C").apply();
            radioButtonCelsius.setChecked(true);
            radioButtonFahrenheit.setChecked(false);
        }else if(id == R.id.radioButtonFahrenheit){
            preferences.edit().putString("celsiusOrFahrenheit", "F").apply();
            radioButtonCelsius.setChecked(false);
            radioButtonFahrenheit.setChecked(true);
        }
    }

    public void onClickWindUnitChange(View view) {
        RadioButton button = (RadioButton)view;
        int id = button.getId();
        if(id == R.id.radioButtonMeterPerSec){
            preferences.edit().putString("windSpeedUnit", "м/с").apply();
            radioButtonMeterPerSec.setChecked(true);
            radioButtonMilePerHour.setChecked(false);
        }else if(id == R.id.radioButtonMilePerHour){
            preferences.edit().putString("windSpeedUnit", "миль/ч").apply();
            radioButtonMeterPerSec.setChecked(false);
            radioButtonMilePerHour.setChecked(true);
        }
    }

    public void onClickPressureUnitChange(View view) {
        RadioButton button = (RadioButton)view;
        int id = button.getId();
        if(id == R.id.radioButtonMmHg){
            preferences.edit().putString("pressureUnit", "мм рт.ст.").apply();
            radioButtonMmHg.setChecked(true);
            radioButtonMBar.setChecked(false);
        }else if(id == R.id.radioButtonMBar){
            preferences.edit().putString("pressureUnit", "мБар").apply();
            radioButtonMmHg.setChecked(false);
            radioButtonMBar.setChecked(true);
        }
    }

    public void onClickImageViewExtendUnits(View view) {
        if(textViewTemperatureUnitLabel.getVisibility() == View.VISIBLE){
            textViewTemperatureUnitLabel.setVisibility(View.GONE);
            textViewWindUnitLabel.setVisibility(View.GONE);
            textViewPressureUnitLabel.setVisibility(View.GONE);
            radioButtonCelsius.setVisibility(View.GONE);
            radioButtonFahrenheit.setVisibility(View.GONE);
            radioButtonMeterPerSec.setVisibility(View.GONE);
            radioButtonMilePerHour.setVisibility(View.GONE);
            radioButtonMmHg.setVisibility(View.GONE);
            radioButtonMBar.setVisibility(View.GONE);
            imageViewExtendUnits.setImageResource(android.R.drawable.arrow_down_float);
        }else if(textViewTemperatureUnitLabel.getVisibility() == View.GONE){
            textViewTemperatureUnitLabel.setVisibility(View.VISIBLE);
            textViewWindUnitLabel.setVisibility(View.VISIBLE);
            textViewPressureUnitLabel.setVisibility(View.VISIBLE);
            radioButtonCelsius.setVisibility(View.VISIBLE);
            radioButtonFahrenheit.setVisibility(View.VISIBLE);
            radioButtonMeterPerSec.setVisibility(View.VISIBLE);
            radioButtonMilePerHour.setVisibility(View.VISIBLE);
            radioButtonMmHg.setVisibility(View.VISIBLE);
            radioButtonMBar.setVisibility(View.VISIBLE);
            imageViewExtendUnits.setImageResource(android.R.drawable.arrow_up_float);
        }
    }
}