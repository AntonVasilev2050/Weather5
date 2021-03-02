package com.example.weather5days.screens.weather;

import com.example.weather5days.pojo.Weather5days;

public interface WeatherView {
    void showData(Weather5days weather5days);
    void showError();
}
