package com.example.todolist.ItemPackage;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository itemRepository;
    private LiveData<List<Item>> listLiveData;

    public ItemViewModel(Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
        listLiveData = itemRepository.getAllItems();
    }

    public void deleteItem(Item item){
        itemRepository.deleteItem(item);
    }

    public void updateItem(Item item){
        itemRepository.updateItem(item);
    }

    public LiveData<List<Item>> getAllStudentsFromVm() {
        listLiveData = itemRepository.getAllItems();
        return listLiveData;
    }

    public LiveData<List<Item>> getAllItemsByCat(String string) {
        listLiveData = itemRepository.getAllItemsByCat(string);
        return listLiveData;
    }

    public void insertStudent(Item item) {
        itemRepository.insertItem(item);
    }
}

