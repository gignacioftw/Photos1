package com.example.photos.Model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Tag implements Serializable {
    protected String name;
    protected String value;
    protected ArrayList<String> tagTypes = new ArrayList<>(Arrays.asList("location", "person", "pet", ""));

    public Tag(){

    }

    public Tag(String name, String value){
        this.name = name;
        this.value = value;
    }

    public void changeTagName(String name){
        this.name = name;
    }

    public void changeTagValue(String value){
        this.value = value;
    }

    public void changeBoth(String name, String value){
        changeTagName(name);
        changeTagValue(value);
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

}
