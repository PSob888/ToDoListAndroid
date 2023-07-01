package com.example.todolist.ItemPackage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {
    ItemDatabase itemDatabase;
    ItemDAO ItemDao;
    private LiveData<List<Item>> listItems;

    public ItemRepository(Application application) {
        itemDatabase = ItemDatabase.getDatabase(application);
        ItemDao = itemDatabase.itemDAO();
        listItems = ItemDao.getItems();
    }

    public void insertItem(Item Item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> ItemDao.insert(Item));
    }

    public LiveData<List<Item>> getAllItems() {
        return listItems;
    }
}
