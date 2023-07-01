package com.example.todolist.CategoryPackage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Update
    void update(Category category);

    @Query("SELECT * from category_table ORDER By id Asc")
    LiveData<List<Category>> getStudent();

    @Query("DELETE from category_table")
    void deleteAll();
}
