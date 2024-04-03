package com.example.photos.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    protected String username;
    protected HashMap<String, Album> albums;
    public User(){

    }

    public User(String username){
        this.username = username;
        albums = new HashMap<>();
    }
    public String[] getAlbumNames(){
        return albums.keySet().toArray(new String[0]);
    }

    public void addAlbum(Album a){
        albums.put(a.getAlbumName(), a);
    }

    public void deleteAlbum(String albumName){
        albums.remove(albumName);
    }

    public Boolean hasAlbum(String albumName){
        return albums.containsKey(albumName);
    }
    public void renameAlbum(String originalName, String newName){
        Album a = albums.get(originalName);
        a.changeName(newName);
        albums.remove(originalName);
        albums.put(newName, a);
    }

    public Album getAlbum(String albumName){
        return albums.get(albumName);
    }

    public String getUsername(){
        return this.username;
    }


}
