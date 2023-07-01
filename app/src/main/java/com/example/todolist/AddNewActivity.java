package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class AddNewActivity extends AppCompatActivity {

    Spinner spinner;
    EditText textTitle;
    EditText textDescription;
    TimePicker timePicker;
    DatePicker datePicker;
    CheckBox boxStatus;
    CheckBox boxNotify;
    Button buttonSave;
    Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        spinner = findViewById(R.id.spinnerCategoriesAdd);
        textTitle = findViewById(R.id.editTextTitle);
    }
}