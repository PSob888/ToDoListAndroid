package com.example.todolist.ItemPackage;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.todolist.DateConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "item_table")
@TypeConverters(DateConverter.class)
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
    String category;
    List<String> attachmentPaths = new ArrayList<>();

    public Item(String category, String title, String description, Date createDate, Date endDate, Boolean isFinished, Boolean notify, Boolean hasAddons) {
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.endDate = endDate;
        this.isFinished = isFinished;
        this.notify = notify;
        this.category = category;
        this.hasAddons = hasAddons;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getAttachmentPaths() {
        return attachmentPaths;
    }

    public void setAttachmentPaths(List<String> attachmentPaths) {
        this.attachmentPaths = attachmentPaths;
    }

    @Ignore
    public Item(String title, String description, Date endDate){
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.hasAddons=false;
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

    public String getCategory() {
        return category;
    }

    public void setCategoryId(String category) {
        this.category = category;
    }
}
