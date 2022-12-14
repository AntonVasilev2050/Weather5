package com.avvsoft2050.weather5days.screens.options;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.avvsoft2050.weather5days.R;
import com.avvsoft2050.weather5days.screens.about.AboutActivity;
import com.avvsoft2050.weather5days.screens.weather.WeatherActivity;

import java.util.Objects;

public class OptionsActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayoutOptions;
    private TextView textViewOptionsLabel;
    private TextView textViewUnitSettings;
    private TextView textViewColors;
    private ImageView imageViewExtendUnits;
    private TextView textViewTemperatureUnitLabel;
    private TextView textViewWindUnitLabel;
    private TextView textViewPressureUnitLabel;
    private RadioButton radioButtonCelsius;
    private RadioButton radioButtonFahrenheit;
    private RadioButton radioButtonMeterPerSec;
    private RadioButton radioButtonMilePerHour;
    private RadioButton radioButtonMmHg;
    private RadioButton radioButtonMBar;
    private RadioButton radioButtonIconSet01;
    private RadioButton radioButtonIconSet02;
    private RadioButton radioButtonIconSet03;
    private RadioButton radioButtonIconSet04;
    private TextView textViewIconSetLabel;
    private ConstraintLayout constraintLayoutIconSets;
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
        textViewTemperatureUnitLabel = findViewById(R.id.textViewTemperatureUnitLabel);
        textViewWindUnitLabel = findViewById(R.id.textViewWindUnitLabel);
        textViewPressureUnitLabel = findViewById(R.id.textViewPressureUnitLabel);
        radioButtonCelsius = findViewById(R.id.radioButtonCelsius);
        radioButtonFahrenheit = findViewById(R.id.radioButtonFahrenheit);
        radioButtonMeterPerSec = findViewById(R.id.radioButtonMeterPerSec);
        radioButtonMilePerHour = findViewById(R.id.radioButtonMilePerHour);
        radioButtonMmHg = findViewById(R.id.radioButtonMmHg);
        radioButtonMBar = findViewById(R.id.radioButtonMBar);
        radioButtonIconSet01 = findViewById(R.id.radioButtonIconSet01);
        radioButtonIconSet02 = findViewById(R.id.radioButtonIconSet02);
        radioButtonIconSet03 = findViewById(R.id.radioButtonIconSet03);
        radioButtonIconSet04 = findViewById(R.id.radioButtonIconSet04);
        textViewIconSetLabel = findViewById(R.id.textViewIconSetLabel);
        constraintLayoutIconSets = findViewById(R.id.constraintLayoutIconSets);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int firstColor = preferences.getInt("firstColor", ContextCompat.getColor(getApplicationContext(), R.color.blue1));
        int secondColor = preferences.getInt("secondColor", ContextCompat.getColor(getApplicationContext(), R.color.blue2));
        if (Objects.equals(preferences.getString("celsiusOrFahrenheit", "C"), "C")) {
            radioButtonCelsius.setChecked(true);
            radioButtonFahrenheit.setChecked(false);
        } else {
            radioButtonCelsius.setChecked(false);
            radioButtonFahrenheit.setChecked(true);
        }
        if (Objects.equals(preferences.getString("windSpeedUnit", "??/??"), "??/??")) {
            radioButtonMeterPerSec.setChecked(true);
            radioButtonMilePerHour.setChecked(false);
        } else {
            radioButtonMeterPerSec.setChecked(false);
            radioButtonMilePerHour.setChecked(true);
        }
        if (Objects.equals(preferences.getString("pressureUnit", "???? ????.????."), "???? ????.????.")) {
            radioButtonMmHg.setChecked(true);
            radioButtonMBar.setChecked(false);
        } else {
            radioButtonMmHg.setChecked(false);
            radioButtonMBar.setChecked(true);
        }
        if (Objects.equals(preferences.getInt("iconSet", 4), 1)) {
            radioButtonIconSet01.setChecked(true);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(false);
        } else if (Objects.equals(preferences.getInt("iconSet", 4), 2)) {
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(true);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(false);
        } else if (Objects.equals(preferences.getInt("iconSet", 4), 3)) {
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(true);
            radioButtonIconSet04.setChecked(false);
        } else if (Objects.equals(preferences.getInt("iconSet", 4), 4)) {
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(true);
        }

        constraintLayoutOptions.setBackgroundColor(firstColor);
        textViewOptionsLabel.setBackgroundColor(secondColor);
        textViewUnitSettings.setBackgroundColor(secondColor);
        textViewColors.setBackgroundColor(secondColor);
        textViewTemperatureUnitLabel.setBackgroundColor(secondColor);
        textViewWindUnitLabel.setBackgroundColor(secondColor);
        textViewPressureUnitLabel.setBackgroundColor(secondColor);
        radioButtonCelsius.setBackgroundColor(secondColor);
        radioButtonFahrenheit.setBackgroundColor(secondColor);
        radioButtonMeterPerSec.setBackgroundColor(secondColor);
        radioButtonMilePerHour.setBackgroundColor(secondColor);
        radioButtonMBar.setBackgroundColor(secondColor);
        radioButtonMmHg.setBackgroundColor(secondColor);
        textViewIconSetLabel.setBackgroundColor(secondColor);
        constraintLayoutIconSets.setBackgroundColor(secondColor);
        radioButtonIconSet01.setBackgroundColor(secondColor);
        radioButtonIconSet02.setBackgroundColor(secondColor);
        radioButtonIconSet03.setBackgroundColor(secondColor);
        radioButtonIconSet04.setBackgroundColor(secondColor);
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
        switch (id) {
            case R.id.itemWeather:
                Intent intentWeather = new Intent(this, WeatherActivity.class);
                finish();
                startActivity(intentWeather);
                break;
            case R.id.itemOptions:
                Intent intentOptions = new Intent(this, OptionsActivity.class);
                finish();
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
        finish();
        startActivity(intentColors);
    }

    public void onClickTempUnitChange(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonCelsius) {
            preferences.edit().putString("celsiusOrFahrenheit", "C").apply();
            radioButtonCelsius.setChecked(true);
            radioButtonFahrenheit.setChecked(false);
        } else if (id == R.id.radioButtonFahrenheit) {
            preferences.edit().putString("celsiusOrFahrenheit", "F").apply();
            radioButtonCelsius.setChecked(false);
            radioButtonFahrenheit.setChecked(true);
        }
    }

    public void onClickWindUnitChange(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonMeterPerSec) {
            preferences.edit().putString("windSpeedUnit", "??/??").apply();
            radioButtonMeterPerSec.setChecked(true);
            radioButtonMilePerHour.setChecked(false);
        } else if (id == R.id.radioButtonMilePerHour) {
            preferences.edit().putString("windSpeedUnit", "????????/??").apply();
            radioButtonMeterPerSec.setChecked(false);
            radioButtonMilePerHour.setChecked(true);
        }
    }

    public void onClickPressureUnitChange(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonMmHg) {
            preferences.edit().putString("pressureUnit", "???? ????.????.").apply();
            radioButtonMmHg.setChecked(true);
            radioButtonMBar.setChecked(false);
        } else if (id == R.id.radioButtonMBar) {
            preferences.edit().putString("pressureUnit", "????????").apply();
            radioButtonMmHg.setChecked(false);
            radioButtonMBar.setChecked(true);
        }
    }

    public void onClickIconSetChange(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonIconSet01) {
            preferences.edit().putInt("iconSet", 1).apply();
            radioButtonIconSet01.setChecked(true);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(false);
        } else if (id == R.id.radioButtonIconSet02) {
            preferences.edit().putInt("iconSet", 2).apply();
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(true);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(false);
        } else if (id == R.id.radioButtonIconSet03) {
            preferences.edit().putInt("iconSet", 3).apply();
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(true);
            radioButtonIconSet04.setChecked(false);
        } else if (id == R.id.radioButtonIconSet04) {
            preferences.edit().putInt("iconSet", 4).apply();
            radioButtonIconSet01.setChecked(false);
            radioButtonIconSet02.setChecked(false);
            radioButtonIconSet03.setChecked(false);
            radioButtonIconSet04.setChecked(true);
        }
    }

    public void onClickImageViewExtendUnits(View view) {
        if (textViewTemperatureUnitLabel.getVisibility() == View.VISIBLE) {
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
        } else if (textViewTemperatureUnitLabel.getVisibility() == View.GONE) {
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