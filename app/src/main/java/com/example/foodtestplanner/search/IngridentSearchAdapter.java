package com.example.foodtestplanner.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodtestplanner.R;
import com.example.foodtestplanner.model.dto.IngridentItem;

import java.util.ArrayList;
import java.util.List;

public class IngridentSearchAdapter extends RecyclerView.Adapter<IngridentSearchAdapter.ViewHolder> {
    private List<IngridentItem> ingridentItemList;
    private Context context;
    private OnClickIngridentListner onClickIngridentListner;

    public IngridentSearchAdapter(List<IngridentItem> ingridentItemList, Context context, OnClickIngridentListner onClickIngridentListner) {
        this.ingridentItemList = ingridentItemList != null ? ingridentItemList : new ArrayList<>();
        this.context = context;
        this.onClickIngridentListner = onClickIngridentListner;
    }
    public IngridentSearchAdapter(Context context, ArrayList<Object> objects) {

    }

    public void setIngridentItemList(List<IngridentItem> ingridentItemList) {
        this.ingridentItemList = ingridentItemList != null ? ingridentItemList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.srarch_by_ingrident, parent, false);
        return new ViewHolder(view, onClickIngridentListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngridentItem ingridentItem = ingridentItemList.get(position);
        holder.text.setText(ingridentItem.getIngreidant());
        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/" + ingridentItem.getIngreidant() + "-Small.png")
                .into(holder.imag);
    }

    @Override
    public int getItemCount() {
        return ingridentItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        ImageView imag;
        OnClickIngridentListner onClickIngridentListner;

        public ViewHolder(@NonNull View itemView, OnClickIngridentListner onClickIngridentListner) {
            super(itemView);
            text = itemView.findViewById(R.id.textid);
            imag = itemView.findViewById(R.id.imageView2);
            this.onClickIngridentListner = onClickIngridentListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickIngridentListner.onIngredientClick(ingridentItemList.get(getAdapterPosition()));
        }
    }
}
