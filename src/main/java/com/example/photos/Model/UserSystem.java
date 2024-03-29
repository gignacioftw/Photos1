package com.example.photos.Model;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;

public class UserSystem implements Serializable {
    private HashSet<User> users;

    public static final String storeDir = "data";
    public static final String storeFile = "users.dat";

    public UserSystem(){
        users = new HashSet<>();
        users.add(new Admin());
    }

    public void addUser(User u){
        users.add(u);
    }

    public void deleteUser(User u){
        users.remove(u);
    }

    public String[] returnUsers(){
        String[] usernames = new String[users.size()];
        int i = 0;
        for(User u : users){
            usernames[i] = u.getUsername();
            i++;
        }
        return usernames;
    }

    public boolean check(String username){
        for(User u : users){
            if(u.username.equals(username)){
                return true;
            }
        }
        return false;
    }
    public void printUsernames(String[] usernames){
        for (String username : usernames) {
            System.out.println(username);
        }
    }
    public static void writeApp(UserSystem sys) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(sys);
    }

    public static UserSystem readApp() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
        return (UserSystem)ois.readObject();
    }

    public static void main(String[]args) throws IOException, ClassNotFoundException {
        UserSystem s = readApp();
        //s.addUser(new User("flames"));
        //s.addUser(new User("blud"));
        s.printUsernames(s.returnUsers());
        //writeApp(s);
    }
}

