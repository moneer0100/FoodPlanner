package com.example.foodtestplanner.searchByName.view;

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
import com.example.foodtestplanner.model.dto.MealsItem;

import java.util.List;

public class SearchMealAdapter extends RecyclerView.Adapter<SearchMealAdapter.ViewHolder> {
    Context context;
    private List<MealsItem>searchMealItem;
    private OnSearchclick onSearchclick;

    public SearchMealAdapter(Context context, List<MealsItem> searchMealItem, OnSearchclick onSearchclick) {
        this.context = context;
        this.searchMealItem = searchMealItem;
        this.onSearchclick = onSearchclick;
    }

    public void setSearchMealItem(List<MealsItem> searchMealItem) {
        this.searchMealItem = searchMealItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.search_by_name_meal,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealsItem mealsItem=searchMealItem.get(position);
        holder.text.setText(mealsItem.getStrMeal());
        Glide.with(context).load(mealsItem.getStrMealThumb()).into(holder.imag);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onSearchclick!=null){
                        onSearchclick.onSearchClickMeal(mealsItem);

                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return searchMealItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView imag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          text=itemView.findViewById(R.id.textView7);
          imag=itemView.findViewById(R.id.imageView4);
        }
    }
}
