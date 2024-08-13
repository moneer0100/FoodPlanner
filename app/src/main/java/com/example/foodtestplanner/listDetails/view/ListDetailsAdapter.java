package com.example.foodtestplanner.listDetails.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.foodtestplanner.R;
import com.example.foodtestplanner.model.dto.Measurement;

import java.util.ArrayList;
import java.util.List;

public class ListDetailsAdapter extends RecyclerView.Adapter<ListDetailsAdapter.ViewHolder> {
private Context context;
private List<Measurement> mealsItemList;
private OnListMealClickListner onListMealClickListner;
    private static final String URL = "https://www.themealdb.com/images/ingredients/";

    public ListDetailsAdapter(Context context, List<Measurement> mealsItemList, OnListMealClickListner onListMealClickListner) {
        this.context = context;
        this.mealsItemList = new ArrayList<>();
        this.onListMealClickListner = onListMealClickListner;
    }

    public ListDetailsAdapter(Context context) {
    }


    public void setMealsItemList(List<Measurement> mealsItemList) {
        this.mealsItemList = mealsItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.list_details,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        context=parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListDetailsAdapter.ViewHolder holder, int position) {
        Measurement measurement=mealsItemList.get(position);
        holder.size.setText(measurement.getIngredientMeasure());
        holder.type.setText(measurement.getIngredientName());
        Glide.with(context).load("https://www.themealdb.com/images/ingredients/" + measurement.getIngredientName()+ "-Small.png").into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mealsItemList.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
ImageView imageView;
TextView size;
TextView type;
    public ViewHolder(@NonNull View itemView) {

        super(itemView);
        imageView=itemView.findViewById(R.id.imag_meal);
        size=itemView.findViewById(R.id.text1_meal);
        type=itemView.findViewById(R.id.teaxt2_meal);
    }
}
}
