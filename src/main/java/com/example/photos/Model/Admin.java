package com.example.photos.Model;

import java.io.Serializable;

/**
 * Admin subclass of User class. Has username.
 * @author Gigna
 */
public class Admin extends User implements Serializable {

    public Admin(){
         super.username = "admin";
    }

    public Admin(String username){
        this.username = username;
    }

}
