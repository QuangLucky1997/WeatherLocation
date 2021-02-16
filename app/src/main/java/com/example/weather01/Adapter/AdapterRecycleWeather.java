package com.example.weather01.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather01.R;
import com.example.weather01.fiveDay.List;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterRecycleWeather extends RecyclerView.Adapter<AdapterRecycleWeather.MyHolder> {
    private java.util.List<List> fiveDayResponeList;
    private Context context;

    public AdapterRecycleWeather(java.util.List<List> fiveDayResponeList, Context context) {
        this.fiveDayResponeList = fiveDayResponeList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_detailweather, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String day = String.valueOf(fiveDayResponeList.get(position).getDt());
        long l = Long.valueOf(day);
        Date date = new Date(l * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        String Day = simpleDateFormat.format(date);
        holder.txtDay.setText(Day);
        String hourfirst = fiveDayResponeList.get(position).getDtTxt();
        String hourFormat = hourfirst.substring(11, 16);
        holder.txtHours.setText(hourFormat);
        holder.txtStatus.setText(fiveDayResponeList.get(position).getWeather().get(0).getMain());
        Double t1 = Double.valueOf(fiveDayResponeList.get(position).getMain().getTempMax() - 273.15);
        String tempMax = String.valueOf(t1.intValue());
        holder.txtTemp1.setText(tempMax + "°");
        Double t2 = Double.valueOf(fiveDayResponeList.get(position).getMain().getTempMin() - 273.15);
        String tempMin = String.valueOf(t2.intValue());
        holder.txtTemp2.setText(tempMin + "°");
        String icon = fiveDayResponeList.get(position).getWeather().get(0).getIcon();
        Picasso.get().load("http://openweathermap.org/img/wn/" + icon + ".png").into(holder.imgweather);


    }

    @Override
    public int getItemCount() {
        return fiveDayResponeList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDay)
        TextView txtDay;
        @BindView(R.id.txtStatus)
        TextView txtStatus;
        @BindView(R.id.temp1)
        TextView txtTemp1;
        @BindView(R.id.temp2)
        TextView txtTemp2;
        @BindView(R.id.txtHours)
        TextView txtHours;
        @BindView(R.id.imgiconweather)
        ImageView imgweather;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
