package com.example.todolist.ItemPackage;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Future;

public class ItemRepository {
    ItemDatabase itemDatabase;
    ItemDAO ItemDao;
    private LiveData<List<Item>> listItems;

    public ItemRepository(Application application) {
        itemDatabase = ItemDatabase.getDatabase(application);
        ItemDao = itemDatabase.itemDAO();
        listItems = ItemDao.getItems();
    }

    public LiveData<Long> insertItem2(Item item) {
        MutableLiveData<Long> insertedItemId = new MutableLiveData<>();
        ItemDatabase.databaseWriteExecutor.execute(() -> {
            long taskId = ItemDao.insert2(item);
            insertedItemId.postValue(taskId);
        });
        return insertedItemId;
    }

    public void deleteItem(Item item){
        ItemDatabase.databaseWriteExecutor.submit(() -> ItemDao.delete(item));
    }

    public void updateItem(Item item){
        ItemDatabase.databaseWriteExecutor.submit(() -> ItemDao.update(item));
    }

    public void insertItem(Item Item) {
        ItemDatabase.databaseWriteExecutor.submit(() -> ItemDao.insert(Item));
    }

    public LiveData<List<Item>> getAllItems() {
        listItems = ItemDao.getItems();
        return listItems;
    }

    public LiveData<List<Item>> getAllItemsByCat(String cat) {
        listItems = ItemDao.getItemsCatSearch(cat);
        return listItems;
    }

    public LiveData<Item> getItemById(int itemId) {
        return ItemDao.getItemById(itemId);
    }
}
