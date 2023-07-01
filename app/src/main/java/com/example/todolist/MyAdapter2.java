package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.CategoryPackage.Category;
import com.example.todolist.ItemPackage.Item;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2> {

    Context context;
    List<Category> itemList;

    public MyAdapter2(Context context, List<Category> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder2(LayoutInflater.from(context).inflate(R.layout.category_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        holder.name.setText(itemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
