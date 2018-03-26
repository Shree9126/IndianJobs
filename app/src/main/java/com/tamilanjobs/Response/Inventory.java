package com.tamilanjobs.Response;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by mahei on 2/9/2018.
 */

@Table(name = "Inventorys")


public class Inventory extends Model {



    @Column(name = "postid")
    public String postid;

    @Column(name = "name")
    public String name;

    @Column(name = "organization")
    public String organization;

    @Column(name = "vacancy")
    public String vacancy;

    @Column(name = "favdate")
    public String favdate;

    @Column(name = "favimage")
    public String favimage;

    @Column(name = "applylink")
    public String applylink;

    @Column(name = "morelink")
    public String morelink;

    public String getMorelink() {
        return morelink;
    }

    public void setMorelink(String morelink) {
        this.morelink = morelink;
    }

    public String getIsReminder() {
        return isReminder;
    }

    public void setIsReminder(String isReminder) {
        this.isReminder = isReminder;
    }

    @Column(name = "isReminder")
    public String isReminder;

    @Column(name = "favouritetypeid")
    public String favouritetypeid;

    public String getFavouritetypeid() {
        return favouritetypeid;
    }

    public void setFavouritetypeid(String favouritetypeid) {
        this.favouritetypeid = favouritetypeid;
    }

    public String getApplylink() {
        return applylink;
    }

    public void setApplylink(String applylink) {
        this.applylink = applylink;
    }

    public String getFavimage() {
        return favimage;
    }

    public void setFavimage(String favimage) {
        this.favimage = favimage;
    }

    public String getFavdate() {
        return favdate;
    }

    public void setFavdate(String favdate) {
        this.favdate = favdate;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}
