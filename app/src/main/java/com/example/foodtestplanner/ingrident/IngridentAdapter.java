package com.example.foodtestplanner.ingrident;

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
import com.example.foodtestplanner.country.view.AreaAdaptor;
import com.example.foodtestplanner.country.view.OnClickAreaListner;
import com.example.foodtestplanner.model.dto.ListDetails;

import java.util.List;

public class IngridentAdapter extends RecyclerView.Adapter<IngridentAdapter.ViewHolder> {
    private List<ListDetails> listDetails;
    private OnClickIngrident onClickIngrident;
    private Context context;

    public IngridentAdapter(List<ListDetails> listDetails, OnClickIngrident onClickIngrident, Context context) {
        this.listDetails = listDetails;
        this.onClickIngrident = onClickIngrident;
        this.context = context;
    }

    public void setListDetails(List<ListDetails> listDetails) {
        this.listDetails = listDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.ingridient_layout,parent,false);
 ViewHolder viewHolder=new ViewHolder(view);
        context= parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListDetails listDetailsingrident=listDetails.get(position);
        holder.text.setText(listDetailsingrident.getStrMeal());
        Glide.with(context).load(listDetailsingrident.getStrMealThumb()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickIngrident!=null){
                    onClickIngrident.onIngredientClick(listDetailsingrident);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDetails.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textviewArea1);
            imageView = itemView.findViewById(R.id.imag2);

        }

    }
}
