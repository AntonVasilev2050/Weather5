package com.avvsoft2050.weather5days.screens.options;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.avvsoft2050.weather5days.R;
import com.avvsoft2050.weather5days.screens.about.AboutActivity;
import com.avvsoft2050.weather5days.screens.weather.WeatherActivity;

public class ChooseBackgroundActivity extends AppCompatActivity {
    private static int firstColor;
    private static int secondColor;
    private TextView textViewChooseBackgroundLabel;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_background);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        textViewChooseBackgroundLabel = findViewById(R.id.textViewChooseBackgroundLabel);
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

    public void onClickCardView(View view) {
        CardView cardView = (CardView)view;
        int id = cardView.getId();
        switch (id){
            case R.id.cardView1:
                firstColor = getResources().getColor(R.color.blue1);
                secondColor = getResources().getColor(R.color.blue2);
                break;
            case R.id.cardView2:
                firstColor = getResources().getColor(R.color.blue4);
                secondColor = getResources().getColor(R.color.blue5);
                break;
            case R.id.cardView3:
                firstColor = getResources().getColor(R.color.green1);
                secondColor = getResources().getColor(R.color.green2);
                break;
            case R.id.cardView4:
                firstColor = getResources().getColor(R.color.green4);
                secondColor = getResources().getColor(R.color.green5);
                break;
            case R.id.cardView5:
                firstColor = getResources().getColor(R.color.orange1);
                secondColor = getResources().getColor(R.color.orange2);
                break;
            case R.id.cardView6:
                firstColor = getResources().getColor(R.color.orange4);
                secondColor = getResources().getColor(R.color.orange5);
                break;
            case R.id.cardView7:
                firstColor = getResources().getColor(R.color.red1);
                secondColor = getResources().getColor(R.color.red2);
                break;
            case R.id.cardView8:
                firstColor = getResources().getColor(R.color.red4);
                secondColor = getResources().getColor(R.color.red5);
                break;
            case R.id.cardView9:
                firstColor = getResources().getColor(R.color.grey1);
                secondColor = getResources().getColor(R.color.grey2);
                break;
            case R.id.cardView10:
                firstColor = getResources().getColor(R.color.grey4);
                secondColor = getResources().getColor(R.color.grey5);
                break;
        }
        textViewChooseBackgroundLabel.setBackgroundColor(firstColor);
        preferences.edit().putInt("firstColor", firstColor).apply();
        preferences.edit().putInt("secondColor", secondColor).apply();
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }
}
