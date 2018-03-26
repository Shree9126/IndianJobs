package com.tamilanjobs.Response;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by mahei on 2/9/2018.
 */

@Table(name = "Reminder")

public class Reminder extends Model {

    @Column(name = "reminderpostid")
    public String reminderpostid;



    @Column(name = "remindertitle")
    public String remindertitle;

    @Column(name = "reminderdesc")
    public String reminderdesc;

    @Column(name = "reminderdate")
    public String reminderdate;

    @Column(name = "reminderdatetime")
    public String reminderdatetime;

    @Column(name = "vacancy")
    public String vacancy;




    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    @Column(name = "remindertime")
    public String remindertime;

    @Column(name = "reminderimage")
    public String reminderimage;


    @Column(name = "remindertypeid")
    public String remindertypeid;

    public String getRemindertypeid() {
        return remindertypeid;
    }

    public void setRemindertypeid(String remindertypeid) {
        this.remindertypeid = remindertypeid;
    }

    public String getReminderimage() {
        return reminderimage;
    }

    public void setReminderimage(String reminderimage) {
        this.reminderimage = reminderimage;
    }

    public String getReminderdatetime() {
        return reminderdatetime;
    }

    public void setReminderdatetime(String reminderdatetime) {
        this.reminderdatetime = reminderdatetime;
    }


    public String getReminderpostid() {
        return reminderpostid;
    }

    public void setReminderpostid(String reminderpostid) {
        this.reminderpostid = reminderpostid;
    }

    public String getRemindertitle() {
        return remindertitle;
    }

    public void setRemindertitle(String remindertitle) {
        this.remindertitle = remindertitle;
    }

    public String getReminderdesc() {
        return reminderdesc;
    }

    public void setReminderdesc(String reminderdesc) {
        this.reminderdesc = reminderdesc;
    }

    public String getReminderdate() {
        return reminderdate;
    }

    public void setReminderdate(String reminderdate) {
        this.reminderdate = reminderdate;
    }

    public String getRemindertime() {
        return remindertime;
    }

    public void setRemindertime(String remindertime) {
        this.remindertime = remindertime;
    }
}
