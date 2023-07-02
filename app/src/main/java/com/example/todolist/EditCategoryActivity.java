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
import com.google.gson.Gson;

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

        String name = editText.getText().toString();

        CategoryViewModel itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);

        category.setName(name);

        //itemViewModel.insertStudent(item);
        itemViewModel.updateCategory(category);

        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }

    public void onClickDelete(View v){
        //Dodac tutaj logike zapisu do bazy

        if(category.getName().equals("Other")){
            Toast.makeText(this , "You cant change category Other", Toast.LENGTH_LONG).show();
            return;
        }

        CategoryViewModel itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);

        //itemViewModel.insertStudent(item);
        itemViewModel.deleteCategory(category);

        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }
}