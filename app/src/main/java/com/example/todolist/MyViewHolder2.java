package com.example.todolist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder2 extends RecyclerView.ViewHolder {

    TextView name;

    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.textCategoryName);
    }
}
