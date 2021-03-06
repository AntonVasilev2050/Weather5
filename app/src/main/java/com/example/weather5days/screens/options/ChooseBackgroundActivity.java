package com.example.weather5days.screens.options;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.weather5days.R;
import com.example.weather5days.screens.about.AboutActivity;
import com.example.weather5days.screens.options.OptionsActivity;
import com.example.weather5days.screens.weather.WeatherActivity;

public class ChooseBackgroundActivity extends AppCompatActivity {
    private static int firstColor;
    private static int secondColor;
    private static boolean backgroundColorChanged = false;
    private TextView textViewChooseBackgroundLabel;

    public static int getFirstColor() {
        return firstColor;
    }

    public static int getSecondColor() {
        return secondColor;
    }

    public static boolean isBackgroundColorChanged() {
        return backgroundColorChanged;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_background);
        textViewChooseBackgroundLabel = findViewById(R.id.textViewChooseBackgroundLabel);
        backgroundColorChanged = false;
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


    public void onClickCardViewBlue1(View view) {
        firstColor = getResources().getColor(R.color.blue1);
        secondColor = getResources().getColor(R.color.blue2);
        textViewChooseBackgroundLabel.setBackgroundColor(firstColor);
        backgroundColorChanged = true;
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public void onClickCardViewBlue4(View view) {
        firstColor = getResources().getColor(R.color.blue4);
        secondColor = getResources().getColor(R.color.blue5);
        textViewChooseBackgroundLabel.setBackgroundColor(firstColor);
        backgroundColorChanged = true;
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public void onClickCardViewGreen1(View view) {
        firstColor = getResources().getColor(R.color.green1);
        secondColor = getResources().getColor(R.color.green2);
        textViewChooseBackgroundLabel.setBackgroundColor(firstColor);
        backgroundColorChanged = true;
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public void onClickCardViewGreen4(View view) {
        firstColor = getResources().getColor(R.color.green4);
        secondColor = getResources().getColor(R.color.green5);
        textViewChooseBackgroundLabel.setBackgroundColor(firstColor);
        backgroundColorChanged = true;
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public void onClickCardViewOrange1(View view) {
        firstColor = getResources().getColor(R.color.orange1);
        secondColor = getResources().getColor(R.color.orange2);
        textViewChooseBackgroundLabel.setBackgroundColor(firstColor);
        backgroundColorChanged = true;
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public void onClickCardViewOrange4(View view) {
        firstColor = getResources().getColor(R.color.orange4);
        secondColor = getResources().getColor(R.color.orange5);
        textViewChooseBackgroundLabel.setBackgroundColor(firstColor);
        backgroundColorChanged = true;
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public void onClickCardViewRed1(View view) {
        firstColor = getResources().getColor(R.color.red1);
        secondColor = getResources().getColor(R.color.red2);
        textViewChooseBackgroundLabel.setBackgroundColor(firstColor);
        backgroundColorChanged = true;
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public void onClickCardViewRed4(View view) {
        firstColor = getResources().getColor(R.color.red4);
        secondColor = getResources().getColor(R.color.red5);
        textViewChooseBackgroundLabel.setBackgroundColor(firstColor);
        backgroundColorChanged = true;
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }
}
