package com.example.todolist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.todolist.ItemPackage.Item;
import com.example.todolist.ItemPackage.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        MainActivity mainActivity = (MainActivity) getActivity();

        dropdown = view.findViewById(R.id.spinner2);
        initspinnerfooter();

        //Dodac pobieranie itemow z bazy tutaj
        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Test1", "Testowy opis 12345", new Date()));
        items.add(new Item("Test2", "Testowy opis bardzo bardzo bardzo bardzo bardzo bardzo bardzo bardzo bardzo bardzo dlugi", new Date()));
        items.add(new Item("Test3", "Testowy opis 1337", new Date()));

        itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(mainActivity.getApplication()).create(ItemViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        itemViewModel.getAllStudentsFromVm().observe(mainActivity, students ->
        {
            if (students != null && !students.isEmpty()) {
                MyAdapter adapter = new MyAdapter(view.getContext(), (ArrayList<Item>) students);
                recyclerView.setAdapter(adapter);
            }
        });
        //recyclerView.setAdapter(new MyAdapter(view.getContext(), items));

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


    }

    private void initspinnerfooter() {
        String[] items = new String[]{
                "tutaj", "beda", "kategorie", "kiedys",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}