package com.example.todolist;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.todolist.CategoryPackage.Category;
import com.example.todolist.CategoryPackage.CategoryViewModel;
import com.example.todolist.ItemPackage.Item;
import com.example.todolist.ItemPackage.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner dropdown;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    private ItemViewModel itemViewModel;
    private CategoryViewModel categoryViewModel;
    String searchCat;
    boolean hideFinished = false;
    ImageButton buttonHide;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchCat = "all";
        initAll(view);
        //notificationCreater();
    }

    @Override
    public void onResume(){
        super.onResume();
        searchCat = "all";
        initAll(this.getView());
        //notificationCreater();
    }

    private void initAll(@NonNull View view) {
        MainActivity mainActivity = (MainActivity) getActivity();

        dropdown = view.findViewById(R.id.spinner2);
        initspinnerfooter();

        //Dodac pobieranie itemow z bazy tutaj
        List<Item> items = new ArrayList<Item>();

        itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(mainActivity.getApplication()).create(ItemViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Searcher(view, mainActivity);

        floatingActionButton = view.findViewById(R.id.floatingButtonList);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(mainActivity, AddNewActivity.class);
                mainActivity.startActivity(myIntent);
            }
        });
        buttonHide = view.findViewById(R.id.ButtonHide);
        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hideFinished){
                    hideFinished = false;
                    buttonHide.setImageResource(R.drawable.baseline_access_time_filled_24);
                    Searcher(view, mainActivity);
                }
                else{
                    hideFinished = true;
                    buttonHide.setImageResource(R.drawable.baseline_access_time_24);
                    Searcher(view, mainActivity);
                }
            }
        });
    }

    private void initspinnerfooter() {
        List<String> items = new ArrayList<>();
        items.add("all");

        MainActivity mainActivity = (MainActivity) getActivity();
        categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(mainActivity.getApplication()).create(CategoryViewModel.class);

        categoryViewModel.getAllStudentsFromVm().observe(mainActivity, students ->
        {
            if (students != null && !students.isEmpty()) {
                for(int i=0;i<students.size();i++){
                    items.add(students.get(i).getName());
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                searchCat = dropdown.getSelectedItem().toString();
                Searcher(view, mainActivity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void notificationCreater(){
        MainActivity mainActivity = (MainActivity) getActivity();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ToDoChannel";
            String description = "Channel for todo reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("todolist", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = mainActivity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(mainActivity, ReminderBroadcast.class);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(mainActivity, 0, intent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getBroadcast(mainActivity, 0, intent, 0);
        }
        AlarmManager alarmManager = (AlarmManager) mainActivity.getSystemService(ALARM_SERVICE);

        long currentTime = System.currentTimeMillis();

        long tenSecondsInMilis = 1000*10;

        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime + tenSecondsInMilis, pendingIntent);
    }

    private void Searcher(View view, MainActivity mainActivity) {
        if(searchCat.equals("all")){
            itemViewModel.getAllStudentsFromVm().observe(mainActivity, students ->
            {
                if (students != null && !students.isEmpty()) {
                    List<Item> itemki = new ArrayList<>();
                    if(hideFinished){
                        for(Item item : students){
                            if(!item.getFinished()){
                                itemki.add(item);
                            }
                        }
                    }
                    else{
                        itemki = students;
                    }

                    MyAdapter adapter = new MyAdapter(view.getContext(), (ArrayList<Item>) itemki);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(view.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                @Override public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(view.getContext(), EditItemActivity.class);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(students.get(position));
                                    Bundle b = new Bundle();
                                    b.putString("cat", json); //Your id
                                    intent.putExtras(b); //Put your id to your next Intent
                                    startActivity(intent);
                                }

                                @Override public void onLongItemClick(View view, int position) {
                                    // do whatever
                                }
                            })
                    );
                }
            });
        }
        else{
            itemViewModel.getAllItemsByCat(searchCat).observe(mainActivity, students ->
            {
                if (students != null && !students.isEmpty()) {

                    List<Item> itemki = new ArrayList<>();
                    if(hideFinished){
                        for(Item item : students){
                            if(!item.getFinished()){
                                itemki.add(item);
                            }
                        }
                    }
                    else{
                        itemki = students;
                    }

                    MyAdapter adapter = new MyAdapter(view.getContext(), (ArrayList<Item>) itemki);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(view.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                @Override public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(view.getContext(), EditItemActivity.class);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(students.get(position));
                                    Bundle b = new Bundle();
                                    b.putString("cat", json); //Your id
                                    intent.putExtras(b); //Put your id to your next Intent
                                    startActivity(intent);
                                }

                                @Override public void onLongItemClick(View view, int position) {
                                    // do whatever
                                }
                            })
                    );
                }
            });
        }
    }
}