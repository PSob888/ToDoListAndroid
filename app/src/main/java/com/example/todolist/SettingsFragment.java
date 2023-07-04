package com.example.todolist;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner dropdown;
    Settings settings;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dropdown = view.findViewById(R.id.spinner);
        getNewestSettings();
        initspinnerfooter();
    }

    public void getNewestSettings(){
        MainActivity mainActivity = (MainActivity) getActivity() ;
        SharedPreferences mPrefs = mainActivity.getPreferences(MODE_PRIVATE);
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

    public void saveSettings(){
        MainActivity mainActivity = (MainActivity) getActivity() ;
        SharedPreferences mPrefs = mainActivity.getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(settings, Settings.class);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("settings", json);
        prefsEditor.commit();
    }

    private void initspinnerfooter() {

        List<String> items = new ArrayList<>();
        items.add("1min");
        items.add("5min");
        items.add("10min");
        items.add("30min");

        String set = settings.getMinutes() + "min";
        int index = items.indexOf(set);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(index);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                String selected = dropdown.getSelectedItem().toString();
                if(selected.equals("1min")){
                    settings = new Settings(1);
                }
                if(selected.equals("5min")){
                    settings = new Settings(5);
                }
                if(selected.equals("10min")){
                    settings = new Settings(10);
                }
                if(selected.equals("30min")){
                    settings = new Settings(30);
                }
                saveSettings();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.ManageNotifications();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}