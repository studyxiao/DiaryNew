package com.studyxiao.diarynew.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Studyxiao on 17/2/22.
 */

public class DiaryBean extends DataSupport implements Serializable {

    private int id;
    private String date;
    private String title;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
