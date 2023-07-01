package com.example.todolist.CategoryPackage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryRepository {
    CategoryRoomDatabase categoryRoomDatabase;
    CategoryDAO categoryDAO;
    private LiveData<List<Category>> listCategories;

    public CategoryRepository(Application application) {
        categoryRoomDatabase = CategoryRoomDatabase.getDatabase(application);
        categoryDAO = categoryRoomDatabase.categoryDAO();
        listCategories = categoryDAO.getStudent();
    }

    public void insertCategory(Category category) {
        CategoryRoomDatabase.databaseWriteExecutor.execute(() -> categoryDAO.insert(category));
    }

    public LiveData<List<Category>> getAllStudents() {
        return listCategories;
    }
}
