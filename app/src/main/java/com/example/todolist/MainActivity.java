package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.todolist.CategoryPackage.Category;
import com.example.todolist.CategoryPackage.CategoryViewModel;
import com.example.todolist.ItemPackage.Item;
import com.example.todolist.ItemPackage.ItemViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    Settings settings;
    AlarmManager alarmManager;
    List<PendingIntent> allIntents = new ArrayList<>();
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(ItemViewModel.class);

        viewPager = findViewById(R.id.viewpager);
        PageAdapter pageAdapter = new PageAdapter(this);
        viewPager.setAdapter(pageAdapter);
        getNewestSettings();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ToDoChannel";
            String description = "Channel for todo reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("todolist", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Bundle b = getIntent().getExtras();
        String s= null; // or other values
        if(b != null){
            Gson gson = new Gson();
            s = b.getString("cat");
            if(s.equals("change"))
                ManageNotifications();
            //category = b.getInt("key");
        }

        CategoryViewModel categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);

        categoryViewModel.getAllStudentsFromVm().observe(this, students ->
        {
            if (students.isEmpty()) {
                Category item = new Category("Other");
                categoryViewModel.insertStudent(item);
            }
        });
    }

    public void ManageNotifications(){
        //Cancel all previously made notifications
        for (PendingIntent thing : allIntents) {
            if(thing!=null) {
                alarmManager.cancel(thing);
            }
            //thing.cancel();
        }
        allIntents = new ArrayList<PendingIntent>();

        //Make new notifiactions
        getNewestSettings();
        itemViewModel.getAllStudentsFromVm().observe(this, students ->
        {
            if (students != null && !students.isEmpty()) {
                List<Item> itemki = new ArrayList<>();
                //zostana tylo te ktore nie sa skonczone i nie maja daty wczesniejszej niz obecna
                for(Item item : students){
                    Date d = new Date();
                    long interval = 60L *1000L*settings.getMinutes();
                    if(item.getNotify() && (item.getEndDate().getTime()-interval > d.getTime())){
                        if(!item.getFinished()){
                            itemki.add(item);
                        }
                    }
                }
                for(Item item : itemki){
                    Intent intent = new Intent(this, ReminderBroadcast.class);
                    Gson gson = new Gson();
                    String json = gson.toJson(item);
                    intent.putExtra("cat", json);
                    PendingIntent pendingIntent = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                        pendingIntent = PendingIntent.getBroadcast(this, item.getId(), intent, PendingIntent.FLAG_IMMUTABLE);
                    }
                    else
                    {
                        pendingIntent = PendingIntent.getBroadcast(this, item.getId(), intent, 0);
                    }

                    long currentTime = System.currentTimeMillis();

                    long interval = 60L *1000L*settings.getMinutes();

                    alarmManager.set(AlarmManager.RTC_WAKEUP, item.getEndDate().getTime() - interval, pendingIntent);
                    allIntents.add(pendingIntent);
                }
            }
        });
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