package com.example.photos.Model;

import java.io.Serializable;

public class User implements Serializable {
    protected String username;

    public User(){

    }

    public User(String username){
        this.username = username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

}
