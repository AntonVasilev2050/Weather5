package com.avvsoft2050.weather5days.screens.weather;

import com.avvsoft2050.weather5days.api.ApiFactory;
import com.avvsoft2050.weather5days.api.ApiService;
import com.avvsoft2050.weather5days.pojo.Weather5days;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter {

    private final String units = "metric";
    private final String lang = "ru";
    private final String appid = "2ad882e3ba88a34eb4e1b8448c86aa9a";

    private final CompositeDisposable compositeDisposable  = new CompositeDisposable();
    ApiFactory apiFactory = ApiFactory.getInstance();
    ApiService apiService = apiFactory.getApiService();

    private final WeatherView weatherView;

    public WeatherPresenter(WeatherView weatherView) {
        this.weatherView = weatherView;
    }

    public void getWeather() {
        Disposable disposable = apiService.getWeather5days(WeatherActivity.getLat(), WeatherActivity.getLon(), units, lang, appid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather5days>() {
                    @Override
                    public void accept(Weather5days weather5days) throws Exception {
                        weatherView.showData(weather5days);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        weatherView.showError();
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void getWeatherCity() {
        Disposable disposable = apiService.getWeather5daysCity(WeatherActivity.getCityName(), units, lang, appid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Weather5days>() {
                    @Override
                    public void accept(Weather5days weather5days) throws Exception {
                        weatherView.showData(weather5days);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        weatherView.showError();
                    }
                });
        compositeDisposable.add(disposable);
    }


    public void disposeDisposable() {
        compositeDisposable.dispose();
    }
}
