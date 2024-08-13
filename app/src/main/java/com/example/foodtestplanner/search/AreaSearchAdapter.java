package com.example.foodtestplanner.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtestplanner.R;
import com.example.foodtestplanner.model.dto.AreaItem;

import java.util.List;

public class AreaSearchAdapter extends RecyclerView.Adapter<AreaSearchAdapter.ViewHolder> {

Context context;
private List<AreaItem>areaItemList;
private OnAreaClickListner onAreaClickListner;

    public AreaSearchAdapter(Context context, List<AreaItem> areaItems, OnAreaClickListner onAreaClickListner) {
        this.context = context;
        this.areaItemList = areaItems;
        this.onAreaClickListner = onAreaClickListner;
    }

    public void setAreaItems(List<AreaItem> areaItems) {

        this.areaItemList = areaItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.search_by_country,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        context=parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AreaItem areaItem=areaItemList.get(position);
        holder.text.setText(areaItem.getArea());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAreaClickListner != null) {
                    onAreaClickListner.onAreaClick(areaItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.textcountryid);
        }
    }
}
