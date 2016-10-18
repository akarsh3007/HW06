package com.example.akarsh.hw06;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DailyWeatherListAdapter extends RecyclerView.Adapter<DailyWeatherListAdapter.ViewHolder> {

    private Context mContext;
    private List<DailyWeather> dailyWeatherList;

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

    public DailyWeatherListAdapter(Context mContext, List<DailyWeather> weatherList) {
        this.mContext = mContext;
        this.dailyWeatherList = weatherList;
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
        DailyWeather currentWeather = dailyWeatherList.get(position);

        holder.textTemperature.setText(currentWeather.getAverageTemperatureText());
        holder.textDate.setText(currentWeather.getTime());
        //holder.imageWeather.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.star_gold));

        Picasso.with(mContext).load(currentWeather.getIconImgUrl()).into(holder.imageWeather);

    }

    @Override
    public int getItemCount() {
        return dailyWeatherList.size();
    }
}
