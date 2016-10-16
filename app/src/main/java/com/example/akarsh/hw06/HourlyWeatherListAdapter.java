package com.example.akarsh.hw06;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ahmet on 16/10/2016.
 */

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
        holder.textWind.setText("TEST");
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}


/*
package com.example.akarsh.hw06;


        import android.content.Context;
        import android.graphics.BitmapFactory;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.List;

public class DailyWeatherListAdapter extends RecyclerView.Adapter<DailyWeatherListAdapter.ViewHolder> {

    private Context mContext;
    private List<Weather> dailyWeatherList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textDate;
        public TextView textTemperature;
        public ImageView imageWeather;

        public ViewHolder(View itemView) {
            super(itemView);

            textDate = (TextView) itemView.findViewById(R.id.textDate);
            textTemperature = (TextView) itemView.findViewById(R.id.textTemperature);
            imageWeather = (ImageView) itemView.findViewById(R.id.imageWeather);
        }
    }

    public DailyWeatherListAdapter(Context mContext, List<Weather> dailyWeatherList) {
        this.mContext = mContext;
        this.dailyWeatherList = dailyWeatherList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View itemView = layoutInflater.inflate(R.layout.daily_forecast_view,parent,false);

        // Make the View width exactly 1/3 of the container
        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        params.width = parent.getWidth() / 3;
        itemView.setLayoutParams(params);

        // Return the new View
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textTemperature.setText("TEST");
        holder.textDate.setText("TEST");
        holder.imageWeather.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.star_gold));


    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
*/