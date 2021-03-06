package com.example.weather5days.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather5days.Converters;
import com.example.weather5days.screens.weather.WeatherActivity;
import com.example.weather5days.R;
import com.example.weather5days.pojo.Weather5days;
import com.example.weather5days.pojo.WeatherList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private List<WeatherList> weatherLists;
    private Weather5days weather5days;
    private OnWeatherClickListener onWeatherClickListener;
    private int secondColor;

    public WeatherAdapter(Weather5days weather5days, int secondColor) {
        this.weather5days = weather5days;
        this.secondColor = secondColor;
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

    public void setOnWeatherClickListener(OnWeatherClickListener onWeatherClickListener) {
        this.onWeatherClickListener = onWeatherClickListener;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item_short, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherList weatherList = weather5days.getWeatherList().get(position);
        holder.textViewDateTime.setText(Converters.dateTime(weatherList.getDtTxt(), "E  HH:mm"));
        holder.textViewTemperature.setText("" + Math.round(weatherList.getMain().getTemp()));
        holder.textViewDescription.setText(weatherList.getWeather().get(0).getDescription());
        holder.cardViewWeatherItemShort.setCardBackgroundColor(secondColor);
        holder.cardViewWeatherItemShort.setBackgroundColor(secondColor);
        Picasso.get().load(String.format(WeatherActivity.getBASE_WEATHER_ICON_URL(), weatherList.getWeather().get(0).getIcon(), 2))
                .into(holder.imageViewWeatherIcon);
    }

    @Override
    public int getItemCount() {
        return weather5days.getCnt();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDateTime;
        private TextView textViewTemperature;
        private TextView textViewDescription;
        private ImageView imageViewWeatherIcon;
        private CardView cardViewWeatherItemShort;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
            textViewTemperature = itemView.findViewById(R.id.textViewTemperature);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageViewWeatherIcon = itemView.findViewById(R.id.imageViewWeatherIcon);
            cardViewWeatherItemShort = itemView.findViewById(R.id.cardViewWeatherItemShort);
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
