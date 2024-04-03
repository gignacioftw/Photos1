package com.example.photos.Model;

import java.io.*;
import java.util.*;
import java.util.HashSet;

public class UserSystem implements Serializable {
    private HashMap<String,User> users;

    public static final String storeDir = "data";
    public static final String storeFile = "users.dat";

    public UserSystem(){
        users = new HashMap<>();
        Admin a = new Admin();
        User stock = new User("stock");
        users.put(a.getUsername(), a);
        users.put(stock.getUsername(), stock);
    }

    public void addUser(User u){
        users.put(u.getUsername(), u);
    }

    public void deleteUser(String username){
        users.remove(username);
    }

    public String[] returnUsers(){
        String[] s = users.keySet().toArray(new String[0]);
        return s;
    }

    public boolean check(String username){
        return users.containsKey(username);
    }
    public void printUsernames(String[] usernames){
        for (String username : usernames) {
            System.out.println(username);
        }
    }
    public Object getUser(String username){
        return users.get(username);
    }
    public static void writeApp(UserSystem sys) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(sys);
    }

    public static UserSystem readApp() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
        return (UserSystem)ois.readObject();
    }
    public static Boolean hasData() throws IOException{
        return new File(storeDir, storeFile).exists();
    }

    public static void main(String[]args) throws IOException, ClassNotFoundException {
        UserSystem s = readApp();
        User u = (User) s.getUser("stock");
        u.addAlbum(new Album("stock"));
        Album a = u.getAlbum("stock");
        File file = new File("data/depositphotos_10880054-stock-photo-monkey-looking-on-a-computer.jpg");
        a.addPhoto(new Photo(file.getName(), file.getPath()));
        writeApp(s);
    }
}

