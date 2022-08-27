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

import com.avvsoft2050.weather5days.utils.Converters;
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
    private static int iconSet = 4;
    SharedPreferences preferences;


    public WeatherForecastAdapter(Context context, Weather5days weather5days) {
        this.weather5days = weather5days;
        weatherLists = new ArrayList<WeatherList>();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        WeatherForecastAdapter.celsiusOrFahrenheit = preferences.getString("celsiusOrFahrenheit", "C");
        WeatherForecastAdapter.windSpeedUnit = preferences.getString("windSpeedUnit", "м/с");
        WeatherForecastAdapter.pressureUnit = preferences.getString("pressureUnit", "мм рт.ст.");
        WeatherForecastAdapter.firstColor = preferences.getInt("firstColor", context.getResources().getColor(R.color.blue1));
        WeatherForecastAdapter.secondColor = preferences.getInt("secondColor", context.getResources().getColor(R.color.blue2));
        iconSet = preferences.getInt("iconSet", 4);
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
        WeatherForecastAdapter.celsiusOrFahrenheit = preferences.getString("celsiusOrFahrenheit", "C");
        WeatherForecastAdapter.windSpeedUnit = preferences.getString("windSpeedUnit", "м/с");
        WeatherForecastAdapter.pressureUnit = preferences.getString("pressureUnit", "мм рт.ст.");
        iconSet = preferences.getInt("iconSet", 4);
        WeatherList weatherList = weather5days.getWeatherList().get(position);
        holder.textViewLocalTimeDate.setText(Converters.dateTime(weatherList.getDtTxt(), "dd.MM EE HH:mm"));
        holder.textViewCurrentWeatherDescription.setText(weatherList.getWeather().get(0).getDescription());
        int iconId = Converters.getIconId(weatherList.getWeather().get(0).getIcon(), iconSet);
        holder.imageViewCurrentWeatherIcon.setImageResource(iconId);
        double temperatureC = weatherList.getMain().getTemp();
        double temperatureF = Converters.celsiusToFahrenheit(temperatureC);
        double temperatureFeelsLikeC = weatherList.getMain().getFeelsLike();
        double temperatureFeelsLikeF = Converters.celsiusToFahrenheit(temperatureFeelsLikeC);
        if(WeatherForecastAdapter.celsiusOrFahrenheit.equals("C")){
            holder.textViewCorF.setText("C");
            holder.textViewCorF2.setText("C");
            holder.textViewCurrentTemperature.setText(String.format(Locale.ROOT,"%3d", Math.round(temperatureC)));
            holder.textViewFeelsLike.setText(String.format(Locale.ROOT, "%3d", Math.round(temperatureFeelsLikeC)));
        }else if(celsiusOrFahrenheit.equals("F")){
            holder.textViewCorF.setText("F");
            holder.textViewCorF2.setText("F");
            holder.textViewCurrentTemperature.setText(String.format(Locale.ROOT,"%3d", Math.round(temperatureF)));
            holder.textViewFeelsLike.setText(String.format(Locale.ROOT, "%3d", Math.round(temperatureFeelsLikeF)));
        }
        try {
            String snow = String.format(Locale.ROOT, "%d%% (%s mm)", (int) (weatherList.getPop() * 100), ((Double) weatherList.getSnow().get3h()));
            holder.textViewCurrentPrecipitation.setText(snow);
        } catch (NullPointerException eSnow) {
            try {
                String rain = String.format(Locale.ROOT, "%d%% (%s mm)", (int) (weatherList.getPop() * 100), ((Double) weatherList.getRain().get3h()));
                holder.textViewCurrentPrecipitation.setText(rain);
            } catch (NullPointerException eRain) {
                String noRainNoSnow = String.format(Locale.ROOT, "%d%% (0mm)", (int)(weatherList.getPop() * 100));
                holder.textViewCurrentPrecipitation.setText(noRainNoSnow);
            }
        }
        int pressure = weatherList.getMain().getPressure();
        if(pressureUnit.equals("мм рт.ст.")){
            String pressureMM = String.format(Locale.ROOT, "%d мм рт.ст.", Math.round(pressure * 0.750064));
            holder.textViewCurrentPressure.setText(pressureMM);
        }else if(pressureUnit.equals("мБар")){
            String pressureBar = String.format(Locale.ROOT, "%d мБар", Math.round(pressure));
            holder.textViewCurrentPressure.setText(pressureBar);
        }
        String humidity = String.format(Locale.ROOT, "%d%%", weatherList.getMain().getHumidity());
        holder.textViewCurrentHumidity.setText(humidity);
        double windSpeed = (Math.round(weatherList.getWind().getSpeed()) * 10.0) / 10.0;
        double visibility = weatherList.getVisibility();
        if(windSpeedUnit.equals("м/с")){
            String windSpeedMilePerSec = String.format(Locale.ROOT, "%d м/с", Math.round(windSpeed));
            holder.textViewWind.setText(windSpeedMilePerSec);
            String visibilityM = String.format(Locale.ROOT, "%d м", Math.round(visibility));
            holder.textViewVisibility.setText(visibilityM);
        }else if(windSpeedUnit.equals("миль/ч")){
            String windSpeedMPH = String.format(Locale.ROOT, "%s миль/ч", (Math.round(windSpeed * 22.369362) / 10.0));
            holder.textViewWind.setText(windSpeedMPH);
            String visibilityMiles = String.format(Locale.ROOT, "%s миль", Math.round(visibility * 0.00062));
            holder.textViewVisibility.setText(visibilityMiles);
        }
        holder.cardViewWeatherItem.setCardBackgroundColor(secondColor);
    }

    @Override
    public int getItemCount() {
        return weather5days.getCnt();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{

        private final TextView textViewLocalTimeDate;
        private final TextView textViewCurrentWeatherDescription;
        private final ImageView imageViewCurrentWeatherIcon;
        private final TextView textViewCurrentTemperature;
        private final TextView textViewCorF;
        private final TextView textViewFeelsLike;
        private final TextView textViewCorF2;
        private final TextView textViewCurrentPrecipitation;
        private final TextView textViewCurrentPressure;
        private final TextView textViewCurrentHumidity;
        private final TextView textViewWind;
        private final TextView textViewVisibility;
        private final CardView cardViewWeatherItem;

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
