package com.example.weather5days.screens.options;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.weather5days.R;
import com.example.weather5days.screens.about.AboutActivity;
import com.example.weather5days.screens.weather.WeatherActivity;

public class OptionsActivity extends AppCompatActivity {
        private ConstraintLayout constraintLayoutOptions;
        private TextView textViewOptionsLabel;
        private TextView textViewUnitSettings;
        private TextView textViewLocation;
        private TextView textViewColors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        constraintLayoutOptions = (ConstraintLayout)findViewById(R.id.constraintLayoutOptions);
        textViewOptionsLabel = findViewById(R.id.textViewOptionsLabel);
        textViewUnitSettings = findViewById(R.id.textViewUnitSettings);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewColors = findViewById(R.id.textViewColors);
        if(ChooseBackgroundActivity.isBackgroundColorChanged()){
            constraintLayoutOptions.setBackgroundColor(ChooseBackgroundActivity.getFirstColor());
            textViewOptionsLabel.setBackgroundColor(ChooseBackgroundActivity.getSecondColor());
            textViewUnitSettings.setBackgroundColor(ChooseBackgroundActivity.getSecondColor());
            textViewLocation.setBackgroundColor(ChooseBackgroundActivity.getSecondColor());
            textViewColors.setBackgroundColor(ChooseBackgroundActivity.getSecondColor());
        }else {
            constraintLayoutOptions.setBackgroundColor(getResources().getColor(R.color.blue4));
            textViewOptionsLabel.setBackgroundColor(getResources().getColor(R.color.blue5));
            textViewUnitSettings.setBackgroundColor(getResources().getColor(R.color.blue5));
            textViewLocation.setBackgroundColor(getResources().getColor(R.color.blue5));
            textViewColors.setBackgroundColor(getResources().getColor(R.color.blue5));
        }
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
}