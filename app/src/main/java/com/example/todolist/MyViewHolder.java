package com.example.todolist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView title;
    TextView description;
    TextView date;
    TextView category;
    TextView isFinished;
    TextView notification;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageAddon);
        title = itemView.findViewById(R.id.textTitle);
        description = itemView.findViewById(R.id.textDescription);
        date = itemView.findViewById(R.id.textDate);
        category = itemView.findViewById(R.id.textCategory);
        isFinished = itemView.findViewById(R.id.textisFinished);
        notification = itemView.findViewById(R.id.textNotification);
    }
}
