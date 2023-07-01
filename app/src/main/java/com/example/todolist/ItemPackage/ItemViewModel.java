package com.example.todolist.ItemPackage;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository itemRepository;
    private final LiveData<List<Item>> listLiveData;

    public ItemViewModel(Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
        listLiveData = itemRepository.getAllItems();
    }

    public LiveData<List<Item>> getAllStudentsFromVm() {
        return listLiveData;
    }

    public void insertStudent(Item item) {
        itemRepository.insertItem(item);
    }
}
