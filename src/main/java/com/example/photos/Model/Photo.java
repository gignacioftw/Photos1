package com.example.photos.Model;


import java.io.File;
import java.io.Serializable;
import java.util.Calendar;

public class Photo implements Serializable {
    protected String name;
    protected String path;
    protected String tag;
    protected String caption;

    protected Calendar date;
    public Photo(){

    }

    public Photo(String name, String path){
        this.name = name;
        this.path = path;
    }

    public Photo(String name, String path, Calendar date){
        this.name = name;
        this.path = path;
        this.date = date;
    }

    public String getPath(){
        return path;
    }

    public String getName(){
        return name;
    }

    public String getCaption(){
        return caption;
    }

    public void addCaption(String caption){
        this.caption = caption;
    }

    public void addTag(String tag){
        this.tag = tag;
    }

    public void removeTag(String tag){

    }

    public void changeName(String newName) {
        this.name = newName;
    }
    public Calendar getDate(){
        return date;
    }
}
