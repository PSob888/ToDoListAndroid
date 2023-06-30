package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        PageAdapter pageAdapter = new PageAdapter(this);
        viewPager.setAdapter(pageAdapter);
    }

    public void onClickList(View v){
        viewPager.setCurrentItem(0);
    }

    public void onClickCategories(View v){
        viewPager.setCurrentItem(1);
    }

    public void onClickSettings(View v){
        viewPager.setCurrentItem(2);
    }
}