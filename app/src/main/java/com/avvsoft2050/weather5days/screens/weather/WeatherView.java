package com.avvsoft2050.weather5days.screens.weather;

import com.avvsoft2050.weather5days.pojo.Weather5days;

public interface WeatherView {
    void showData(Weather5days weather5days);
    void showError();
}
