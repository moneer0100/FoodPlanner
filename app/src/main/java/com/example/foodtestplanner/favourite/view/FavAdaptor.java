package com.example.foodtestplanner.favourite.view;

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
import com.example.foodtestplanner.model.dto.MealsItem;

import java.util.List;

public class FavAdaptor extends RecyclerView.Adapter<FavAdaptor.ViewHolder> {
private Context context;
private List<MealsItem>mealsItems;
private OnVlickFav onVlickFav;

    public FavAdaptor(Context context, List<MealsItem> mealsItems, OnVlickFav onVlickFav) {
        this.context = context;
        this.mealsItems = mealsItems;
        this.onVlickFav = onVlickFav;
    }

    public void setMealsItems(List<MealsItem> mealsItems) {
        this.mealsItems = mealsItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.fav_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        context=parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    MealsItem mealsItemfav=mealsItems.get(position);
    holder.textView.setText(mealsItemfav.getStrMeal());
        Glide.with(context).load(mealsItemfav.getStrMealThumb()).into(holder.imag);
        holder.deletImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVlickFav.onDeleteItemClick(mealsItems.get(holder.getAdapterPosition()));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: ");
            }
        });

    }

    @Override
    public int getItemCount() {
        return mealsItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imag,deletImag;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imag=itemView.findViewById(R.id.imageView5);
            deletImag=itemView.findViewById(R.id.imageView6);
            textView=itemView.findViewById(R.id.textView9);
        }
    }
}
