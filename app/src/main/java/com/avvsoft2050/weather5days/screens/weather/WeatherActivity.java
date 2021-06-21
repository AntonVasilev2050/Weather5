package com.avvsoft2050.weather5days.screens.weather;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avvsoft2050.weather5days.BuildConfig;
import com.avvsoft2050.weather5days.utils.Converters;
import com.avvsoft2050.weather5days.R;
import com.avvsoft2050.weather5days.adapters.WeatherForecastAdapter;
import com.avvsoft2050.weather5days.pojo.Weather5days;
import com.avvsoft2050.weather5days.screens.about.AboutActivity;
import com.avvsoft2050.weather5days.screens.options.OptionsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.Locale;

import static com.avvsoft2050.weather5days.R.string.location_error_notice;


public class WeatherActivity extends AppCompatActivity implements WeatherView {
    private static double lat = 0.0;
    private static double lon = 0.0;
    private static String cityName;
    private final static String BASE_WEATHER_ICON_URL = "https://openweathermap.org/img/wn/%s@%sx.png";
    private int firstColor;
    private int secondColor;
    private static String celsiusOrFahrenheit;
//    private static String windSpeedUnit;
//    private static String pressureUnit;

    public static String getCelsiusOrFahrenheit() {
        return celsiusOrFahrenheit;
    }

    public static double getLat() {
        return lat;
    }

    public static double getLon() {
        return lon;
    }

    public static String getCityName() {
        return cityName;
    }

    private RecyclerView recyclerViewWeather;
    private WeatherForecastAdapter weatherAdapter;
    private TextView textViewCityName;
    private ImageView imageViewWeatherNow;
    private TextView textViewTemperatureNow;
    private TextView textViewNowPlus6;
    private TextView textViewNowPlus12;
    private TextView textViewNowPlus18;
    private ImageView imageViewWeatherPlus6;
    private ImageView imageViewWeatherPlus12;
    private ImageView imageViewWeatherPlus18;
    private TextView textViewTemperature6;
    private TextView textViewTemperature12;
    private TextView textViewTemperature18;
    private TextView textViewCorF3;
    private SearchView searchViewLocation;
    private WeatherPresenter presenter;
    SharedPreferences preferences;

    private View viewLine1;
    private View viewLine2;
    private ConstraintLayout constraintLayoutMain;
    private TextView textViewWeatherForecastLabel;

    private static final String TAG = WeatherActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    FusedLocationProviderClient fusedLocationProviderClient;
    protected Location mLastLocation;

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
//        ActionBar actionBar = getSupportActionBar();
//                if(actionBar != null){
//            actionBar.hide();
//        }
        textViewCityName = findViewById(R.id.textViewCityName);
        searchViewLocation = findViewById(R.id.searchViewLocation);
        viewLine1 = findViewById(R.id.viewLine1);
        viewLine2 = findViewById(R.id.viewLine2);
        constraintLayoutMain = findViewById(R.id.constraintLayoutMain);
        imageViewWeatherNow = findViewById(R.id.imageViewWeatherNow);
        textViewTemperatureNow = findViewById(R.id.textViewTemperatureNow);
        textViewCorF3 = findViewById(R.id.textViewCorF3);
        textViewNowPlus6 = findViewById(R.id.textViewNowPlus6);
        textViewNowPlus12 = findViewById(R.id.textViewNowPlus12);
        textViewNowPlus18 = findViewById(R.id.textViewNowPlus18);
        imageViewWeatherPlus6 = findViewById(R.id.imageViewWeatherPlus6);
        imageViewWeatherPlus12 = findViewById(R.id.imageViewWeatherPlus12);
        imageViewWeatherPlus18 = findViewById(R.id.imageViewWeatherPlus18);
        textViewTemperature6 = findViewById(R.id.textViewTemperature6);
        textViewTemperature12 = findViewById(R.id.textViewTemperature12);
        textViewTemperature18 = findViewById(R.id.textViewTemperature18);
        recyclerViewWeather = findViewById(R.id.recyclerViewWeather);
        textViewWeatherForecastLabel = findViewById(R.id.textViewWeatherForecastLabel);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        cityName = preferences.getString("cityName", "Краснодар");
        celsiusOrFahrenheit = preferences.getString("celsiusOrFahrenheit", "C");
//        windSpeedUnit = preferences.getString("windSpeedUnit", "м/с");
//        pressureUnit = preferences.getString("pressureUnit", "мм рт.ст.");
        firstColor = preferences.getInt("firstColor", getResources().getColor(R.color.blue4));
        secondColor = preferences.getInt("secondColor", getResources().getColor(R.color.blue5));
        presenter = new WeatherPresenter(this);
        constraintLayoutMain.setBackgroundColor(firstColor);
        textViewWeatherForecastLabel.setBackgroundColor(secondColor);
        viewLine1.setBackgroundColor(secondColor);
        viewLine2.setBackgroundColor(secondColor);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        weatherAdapter = new WeatherForecastAdapter(this, new Weather5days());
        recyclerViewWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewWeather.setAdapter(weatherAdapter);
        presenter.getWeatherCity();
        weatherAdapter.setOnWeatherClickListener(new WeatherForecastAdapter.OnWeatherClickListener() {
            @Override
            public void onWeatherClick(int position) {
            }

            @Override
            public void onWeatherLongClick(int position) {
//                showCurrentWeather(position);
            }
        });

