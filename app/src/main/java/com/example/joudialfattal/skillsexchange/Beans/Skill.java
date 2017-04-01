package com.example.joudialfattal.skillsexchange.Beans;

import java.io.Serializable;

/**
 * Created by joudial-fattal on 3/24/17.
 */

public class Skill implements Serializable {
    private String id;
    private String title;
    //private String content;
    private String date;

    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
