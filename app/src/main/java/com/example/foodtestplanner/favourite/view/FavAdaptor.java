package com.example.foodtestplanner.favourite.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtestplanner.R;
import com.example.foodtestplanner.favourite.presenter.FavPresenter;
import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsItem;

import java.util.ArrayList;
import java.util.List;

public class FavAdaptor extends RecyclerView.Adapter<FavAdaptor.ViewHolder> {
    private Context context;
    private List<MealsItem> favMealList;
    private OnVlickFav onFavoriteMealClickListener;
    private ImageView removeFromFavImage;
    private FavPresenter favouriteMealPresenterView;
    private List<MealsDetail> favMeaDetails;

    public FavAdaptor(Context context, List<MealsItem> favMealList, OnVlickFav onFavoriteMealClickListener, ImageView removeFromFavImage, FavPresenter favouriteMealPresenterView, List<MealsDetail> favMeaDetails) {
        this.context = context;
        this.favMealList = favMealList;
        this.onFavoriteMealClickListener = onFavoriteMealClickListener;
        this.removeFromFavImage = removeFromFavImage;
        this.favouriteMealPresenterView = favouriteMealPresenterView;
        this.favMeaDetails = favMeaDetails;
    }

    public void setFavMeaDetails(List<MealsDetail> favMeaDetails) {
        this.favMeaDetails = favMeaDetails;
    }

    public FavAdaptor(Context context, List<MealsItem> favMealList, List<MealsDetail> favMeaDetails, OnVlickFav onFavoriteMealClickListener) {
        this.context = context;
        this.favMealList = favMealList;
        this.favMeaDetails = favMeaDetails;
        this.onFavoriteMealClickListener = onFavoriteMealClickListener;
    }

    public void setFavMealList(List<MealsItem> favMealList) {
        this.favMealList = favMealList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fav_layout, parent, false);
        context = parent.getContext();
        removeFromFavImage = view.findViewById(R.id.imageView6);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdaptor.ViewHolder holder, int position) {
        MealsItem mealsItem = favMealList.get(position);
        holder.bind(mealsItem);
        holder.deleteMealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteMealClickListener.onDeleteItemClick(favMealList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return favMealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mealImage;
        private ImageView deleteMealImage;
        private TextView mealName;
        private TextView mealCountry;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.imageView5);
            deleteMealImage = itemView.findViewById(R.id.imageView6);
            mealName = itemView.findViewById(R.id.textView9);


        }

        public void bind(MealsItem mealsItem) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mealName.setText(mealsItem.getStrMeal());
                    Glide.with(context).load(mealsItem.getStrMealThumb()).into(mealImage);

                }
            });
        }
}

}