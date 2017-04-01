package com.example.joudialfattal.skillsexchange.Beans;

import java.io.Serializable;

/**
 * Created by sotra on 4/1/2017.
 */
public class UserModel implements Serializable {
    String id , name  ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
