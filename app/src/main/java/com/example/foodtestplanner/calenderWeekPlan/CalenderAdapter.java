package com.example.foodtestplanner.calenderWeekPlan;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtestplanner.R;
import com.example.foodtestplanner.calenderWeekPlan.presenter.CalenderPresenterView;
import com.example.foodtestplanner.model.dto.WeekPlan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {
    private Context context;
    private List<WeekPlan> weekPlanListMealList;
    private CalenderInClick onWeekPlanMealClickListener;
    private ImageView removeFromWeekPlanImage;
    private CalenderPresenterView weekPlanMealPresenterView;

    public CalenderAdapter(Context context, List<WeekPlan> weekPlanListMealList, CalenderInClick onWeekPlanMealClickListener, CalenderPresenterView weekPlanMealPresenterView) {
        this.context = context;
        this.weekPlanListMealList = weekPlanListMealList;
        this.onWeekPlanMealClickListener = onWeekPlanMealClickListener;
        this.weekPlanMealPresenterView = weekPlanMealPresenterView;
    }

    public void setWeekPlanListMealList(List<WeekPlan> weekPlanListMealList) {
        this.weekPlanListMealList = weekPlanListMealList;
//        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CalenderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calender_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeekPlan weekPlan = weekPlanListMealList.get(position);
        holder.bind(weekPlan);
        holder.deleteMealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWeekPlanMealClickListener.onDeleteItemClick(weekPlanListMealList.get(holder.getAdapterPosition()));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onWeekPlanMealClickListener != null) {
                    onWeekPlanMealClickListener.onWeekPlanMealClick(weekPlan);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return weekPlanListMealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mealImage;
        private ImageView deleteMealImage;
        private TextView mealName;
        private TextView mealCountry;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.weekMealImage);
            deleteMealImage = itemView.findViewById(R.id.deleteWeekMealImageView);
            mealName = itemView.findViewById(R.id.tvWeekMealName);
            cardView = itemView.findViewById(R.id.weekPlanCardView);
        }

        public void bind(WeekPlan weekPlan) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mealName.setText(weekPlan.getStrMeal());
                    Glide.with(context).load(weekPlan.getStrMealThumb()).into(mealImage);

                }

            });
        }

    }}
