package com.example.weather5days.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather5days.MainActivity;
import com.example.weather5days.R;
import com.example.weather5days.pojo.Weather5days;
import com.example.weather5days.pojo.WeatherList;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private List<WeatherList> weatherLists;

    public List<WeatherList> getWeatherLists() {
        return weatherLists;
    }

    public void setWeatherLists(List<WeatherList> weatherLists) {
        this.weatherLists = weatherLists;
        notifyDataSetChanged();
    }

    private Weather5days weather5days;

    public Weather5days getWeather5days() {
        return weather5days;
    }

    public void setWeather5days(Weather5days weather5days) {
        this.weather5days = weather5days;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
//        WeatherList weatherList = weatherLists.get(position);
        WeatherList weatherList = weather5days.getWeatherList().get(position);
        holder.textViewDateTime.setText(weatherList.getDtTxt());
        holder.textViewTemperature.setText("" + Math.round(weatherList.getMain().getTemp()));
        holder.textViewDescription.setText(weatherList.getWeather().get(0).getDescription());
        try {
            holder.textViewPrecipitation.setText((int) (weatherList.getPop() *100) + "% ("
                    + (Double) weatherList.getSnow().get3h() + "cm)");
        }catch (NullPointerException eSnow){
            try {
                holder.textViewPrecipitation.setText((int) (weatherList.getPop() *100) + "% ("
                        + (Double) weatherList.getRain().get3h() + "cm)");
            }catch (NullPointerException eRain){
                holder.textViewPrecipitation.setText((int)(weatherList.getPop() *100) + "% (0cm)");
            }
        }
        holder.textViewHumidity.setText("" + weatherList.getMain().getHumidity() + "%");
        holder.textViewPressure.setText("" + weatherList.getMain().getPressure());
        holder.textViewPressureUnit.setText("mBar");
        holder.textViewVisibility.setText("" + weatherList.getVisibility());
        holder.textViewVisibilityUnit.setText("m");
        if(weatherList.getWeather().get(0).getDescription().equals("небольшой снег")
                || weatherList.getWeather().get(0).getDescription().equals("снег")){
            holder.imageViewWeatherIcon.setImageResource(R.drawable.snow);
        }
        if(weatherList.getWeather().get(0).getDescription().equals("небольшой дождь")
                || weatherList.getWeather().get(0).getDescription().equals("дождь")){
            holder.imageViewWeatherIcon.setImageResource(R.drawable.rain);
        }
        if(weatherList.getWeather().get(0).getDescription().equals("пасмурно")){
            holder.imageViewWeatherIcon.setImageResource(R.drawable.overcast);
        }
        if(weatherList.getWeather().get(0).getDescription().equals("переменная облачность")
                || weatherList.getWeather().get(0).getDescription().equals("небольшая облачность")
                || weatherList.getWeather().get(0).getDescription().equals("облачно с прояснениями")){
            holder.imageViewWeatherIcon.setImageResource(R.drawable.day_partial_cloud);
        }
        if(weatherList.getWeather().get(0).getDescription().equals("ясно")){
            holder.imageViewWeatherIcon.setImageResource(R.drawable.day_clear);
        }

    }

    @Override
    public int getItemCount() {
        return weather5days.getCnt();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDateTime;
        private TextView textViewDescription;
        private TextView textViewPrecipitation;
        private TextView textViewHumidity;
        private TextView textViewTemperature;
        private TextView textViewPressure;
        private TextView textViewPressureUnit;
        private TextView textViewVisibility;
        private TextView textViewVisibilityUnit;
        private ImageView imageViewWeatherIcon;
//        private TextView textViewCityName;
        private TextView textViewLocalTimeDate;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrecipitation = itemView.findViewById(R.id.textViewPrecipitation);
            textViewHumidity = itemView.findViewById(R.id.textViewHumidity);
            textViewTemperature = itemView.findViewById(R.id.textViewTemperature);
            textViewPressure = itemView.findViewById(R.id.textViewPressure);
            textViewPressureUnit = itemView.findViewById(R.id.textViewPressureUnit);
            textViewVisibility = itemView.findViewById(R.id.textViewVisibility);
            textViewVisibilityUnit = itemView.findViewById(R.id.textViewVisibilityUnit);
            imageViewWeatherIcon = itemView.findViewById(R.id.imageViewWeatherIcon);
//            textViewCityName = itemView.findViewById(R.id.textViewCityName);
            textViewLocalTimeDate = itemView.findViewById(R.id.textViewLocalTimeDate);
        }
    }
}
