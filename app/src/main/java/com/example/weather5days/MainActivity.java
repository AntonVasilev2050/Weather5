package com.example.weather5days;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather5days.adapters.WeatherAdapter;
import com.example.weather5days.api.ApiFactory;
import com.example.weather5days.api.ApiService;
import com.example.weather5days.pojo.Weather5days;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static double lat = 0.0;
    private static double lon = 0.0;
    private String units = "metric";
    private String lang = "ru";
    private String appid = "292fc3d250148f4c77a7a51ac68a6302";

    private RecyclerView recyclerViewWeather;
    private WeatherAdapter weatherAdapter;
    private TextView textViewCityName;
    private TextView textViewLocalTimeDate;
    private TextView textViewCurrentTemperature;
    private TextView textViewCorF;
    private ImageView imageViewCurrentWeatherIcon;
    private TextView textViewCurrentWeatherDescription;
    private TextView textViewFeelsLike;
    private TextView textViewCorF2;
    private TextView textViewCurrentPrecipitation;
    private TextView textViewCurrentPressure;
    private TextView textViewCurrentHumidity;
    private ImageView imageViewLocation;
    private ApiService apiService;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable;

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
                Intent intentWeather = new Intent(this, MainActivity.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.hide();
//        }
        textViewCityName = findViewById(R.id.textViewCityName);
        imageViewLocation = findViewById(R.id.imageViewLocation);
        textViewLocalTimeDate = findViewById(R.id.textViewLocalTimeDate);
        textViewCurrentTemperature = findViewById(R.id.textViewCurrentTemperature);
        textViewCorF = findViewById(R.id.textViewCorF);
        imageViewCurrentWeatherIcon = findViewById(R.id.imageViewCurrentWeatherIcon);
        textViewCurrentWeatherDescription = findViewById(R.id.textViewCurrentWeatherDescription);
        textViewFeelsLike = findViewById(R.id.textViewFeelsLike);
        textViewCorF2 = findViewById(R.id.textViewCorF2);
        textViewCurrentPrecipitation = findViewById(R.id.textViewCurrentPrecipitation);
        textViewCurrentPressure = findViewById(R.id.textViewCurrentPressure);
        textViewCurrentHumidity = findViewById(R.id.textViewCurrentHumidity);
        recyclerViewWeather = findViewById(R.id.recyclerViewWeater);
        weatherAdapter = new WeatherAdapter();
        weatherAdapter.setWeather5days(new Weather5days());
        recyclerViewWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewWeather.setAdapter(weatherAdapter);
        getCurrentLocation();
        getWeather();

    }

    public void onClickImageViewLocation(View view) {
        getCurrentLocation();
        getWeather();
    }

    public void getWeather() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();

        disposable = apiService.getWeather5days(lat, lon, units, lang, appid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather5days>() {
                    @Override
                    public void accept(Weather5days weather5days) throws Exception {
                        weatherAdapter.setWeatherLists(weather5days.getWeatherList());

                        weatherAdapter.setWeather5days(weather5days);
                        textViewCityName.setText(weatherAdapter.getWeather5days().getCity().getName());
                        showCurrentWeather();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "" + throwable, Toast.LENGTH_SHORT).show();
                        Log.i("myStr", "exception== " + throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
//                ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
//               public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                      int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    Toast.makeText(MainActivity.this, "координаты получены " + lat + " " + lon, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Не могу получить текущие координаты", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void showCurrentWeather(){
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("EEEE dd-MM-yyyy HH:mm");
        textViewLocalTimeDate.setText(df.format(calendar.getTime()));
        textViewCurrentTemperature.setText("" + Math.round(weatherAdapter.getWeatherLists().get(0).getMain().getTemp()));
        textViewCurrentWeatherDescription.setText(weatherAdapter.getWeatherLists().get(0).getWeather().get(0).getDescription());
        textViewFeelsLike.setText(weatherAdapter.getWeatherLists().get(0).getMain().getFeelsLike().toString());
        try {
            textViewCurrentPrecipitation.setText((int) (weatherAdapter.getWeatherLists().get(0).getPop() *100) + "% ("
                    + (Double) weatherAdapter.getWeatherLists().get(0).getSnow().get3h() + "cm)");
        }catch (NullPointerException eSnow){
            try {
                textViewCurrentPrecipitation.setText((int) (weatherAdapter.getWeatherLists().get(0).getPop() *100) + "% ("
                        + (Double) weatherAdapter.getWeatherLists().get(0).getRain().get3h() + "cm)");
            }catch (NullPointerException eRain){
                textViewCurrentPrecipitation.setText((int)(weatherAdapter.getWeatherLists().get(0).getPop() *100) + "% (0cm)");
            }
        }
        textViewCurrentPressure.setText("" + weatherAdapter.getWeatherLists().get(0).getMain().getPressure());
        textViewCurrentHumidity.setText(weatherAdapter.getWeatherLists().get(0).getMain().getHumidity() + "%");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}