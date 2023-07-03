package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        PageAdapter pageAdapter = new PageAdapter(this);
        viewPager.setAdapter(pageAdapter);
        getNewestSettings();
    }

    public void getNewestSettings(){
        SharedPreferences mPrefs = this.getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("settings", "");
        Settings settings1 = gson.fromJson(json, Settings.class);

        if(settings1 != null){
            settings = new Settings(settings1.getMinutes());
        }
        else{
            settings = new Settings(1);
        }
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