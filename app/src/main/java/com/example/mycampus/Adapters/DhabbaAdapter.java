package com.example.mycampus.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycampus.Models.DhabbaItemModel;
import com.example.mycampus.Models.DhabbaModel;
import com.example.mycampus.R;

import java.util.List;

public class DhabbaAdapter extends RecyclerView.Adapter<DhabbaAdapter.MyViewHolder> {
    private List<DhabbaModel> list;

    public void setList(List<DhabbaModel> list) {
        this.list = list;
    }

    public DhabbaAdapter() {
    }

    public DhabbaAdapter(List<DhabbaModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dhabba,parent,false);
        return new DhabbaAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DhabbaModel dhabbaModel = list.get(position);
        holder.itemName.setText(dhabbaModel.getItemName());
        Glide.with(holder.itemView.getContext()).load(dhabbaModel.getItemPic()).into(holder.itemPic);
        holder.items.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        DhabbaItemAdapter dhabbaItemAdapter= new DhabbaItemAdapter(dhabbaModel.getDhabbaItemModelList());
        holder.items.setAdapter(dhabbaItemAdapter);
        dhabbaItemAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if(list!=null)
        {
            return list.size();
        }
        else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        ImageView itemPic;
        RecyclerView items;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPic = itemView.findViewById(R.id.itemPic);
            items=itemView.findViewById(R.id.items);
        }
    }
}
