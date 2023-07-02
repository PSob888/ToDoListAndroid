package com.example.todolist.CategoryPackage;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private final LiveData<List<Category>> listLiveData;

    public CategoryViewModel(Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        listLiveData = categoryRepository.getAllStudents();
    }

    public LiveData<List<Category>> getAllStudentsFromVm() {
        return listLiveData;
    }

    public void insertStudent(Category category) {
        categoryRepository.insertCategory(category);
    }

    public void updateCategory(Category category){
        categoryRepository.updateCategory(category);
    }

    public void deleteCategory(Category category){
        categoryRepository.deleteCategory(category);
    }
}
