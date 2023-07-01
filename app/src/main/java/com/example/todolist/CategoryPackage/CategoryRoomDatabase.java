package com.example.todolist.CategoryPackage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class CategoryRoomDatabase extends RoomDatabase {
    public abstract CategoryDAO categoryDAO();

    private static volatile CategoryRoomDatabase categoryRoomDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CategoryRoomDatabase getDatabase(final Context context) {
        if (categoryRoomDatabase == null) {
            synchronized (CategoryRoomDatabase.class) {
                if (categoryRoomDatabase == null) {
                    categoryRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    CategoryRoomDatabase.class, "category_database")
                            .build();
                }
            }
        }
        return categoryRoomDatabase;
    }
}
