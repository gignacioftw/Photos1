package com.example.photos.Model;

import java.io.Serializable;

public class Photo implements Serializable {
    protected String name;
    protected String path;
    protected String tag;
    protected String caption;

    protected String date;
    public Photo(){

    }

    public Photo(String name, String path){
        this.name = name;
        this.path = path;
    }

    public Photo(String name, String path, String date){
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

    public void addCaption(String caption){
        this.caption = caption;
    }

    public void addTag(String tag){
        this.tag = tag;
    }

    public void removeTag(String tag){

    }
    public String getDate(){
        return date;
    }
}
