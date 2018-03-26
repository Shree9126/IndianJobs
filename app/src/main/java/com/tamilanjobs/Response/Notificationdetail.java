package com.tamilanjobs.Response;

/**
 * Created by mahei on 2/12/2018.
 */

public class Notificationdetail {

    String notify_id;


    String notify_title;
    String notify_short_desc;
    String notify_ntype_id;

    public String getNotify_ntype_id() {
        return notify_ntype_id;
    }

    public void setNotify_ntype_id(String notify_ntype_id) {
        this.notify_ntype_id = notify_ntype_id;
    }

    public String getNotify_jpost_id() {
        return notify_jpost_id;
    }

    public void setNotify_jpost_id(String notify_jpost_id) {
        this.notify_jpost_id = notify_jpost_id;
    }

    String notify_jpost_id;

    public String getNotify_short_desc() {
        return notify_short_desc;
    }

    public void setNotify_short_desc(String notify_short_desc) {
        this.notify_short_desc = notify_short_desc;
    }

    String notify_message;
    String notify_created_on;

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }


    public String getNotify_title() {
        return notify_title;
    }

    public void setNotify_title(String notify_title) {
        this.notify_title = notify_title;
    }

    public String getNotify_message() {
        return notify_message;
    }

    public void setNotify_message(String notify_message) {
        this.notify_message = notify_message;
    }

    public String getNotify_created_on() {
        return notify_created_on;
    }

    public void setNotify_created_on(String notify_created_on) {
        this.notify_created_on = notify_created_on;
    }
}
