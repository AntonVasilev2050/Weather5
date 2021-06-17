package com.avvsoft2050.weather5days.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avvsoft2050.weather5days.Converters;
import com.avvsoft2050.weather5days.R;
import com.avvsoft2050.weather5days.pojo.Weather5days;
import com.avvsoft2050.weather5days.pojo.WeatherList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherViewHolder> {
    private List<WeatherList> weatherLists;
    private Weather5days weather5days;
    private WeatherForecastAdapter.OnWeatherClickListener onWeatherClickListener;
    private static int firstColor;
    private static int secondColor;
    private static String celsiusOrFahrenheit = "C";
    private static String windSpeedUnit = "м/с";
    private static String pressureUnit = "мм рт.ст.";
    SharedPreferences preferences;


    public WeatherForecastAdapter(Context context, Weather5days weather5days) {
        this.weather5days = weather5days;
        weatherLists = new ArrayList<WeatherList>();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        WeatherForecastAdapter.celsiusOrFahrenheit = preferences.getString("celsiusOrFahrenheit", "C");
        WeatherForecastAdapter.windSpeedUnit = preferences.getString("windSpeedUnit", "м/с");
        WeatherForecastAdapter.pressureUnit = preferences.getString("pressureUnit", "мм рт.ст.");
        WeatherForecastAdapter.firstColor = preferences.getInt("firstColor", context.getResources().getColor(R.color.blue4));
        WeatherForecastAdapter.secondColor = preferences.getInt("secondColor", context.getResources().getColor(R.color.blue5));
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

    public void setOnWeatherClickListener(WeatherForecastAdapter.OnWeatherClickListener onWeatherClickListener) {
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
        if(WeatherForecastAdapter.celsiusOrFahrenheit.equals("C")){
            holder.textViewCorF.setText("C");
            holder.textViewCorF2.setText("C");
            holder.textViewCurrentTemperature.setText(String.format(Locale.ROOT,"%d", Math.round(temperatureC)));
            holder.textViewFeelsLike.setText(String.format(Locale.ROOT, "%d", Math.round(temperatureFeelsLikeC)));
        }else if(celsiusOrFahrenheit.equals("F")){
            holder.textViewCorF.setText("F");
            holder.textViewCorF2.setText("F");
            holder.textViewCurrentTemperature.setText(String.format(Locale.ROOT,"%d", Math.round(temperatureF)));
            holder.textViewFeelsLike.setText(String.format(Locale.ROOT, "%d", Math.round(temperatureFeelsLikeC)));
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
        holder.cardViewWeatherItem.setCardBackgroundColor(secondColor);
    }

    @Override
    public int getItemCount() {
        return weather5days.getCnt();
//        return weatherLists.size();
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
        private CardView cardViewWeatherItem;

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
            cardViewWeatherItem = itemView.findViewById(R.id.cardViewWeatherItem);
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
