package com.example.akarsh.hw06;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class HourlyWeatherListAdapter extends RecyclerView.Adapter<HourlyWeatherListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Weather> weatherArrayList;

    public HourlyWeatherListAdapter(Context mContext, ArrayList<Weather> weatherArrayList) {
        this.mContext = mContext;
        this.weatherArrayList = weatherArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTime, textTemperature, textCondition, textHumidity, textPressure, textWind;
        public ImageView imageWeather;

        public ViewHolder(View itemView) {
            super(itemView);

            textTime = (TextView) itemView.findViewById(R.id.textHourlyTime);
            textTemperature = (TextView) itemView.findViewById(R.id.textHourlyTemperature);
            textCondition = (TextView) itemView.findViewById(R.id.textHourlyCondition);
            textHumidity = (TextView) itemView.findViewById(R.id.textHourlyHumidity);
            textPressure = (TextView) itemView.findViewById(R.id.textHourlyPressure);
            textWind = (TextView) itemView.findViewById(R.id.textHourlyWind);

            imageWeather = (ImageView) itemView.findViewById(R.id.imageHourlyWeather);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View itemView = layoutInflater.inflate(R.layout.hourly_forecast_view,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather currentWeather = weatherArrayList.get(position);

        holder.textWind.setText(mContext.getString(R.string.textLabelWind) + currentWeather.getWindSpeed());
        holder.textPressure.setText(mContext.getString(R.string.textLabelPressure) + currentWeather.getPressure());
        holder.textHumidity.setText(mContext.getString(R.string.textLabelHumidity) + currentWeather.getHumidity());
        holder.textCondition.setText(mContext.getString(R.string.textLabelCondition) + currentWeather.getCondition());
        holder.textTemperature.setText(mContext.getString(R.string.textLabelTemperature) + currentWeather.getTemperatureText());
        SimpleDateFormat utcDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeDateFormat = new SimpleDateFormat("h:mm aa");
        String timeDate = "";
        try {
            timeDate = timeDateFormat.format(utcDateFormat.parse(currentWeather.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textTime.setText(timeDate);

        Picasso.with(mContext).load(currentWeather.getIconImgUrl()).into(holder.imageWeather);

    }

    @Override
    public int getItemCount() {
        return weatherArrayList.size();
    }
}