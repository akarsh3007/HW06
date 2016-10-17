package com.example.akarsh.hw06;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ahmet on 16/10/2016.
 */

public class FavoriteCityListAdapter extends RecyclerView.Adapter<FavoriteCityListAdapter.ViewHolder> {

    private Context mContext;
    private List<FavoriteCity> favoriteCityList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textLocation;
        public TextView textTemperature;
        public TextView textUpdate;
        public ImageButton imageButtonFavorite;

        public ViewHolder(View itemView) {
            super(itemView);

            textLocation = (TextView) itemView.findViewById(R.id.textLocation);
            textTemperature = (TextView) itemView.findViewById(R.id.textTemperature);
            textUpdate = (TextView) itemView.findViewById(R.id.textUpdate);
            imageButtonFavorite = (ImageButton) itemView.findViewById(R.id.imageButtonFavorite);
        }
    }

    public FavoriteCityListAdapter(Context mContext, List<FavoriteCity> favoriteCityList) {
        this.mContext = mContext;
        this.favoriteCityList = favoriteCityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rowView = layoutInflater.inflate(R.layout.favorite_city_row,parent,false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FavoriteCity currentFavorite = favoriteCityList.get(position);

        holder.textUpdate.setText("TEST");
        holder.textTemperature.setText(Double.toString(currentFavorite.getTemperature()));
        holder.textLocation.setText(currentFavorite.getCity() + ", " + currentFavorite.getCountry());

    }

    @Override
    public int getItemCount() {
        return favoriteCityList.size();
    }
}
