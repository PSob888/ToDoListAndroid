package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.CategoryPackage.Category;
import com.example.todolist.CategoryPackage.CategoryViewModel;
import com.example.todolist.ItemPackage.Item;
import com.example.todolist.ItemPackage.ItemViewModel;

import java.util.Calendar;
import java.util.Date;

public class AddCategoryActivity extends AppCompatActivity {

    EditText textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        textTitle = findViewById(R.id.editCategoryCat);
    }

    public void onClickCancel(View v){
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }

    public void onClickSave(View v){
        //Dodac tutaj logike zapisu do bazy

        String name = textTitle.getText().toString();


        //Dopisac checker czy juz takie jest
        if(name.equals("Other")){
            Toast.makeText(this , "You make category Other", Toast.LENGTH_LONG).show();
            return;
        }

        CategoryViewModel itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);

        Category item = new Category(name);

        itemViewModel.insertStudent(item);

        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }
}