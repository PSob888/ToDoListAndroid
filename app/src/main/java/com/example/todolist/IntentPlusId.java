package com.example.todolist;

import android.app.PendingIntent;

public class IntentPlusId {
    int id;
    PendingIntent pendingIntent;

    public IntentPlusId(int id, PendingIntent pendingIntent) {
        this.id = id;
        this.pendingIntent = pendingIntent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }
}
