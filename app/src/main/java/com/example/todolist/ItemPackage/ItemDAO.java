package com.example.todolist.ItemPackage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert2(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * from item_table ORDER By endDate Asc")
    LiveData<List<Item>> getItems();

    @Query("SELECT * from item_table WHERE category=:category ORDER By endDate Asc")
    LiveData<List<Item>> getItemsCatSearch(String category);

    @Query("SELECT * FROM item_table")
    List<Item> getAll();

    @Query("SELECT * FROM item_table WHERE id = :itemId LIMIT 1")
    LiveData<Item> getItemById(int itemId);

}
