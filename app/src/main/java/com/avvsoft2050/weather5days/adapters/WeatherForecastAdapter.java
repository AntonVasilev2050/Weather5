package com.avvsoft2050.weather5days.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avvsoft2050.weather5days.Converters;
import com.avvsoft2050.weather5days.R;
import com.avvsoft2050.weather5days.pojo.Weather5days;
import com.avvsoft2050.weather5days.pojo.WeatherList;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherViewHolder> {
    private List<WeatherList> weatherLists;
    private Weather5days weather5days;
    private WeatherAdapter.OnWeatherClickListener onWeatherClickListener;
    private int firstColor;
    private int secondColor;
    private static String celsiusOrFahrenheit;
    private static String windSpeedUnit;
    private static String pressureUnit;

    public WeatherForecastAdapter(Weather5days weather5days, int secondColor) {
        this.weather5days = weather5days;
        this.secondColor = secondColor;
        weatherLists = new ArrayList<WeatherList>();
    }

    public List<WeatherList> getWeatherLists() {
        return weatherLists;
    }
    public void setWeatherLists(List<WeatherList> weatherLists) {
        this.weatherLists = weatherLists;
        notifyDataSetChanged();
    }

    public Weather5days getWeather5days() {
        return weather5days;
    }
    public void setWeather5days(Weather5days weather5days) {
        this.weather5days = weather5days;
    }

    public interface OnWeatherClickListener{
        void onWeatherClick(int position);
        void onWeatherLongClick(int position);
    }

    public void setOnWeatherClickListener(WeatherAdapter.OnWeatherClickListener onWeatherClickListener) {
        this.onWeatherClickListener = onWeatherClickListener;
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weater_item, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForecastAdapter.WeatherViewHolder holder, int position) {
        WeatherList weatherList = weather5days.getWeatherList().get(position);
        holder.textViewLocalTimeDate.setText(Converters.dateTime(weatherList.getDtTxt(), "dd.MM EE HH:mm"));
        holder.textViewCurrentWeatherDescription.setText(weatherList.getWeather().get(0).getDescription());
        int iconId = Converters.getIconId(weatherList.getWeather().get(0).getIcon());
        holder.imageViewCurrentWeatherIcon.setImageResource(iconId);
        double temperatureC = weatherList.getMain().getTemp();
        double temperatureF = Converters.celsiusToFahrenheit(temperatureC);
        double temperatureFeelsLikeC = weatherList.getMain().getFeelsLike();
        double temperatureFeelsLikeF = Converters.celsiusToFahrenheit(temperatureFeelsLikeC);
        if(celsiusOrFahrenheit.equals("C")){
            holder.textViewCurrentTemperature.setText("" + Math.round(temperatureC));
            holder.textViewFeelsLike.setText("" + Math.round(temperatureFeelsLikeC));
        }else if(celsiusOrFahrenheit.equals("F")){
            holder.textViewCurrentTemperature.setText("" + Math.round(temperatureF));
            holder.textViewFeelsLike.setText("" + Math.round(temperatureFeelsLikeF));
        }
        try {
            holder.textViewCurrentPrecipitation.setText((int) (weatherList.getPop() * 100) + "% ("
                    + (Double) weatherList.getSnow().get3h() + "mm)");
        } catch (NullPointerException eSnow) {
            try {
                holder.textViewCurrentPrecipitation.setText((int) (weatherList.getPop() * 100) + "% ("
                        + (Double) weatherList.getRain().get3h() + "mm)");
            } catch (NullPointerException eRain) {
                holder.textViewCurrentPrecipitation.setText((int) (weatherList.getPop() * 100) + "% (0mm)");
            }
        }
        int pressure = weatherList.getMain().getPressure();
        if(pressureUnit.equals("мм рт.ст.")){
            holder.textViewCurrentPressure.setText(Math.round(pressure * 0.750064) + " " + pressureUnit);
        }else if(pressureUnit.equals("мБар")){
            holder.textViewCurrentPressure.setText(Math.round(pressure) + " " + pressureUnit);
        }
        holder.textViewCurrentHumidity.setText(weatherList.getMain().getHumidity() + "%");
        double windSpeed = (Math.round(weatherList.getWind().getSpeed()) * 10.0) / 10.0;
        double visibility = weatherList.getVisibility();
        if(windSpeedUnit.equals("м/с")){
            holder.textViewWind.setText(Math.round(windSpeed) + " " + windSpeedUnit);
            holder.textViewVisibility.setText(Math.round(visibility) + " м");
        }else if(windSpeedUnit.equals("миль/ч")){
            holder.textViewWind.setText((Math.round(windSpeed * 22.369362)) / 10.0 + " " + windSpeedUnit);
            holder.textViewVisibility.setText((Math.round(visibility * 0.00062)) + " миль");
        }
    }

    @Override
    public int getItemCount() {
//        return weather5days.getCnt();
        return weatherLists.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewLocalTimeDate;
        private TextView textViewCurrentWeatherDescription;
        private ImageView imageViewCurrentWeatherIcon;
        private TextView textViewCurrentTemperature;
        private TextView textViewCorF;
        private TextView textViewFeelsLike;
        private TextView textViewCorF2;
        private TextView textViewCurrentPrecipitation;
        private TextView textViewCurrentPressure;
        private TextView textViewCurrentHumidity;
        private TextView textViewWind;
        private TextView textViewVisibility;
        private TextView textViewWeatherForecastLabel;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocalTimeDate = itemView.findViewById(R.id.textViewLocalTimeDate);
            textViewCurrentWeatherDescription = itemView.findViewById(R.id.textViewCurrentWeatherDescription);
            imageViewCurrentWeatherIcon = itemView.findViewById(R.id.imageViewCurrentWeatherIcon);
            textViewCurrentTemperature = itemView.findViewById(R.id.textViewCurrentTemperature);
            textViewCorF = itemView.findViewById(R.id.textViewCorF);
            textViewFeelsLike = itemView.findViewById(R.id.textViewFeelsLike);
            textViewCorF2 = itemView.findViewById(R.id.textViewCorF2);
            textViewCurrentPrecipitation = itemView.findViewById(R.id.textViewCurrentPrecipitation);
            textViewCurrentPressure = itemView.findViewById(R.id.textViewCurrentPressure);
            textViewCurrentHumidity = itemView.findViewById(R.id.textViewCurrentHumidity);
            textViewWind = itemView.findViewById(R.id.textViewWind);
            textViewVisibility = itemView.findViewById(R.id.textViewVisibility);
            textViewWeatherForecastLabel = itemView.findViewById(R.id.textViewWeatherForecastLabel);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onWeatherClickListener != null){
                        onWeatherClickListener.onWeatherClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onWeatherClickListener != null){
                        onWeatherClickListener.onWeatherLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
}
