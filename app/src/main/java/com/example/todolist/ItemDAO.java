package com.example.todolist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

@Dao
public interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Update
    void update(Item item);


}
