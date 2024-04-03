package com.example.photos.Model;

import java.io.Serializable;
import java.util.HashMap;

public class Album implements Serializable {
    protected String albumName;

    protected HashMap<String, Photo> photos;
    public Album(){

    }

    public Album(String albumName){
        this.albumName = albumName;
        photos = new HashMap<>();
    }

    public String getAlbumName(){
        return albumName;
    }
    public void changeName(String albumName){
        this.albumName = albumName;
    }

    public void addPhoto(Photo p){
        photos.put(p.name, p);
    }

    public int getNumOfPhotos(){
        return photos.size();
    }

    public Photo[] getPhotos(){
        return photos.values().toArray(new Photo[0]);
    }

}
