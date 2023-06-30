package com.example.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "addon_table")
public class Addon {
    @PrimaryKey(autoGenerate = true)
    int id;
    int itemId;
    String URI;

    public Addon(int itemId, int id, String URI) {
        this.id = id;
        this.URI = URI;
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
