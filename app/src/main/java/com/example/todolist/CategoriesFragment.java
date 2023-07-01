package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolist.CategoryPackage.Category;
import com.example.todolist.CategoryPackage.CategoryViewModel;
import com.example.todolist.ItemPackage.Item;
import com.example.todolist.ItemPackage.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    private CategoryViewModel categoryViewModel;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
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
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAll(view);
    }

    @Override
    public void onResume(){
        super.onResume();
        initAll(this.getView());
    }

    private void initAll(@NonNull View view) {
        MainActivity mainActivity = (MainActivity) getActivity();

        categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(mainActivity.getApplication()).create(CategoryViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        categoryViewModel.getAllStudentsFromVm().observe(mainActivity, students ->
        {
            if (students != null && !students.isEmpty()) {
                MyAdapter2 adapter = new MyAdapter2(view.getContext(), (ArrayList<Category>) students);
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
}