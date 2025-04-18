package com.managerapp.personnelmanagerapp.presentation.adapters;

public class TimelineModel {
    private String date;
    private String title;

    public TimelineModel(String date, String title) {
        this.date = date;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }
}