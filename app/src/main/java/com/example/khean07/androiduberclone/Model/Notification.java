package com.example.khean07.androiduberclone.Model;

/**
 * Created by Khean07 on 4/14/2018.
 */

public class Notification {

    public String title;
    public String body;

    public Notification() {
    }

    public Notification(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
