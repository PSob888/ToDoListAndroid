package com.example.todolist.ItemPackage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {
    public abstract ItemDAO itemDAO();

    private static volatile ItemDatabase itemDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ItemDatabase getDatabase(final Context context) {
        if (itemDatabase == null) {
            synchronized (ItemDatabase.class) {
                if (itemDatabase == null) {
                    itemDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    ItemDatabase.class, "item_database")
                            .build();
                }
            }
        }
        return itemDatabase;
    }
}
