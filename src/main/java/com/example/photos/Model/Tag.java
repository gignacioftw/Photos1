package com.example.photos.Model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Tag implements Serializable {
    protected String name;
    protected String value;

    public Tag(){

    }

    public Tag(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }


}
