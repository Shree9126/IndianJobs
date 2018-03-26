package com.tamilanjobs.Response;

import com.tamilanjobs.Filterloaddatelist;

import java.util.ArrayList;

/**
 * Created by mahei on 2/8/2018.
 */

public class BasicResponse {

    int success;
    String message;
    String notificationcount;

    public String getNotificationcount() {
        return notificationcount;
    }

    public void setNotificationcount(String notificationcount) {
        this.notificationcount = notificationcount;
    }

    ArrayList<Joblistdetail> job_list;
    ArrayList<Joblistfullviewdetail> job_listview;
    ArrayList<Notificationdetail> notification_list;
    ArrayList<Settingsdetail> settings_list;
    ArrayList<Searchjobdetail> searchjob_list;
    ArrayList<Notificatiomlist> motification_general_list;

    public ArrayList<Notificatiomlist> getMotification_general_list() {
        return motification_general_list;
    }

    public void setMotification_general_list(ArrayList<Notificatiomlist> motification_general_list) {
        this.motification_general_list = motification_general_list;
    }

    public ArrayList<Searchjobdetail> getSearchjob_list() {
        return searchjob_list;
    }

    public void setSearchjob_list(ArrayList<Searchjobdetail> searchjob_list) {
        this.searchjob_list = searchjob_list;
    }
    //filterload

    ArrayList<Filterloaddatelist> education_qualification;
    ArrayList<Filterloaddatelist> job_category;

    public ArrayList<Filterloaddatelist> getEducation_qualification() {
        return education_qualification;
    }

    public void setEducation_qualification(ArrayList<Filterloaddatelist> education_qualification) {
        this.education_qualification = education_qualification;
    }

    public ArrayList<Filterloaddatelist> getJob_category() {
        return job_category;
    }

    public void setJob_category(ArrayList<Filterloaddatelist> job_category) {
        this.job_category = job_category;
    }

    public ArrayList<Settingsdetail> getSettings_list() {
        return settings_list;
    }

    public void setSettings_list(ArrayList<Settingsdetail> settings_list) {
        this.settings_list = settings_list;
    }

    public ArrayList<Notificationdetail> getNotification_list() {
        return notification_list;
    }

    public void setNotification_list(ArrayList<Notificationdetail> notification_list) {
        this.notification_list = notification_list;
    }

    public ArrayList<Joblistfullviewdetail> getJob_listview() {
        return job_listview;
    }

    public void setJob_listview(ArrayList<Joblistfullviewdetail> job_listview) {
        this.job_listview = job_listview;
    }

    public ArrayList<Joblistdetail> getJob_list() {
        return job_list;
    }

    public void setJob_list(ArrayList<Joblistdetail> job_list) {
        this.job_list = job_list;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