        searchViewLocation.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewCityName.setVisibility(View.GONE);
                recyclerViewWeather.setVisibility(View.GONE);
            }
        });

        searchViewLocation.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                cityName = preferences.getString("cityName", "Краснодар");
                textViewCityName.setVisibility(View.VISIBLE);
                recyclerViewWeather.setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchViewLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cityName = query.trim();
                searchViewLocation.clearFocus();
                presenter.getWeatherCity();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void showWeatherNow() {
        celsiusOrFahrenheit = preferences.getString("celsiusOrFahrenheit", "C");
        firstColor = preferences.getInt("firstColor", getResources().getColor(R.color.blue4));
        secondColor = preferences.getInt("secondColor", getResources().getColor(R.color.blue5));
        constraintLayoutMain.setBackgroundColor(firstColor);
        textViewWeatherForecastLabel.setBackgroundColor(secondColor);
        viewLine1.setBackgroundColor(secondColor);
        viewLine2.setBackgroundColor(secondColor);
        int iconId = Converters.getIconId(weatherAdapter.getWeatherLists().get(0).getWeather().get(0).getIcon());
        imageViewWeatherNow.setImageResource(iconId);
        double temperatureC = weatherAdapter.getWeatherLists().get(0).getMain().getTemp();
        double temperatureC6 = weatherAdapter.getWeatherLists().get(2).getMain().getTemp();
        double temperatureC12 = weatherAdapter.getWeatherLists().get(4).getMain().getTemp();
        double temperatureC18 = weatherAdapter.getWeatherLists().get(6).getMain().getTemp();
        double temperatureF = Converters.celsiusToFahrenheit(temperatureC);
        double temperatureF6 = Converters.celsiusToFahrenheit(temperatureC6);
        double temperatureF12 = Converters.celsiusToFahrenheit(temperatureC12);
        double temperatureF18 = Converters.celsiusToFahrenheit(temperatureC18);
        if (celsiusOrFahrenheit.equals("C")) {
            textViewCorF3.setText("C");
            textViewTemperatureNow.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureC)));
            textViewTemperature6.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureC6)));
            textViewTemperature12.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureC12)));
            textViewTemperature18.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureC18)));
        } else if (celsiusOrFahrenheit.equals("F")) {
            textViewCorF3.setText("F");
            textViewTemperatureNow.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureF)));
            textViewTemperature6.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureF6)));
            textViewTemperature12.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureF12)));
            textViewTemperature18.setText(String.format(Locale.ROOT, "%s", Math.round(temperatureF18)));
        }
        iconId = Converters.getIconId(weatherAdapter.getWeatherLists().get(2).getWeather().get(0).getIcon());
        imageViewWeatherPlus6.setImageResource(iconId);
        iconId = Converters.getIconId(weatherAdapter.getWeatherLists().get(4).getWeather().get(0).getIcon());
        imageViewWeatherPlus12.setImageResource(iconId);
        iconId = Converters.getIconId(weatherAdapter.getWeatherLists().get(6).getWeather().get(0).getIcon());
        imageViewWeatherPlus18.setImageResource(iconId);
        int now = LocalDateTime.now().getHour();
        if (now >= 0 && now < 6) {
            textViewNowPlus6.setText("Утро");
            textViewNowPlus12.setText("День");
            textViewNowPlus18.setText("Вечер");
        } else if (now >= 6 && now < 12) {
            textViewNowPlus6.setText("День");
            textViewNowPlus12.setText("Вечер");
            textViewNowPlus18.setText("Ночь");
        } else if (now >= 12 && now < 18) {
            textViewNowPlus6.setText("Вечер");
            textViewNowPlus12.setText("Ночь");
            textViewNowPlus18.setText("Утро");
        } else if (now >= 18 && now < 24) {
            textViewNowPlus6.setText("Ночь");
            textViewNowPlus12.setText("Утро");
            textViewNowPlus18.setText("День");
        }
    }

    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getWeatherCity();
    }

    @Override
    public void showData(Weather5days weather5days) {
        weatherAdapter.setWeather5days(weather5days);
        weatherAdapter.setWeatherLists(weather5days.getWeatherList());
        cityName = weatherAdapter.getWeather5days().getCity().getName();
        preferences.edit().putString("cityName", cityName).apply();
        showWeatherNow();
        searchViewLocation.setQuery("", false);
        searchViewLocation.onActionViewCollapsed();
        searchViewLocation.clearFocus();
        textViewCityName.setText(String.format(Locale.ROOT, "%s (%s)", cityName, weatherAdapter.getWeather5days().getCity().getCountry()));
        textViewCityName.setVisibility(View.VISIBLE);
        recyclerViewWeather.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, location_error_notice, Toast.LENGTH_LONG).show();
    }

    public void onClickImageViewLocation(View view) {
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
            presenter.getWeather();
            searchViewLocation.clearFocus();
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            lat = mLastLocation.getLatitude();
                            lon = mLastLocation.getLongitude();
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(R.id.itemWeather);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(WeatherActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }
}