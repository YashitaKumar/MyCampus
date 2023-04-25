package com.example.mycampus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycampus.Models.DhabbaItemModel;
import com.example.mycampus.R;

import java.util.Collections;
import java.util.List;

public class DhabbaItemAdapter extends RecyclerView.Adapter<DhabbaItemAdapter.MyViewHolder> {

    private List<DhabbaItemModel> dhabbaItemModelList;

    public DhabbaItemAdapter(List<DhabbaItemModel> dhabbaItemModelList) {
        this.dhabbaItemModelList = dhabbaItemModelList;
        this.dhabbaItemModelList.removeAll(Collections.singleton(null));
    }

    public DhabbaItemAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dhabbaitem,parent,false);
        return new DhabbaItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DhabbaItemModel dhabbaItemModel=dhabbaItemModelList.get(position);
        holder.item.setText(dhabbaItemModel.getItem());
        String priceval;
        priceval=Integer.toString(dhabbaItemModel.getPrice());
        holder.price.setText(priceval);


    }

    @Override
    public int getItemCount() {
        if(dhabbaItemModelList!=null)
        {
            return dhabbaItemModelList.size();
        }
        else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView item,price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.item);
            price=itemView.findViewById(R.id.price);
        }
    }
}
