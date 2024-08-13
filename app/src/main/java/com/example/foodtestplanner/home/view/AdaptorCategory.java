package com.example.foodtestplanner.home.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtestplanner.R;
import com.example.foodtestplanner.model.dto.CategorisItem;

import java.util.List;

public class AdaptorCategory extends RecyclerView.Adapter<AdaptorCategory.MealHolder>  {
private Context context;
private List<CategorisItem>categorisItemList;
private OnCategoryClickListner onCategoryClickListner;

    public AdaptorCategory(Context context, List<CategorisItem> categorisItemList, OnCategoryClickListner onCategoryClickListner) {
        this.context = context;
        this.categorisItemList = categorisItemList;
        Log.e("Sobaih", "AdaptorCategory: "+categorisItemList.size() );
        this.onCategoryClickListner = onCategoryClickListner;
    }

    public AdaptorCategory(Context context) {
        this.context = context;
    }

    public void setOnCategoryClickListner(OnCategoryClickListner onCategoryClickListner) {
        this.onCategoryClickListner = onCategoryClickListner;
    }

    public void setCategorisItemList(List<CategorisItem> categorisItemList) {
        this.categorisItemList = categorisItemList;
        Log.e("Sobaih", "AdaptorCategory: "+categorisItemList.size() );

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.categeoryitem,parent,false);
        MealHolder mealHolder=new MealHolder(view);
      context=  parent.getContext();

        return mealHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        CategorisItem categorisItem=categorisItemList.get(position);
        holder.text.setText(categorisItem.getCategory());
        Glide.with(context).load(categorisItem.getThumbnail()).into(holder.imag);
        holder.imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(onCategoryClickListner !=null){
                 onCategoryClickListner.onclickCategory(categorisItem);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("Sobaih", "AdaptorCategory: "+categorisItemList.size() );

        return categorisItemList.size();

    }

    public class MealHolder extends RecyclerView.ViewHolder {
    ImageView imag;
    TextView text;
    public MealHolder(@NonNull View itemView) {
        super(itemView);
        imag=itemView.findViewById(R.id.imagcatitemid);
        text=itemView.findViewById(R.id.textcatitemid);


    }
}
}
