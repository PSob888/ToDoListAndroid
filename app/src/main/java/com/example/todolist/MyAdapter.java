package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.ItemPackage.Item;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Item> itemList;

    public MyAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(itemList.get(position).getTitle());
        holder.description.setText(itemList.get(position).getDescription());
        holder.date.setText(itemList.get(position).getEndDate().toString());
        holder.category.setText(itemList.get(position).getCategory());
        if(itemList.get(position).getHasAddons()){
            holder.imageView.setVisibility(View.VISIBLE);
        }
        else{
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        if(itemList.get(position).getFinished()){
            holder.isFinished.setText("Finished YES");
        }
        else{
            holder.isFinished.setText("Finished NO");
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
