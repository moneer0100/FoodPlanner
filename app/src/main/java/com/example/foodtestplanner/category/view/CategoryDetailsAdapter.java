package com.example.foodtestplanner.category.view;

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
import com.example.foodtestplanner.model.dto.ListDetails;

import java.util.List;

public class CategoryDetailsAdapter extends RecyclerView.Adapter<CategoryDetailsAdapter.ViewHolder> {
    private Context context;
    private List<ListDetails> categoryDetailsList;
    private OnCategoryDetailsClickListener categoryDetailsClickListener;

    public CategoryDetailsAdapter(Context context, List<ListDetails> categoryDetailsList, OnCategoryDetailsClickListener categoryDetailsClickListener){
        this.context = context;
        this.categoryDetailsList = categoryDetailsList;
        this.categoryDetailsClickListener = categoryDetailsClickListener;
    }
    public void setList(List<ListDetails> categoryDetailsList) {
        this.categoryDetailsList = categoryDetailsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_details_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListDetails categoriesDetailsItem = categoryDetailsList.get(position);
        holder.txtCategoryItemName.setText(categoriesDetailsItem.getStrMeal());
        Glide.with(context).load(categoriesDetailsItem.getStrMealThumb()).into(holder.categoryItemImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryDetailsClickListener != null) {
                    categoryDetailsClickListener.onCategoryClick(categoriesDetailsItem);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryDetailsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryItemImage;
        private TextView txtCategoryItemName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryItemImage = itemView.findViewById(R.id.categoryDetailsItemImage);
            txtCategoryItemName = itemView.findViewById(R.id.txtCategoryDetailsItemName);
        }
    }


}
