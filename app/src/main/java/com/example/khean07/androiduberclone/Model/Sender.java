package com.example.khean07.androiduberclone.Model;

/**
 * Created by Khean07 on 4/14/2018.
 */

public class Sender {

    public Notification notification;
    public String to;

    public Sender() {

    }

    public Sender(Notification notification, String to) {
        this.notification = notification;
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
