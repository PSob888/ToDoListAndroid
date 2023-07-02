package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.todolist.CategoryPackage.Category;
import com.example.todolist.CategoryPackage.CategoryViewModel;
import com.example.todolist.ItemPackage.Item;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EditItemActivity extends AppCompatActivity {

    Spinner spinner;
    EditText textTitle;
    EditText textDescription;
    TimePicker timePicker;
    DatePicker datePicker;
    CheckBox boxStatus;
    CheckBox boxNotify;
    Button buttonSave;
    Button buttonCancel;
    private CategoryViewModel categoryViewModel;
    Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Bundle b = getIntent().getExtras();
        item = null; // or other values
        if(b != null){
            Gson gson = new Gson();
            String s = b.getString("cat");
            item = gson.fromJson(s, Item.class);
            //category = b.getInt("key");
        }

        spinner = findViewById(R.id.spinnerCategoriesAdd);
        textTitle = findViewById(R.id.editTextTitle);
        textDescription = findViewById(R.id.editTextDescription);
        timePicker = findViewById(R.id.timePicker1);
        datePicker = findViewById(R.id.datePicker1);
        boxStatus = findViewById(R.id.checkBoxStatus);
        boxNotify = findViewById(R.id.checkBoxNotification);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);

        Date data = item.getEndDate();
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(data);   // assigns calendar to given date
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        int minute = calendar.get(Calendar.MINUTE);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        timePicker.setHour(hour);
        timePicker.setMinute(minute);
        datePicker.init(year, month, day, null);

        textTitle.setText(item.getTitle());
        textDescription.setText(item.getDescription());
        boxStatus.setChecked(item.getFinished());
        boxNotify.setChecked(item.getNotify());
        initspinnerfooter();
    }

    private void initspinnerfooter() {
        List<String> items = new ArrayList<>();

        categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);

        categoryViewModel.getAllStudentsFromVm().observe(this, students ->
        {
            if (students != null && !students.isEmpty()) {
                for(int i=0;i<students.size();i++){
                    items.add(students.get(i).getName());
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
            spinner.setAdapter(adapter);
            spinner.setSelection(adapter.getPosition(item.getCategory()));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });
    }
}