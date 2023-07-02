package com.example.todolist.CategoryPackage;

import android.app.Application;
import android.util.Log;

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

    public void updateCategory(Category category){
        CategoryRoomDatabase.databaseWriteExecutor.execute(() -> categoryDAO.update(category));
    }

    public void deleteCategory(Category category){
        CategoryRoomDatabase.databaseWriteExecutor.execute(() -> categoryDAO.delete(category));
    }
}
