package com.example.akarsh.hw06;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FavoriteCityListAdapter extends RecyclerView.Adapter<FavoriteCityListAdapter.ViewHolder> {

    private IFavoriteListener mListener;
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

    public FavoriteCityListAdapter(Context mContext, List<FavoriteCity> favoriteCityList, IFavoriteListener mListener) {
        this.mContext = mContext;
        this.favoriteCityList = favoriteCityList;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rowView = layoutInflater.inflate(R.layout.favorite_city_row,parent,false);

        // Attach listeners to the rowView
        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext,"HELLO",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        FavoriteCity currentFavorite = favoriteCityList.get(position);

        holder.textUpdate.setText("TEST");
        holder.textTemperature.setText(currentFavorite.getTemperatureText());
        holder.textLocation.setText(currentFavorite.getCity() + ", " + currentFavorite.getCountry());
        holder.textUpdate.setText("Updated on: " + currentFavorite.getUpdatedText());


        if (currentFavorite.getFavorite()){
            holder.imageButtonFavorite.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.star_gold));
        } else {
            holder.imageButtonFavorite.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.star_gray));
        }

        holder.imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.toggleFavorite(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteCityList.size();
    }

    public interface IFavoriteListener{
        void toggleFavorite(int position);
    }
}
