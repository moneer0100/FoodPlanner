package com.example.foodtestplanner.mealDetails.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtestplanner.R;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.dto.Measurement;

import java.util.List;

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.ViewHolder> {

    private Context context;
    private List<Measurement> mealsItemList;

    private static final String URL = "https://www.themealdb.com/images/ingredients/";

    public MealDetailsAdapter(Context context, List<Measurement> mealsItemList) {
        this.context = context;
        this.mealsItemList = mealsItemList;
    }

    public MealDetailsAdapter(MealDetails mealDetails, MealRepositoryImpl instance) {
    }

    public void setMealsItemList(List<Measurement> mealsItemList) {
        this.mealsItemList = mealsItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.meal_details,parent,false);
      ViewHolder viewHolder=new ViewHolder(view);
        context=parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Measurement measurement=mealsItemList.get(position);
        holder.size.setText(measurement.getIngredientMeasure());
        holder.type.setText(measurement.getIngredientName());
        Glide.with(context).load(URL + measurement.getIngredientName()+ "-Small.png").into(holder.imag);
    }

    @Override
    public int getItemCount() {
        return mealsItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imag;
                TextView size,type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imag=itemView.findViewById(R.id.imag_meal);
            size=itemView.findViewById(R.id.text1_meal);
            type=itemView.findViewById(R.id.teaxt2_meal);

        }
    }
}
