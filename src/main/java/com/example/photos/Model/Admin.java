package com.example.photos.Model;

import java.io.Serializable;

public class Admin extends User implements Serializable {

    public Admin(){
         super.username = "admin";
    }

    public Admin(String username){
        this.username = username;
    }

    public void createUser(){

    }

}
