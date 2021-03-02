package com.example.weather5days.screens.weather;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.weather5days.api.ApiFactory;
import com.example.weather5days.api.ApiService;
import com.example.weather5days.pojo.Weather5days;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter {

    private String units = "metric";
    private String lang = "ru";
    private String appid = "292fc3d250148f4c77a7a51ac68a6302";

    private CompositeDisposable compositeDisposable;
    private WeatherActivity weatherActivity;

    public WeatherPresenter(WeatherActivity weatherActivity) {
        this.weatherActivity = weatherActivity;
    }

    public void getWeather() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();

        Disposable disposable = apiService.getWeather5days(WeatherActivity.getLat(), WeatherActivity.getLon(), units, lang, appid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather5days>() {
                    @Override
                    public void accept(Weather5days weather5days) throws Exception {
                        weatherActivity.showData(weather5days);
//                        weatherAdapter.setWeatherLists(weather5days.getWeatherList());
//                        weatherAdapter.setWeather5days(weather5days);
//                        textViewCityName.setText(weatherAdapter.getWeather5days().getCity().getName());
//                        showCurrentWeather(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(WeatherActivity.this, "" + throwable, Toast.LENGTH_SHORT).show();
//                        Log.i("myStr", "exception== " + throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }



    public void disposeDisposable(){
        if(compositeDisposable != null){
            compositeDisposable.dispose();
        }
    }
}
