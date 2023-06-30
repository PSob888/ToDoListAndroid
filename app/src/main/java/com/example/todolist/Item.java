package com.example.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "item_table")
public class Item {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String description;
    Date createDate;
    Date endDate;
    Boolean isFinished;
    Boolean notify;
    Boolean hasAddons;
    int categoryId;

    public Item(int categoryId, int id, String title, String description, Date createDate, Date endDate, Boolean isFinished, Boolean notify, Boolean hasAddons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.endDate = endDate;
        this.isFinished = isFinished;
        this.notify = notify;
        this.categoryId = categoryId;
        this.hasAddons = hasAddons;
    }

    public Boolean getHasAddons() {
        return hasAddons;
    }

    public void setHasAddons(Boolean hasAddons) {
        this.hasAddons = hasAddons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Boolean getNotify() {
        return notify;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
