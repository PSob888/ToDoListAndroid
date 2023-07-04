package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolist.CategoryPackage.Category;
import com.example.todolist.CategoryPackage.CategoryViewModel;
import com.example.todolist.ItemPackage.Item;
import com.example.todolist.ItemPackage.ItemViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EditCategoryActivity extends AppCompatActivity {

    EditText editText;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        Bundle b = getIntent().getExtras();
        category = null; // or other values
        if(b != null){
            Gson gson = new Gson();
            String s = b.getString("cat");
            category = gson.fromJson(s, Category.class);
            //category = b.getInt("key");
        }

        editText = findViewById(R.id.editTextCategoryEdit);
        editText.setText(category.getName());
    }

    public void onClickCancel(View v){
        //dodac zeby przechodzilo na strone z kategoriami
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }

    public void onClickSave(View v){
        //Dodac tutaj logike zapisu do bazy

        if(category.getName().equals("Other")){
            Toast.makeText(this , "You cant change category Other", Toast.LENGTH_LONG).show();
            return;
        }

        ItemViewModel itemViewModel2 = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ItemViewModel.class);
        itemViewModel2.getAllStudentsFromVm().observe(this, students ->
        {
            if (students != null && !students.isEmpty()) {
                for(Item item : students){
                    if(item.getCategory().equals(category.getName())){
                        Toast.makeText(this , "You cant change category with tasks", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            String name = editText.getText().toString();

            CategoryViewModel itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);

            category.setName(name);

            //itemViewModel.insertStudent(item);
            itemViewModel.updateCategory(category);

            Intent myIntent = new Intent(this, MainActivity.class);
            this.startActivity(myIntent);
        });


    }

    public void onClickDelete(View v){
        //Dodac tutaj logike zapisu do bazy
        if(category.getName().equals("Other")){
            Toast.makeText(this , "You cant change category Other", Toast.LENGTH_LONG).show();
            return;
        }

        //sprawdzanie czy jakis item nie ma kategorii
        ItemViewModel itemViewModel2 = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ItemViewModel.class);
        itemViewModel2.getAllStudentsFromVm().observe(this, students ->
        {
            if (students != null && !students.isEmpty()) {
                for(Item item : students){
                    if(item.getCategory().equals(category.getName())){
                        Toast.makeText(this , "You cant delete category with tasks", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to remove this category?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CategoryViewModel itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);

                    itemViewModel.deleteCategory(category);

                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(myIntent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }
}