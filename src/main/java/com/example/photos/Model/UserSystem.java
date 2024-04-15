package com.example.photos.Model;

import java.io.*;
import java.util.*;
import java.util.HashSet;

/**
 * UserSystem has a HashMap of Users and a HashMap of Strings+Ints that stores the type of tag names
 * @author Gigna
 */
public class UserSystem implements Serializable {
    private final HashMap<String,User> users;

    private final HashMap<String, Integer> tagTypes;

    public static final String storeDir = "data";
    public static final String storeFile = "users.dat";

    public UserSystem(){
        tagTypes = new HashMap<>();
        users = new HashMap<>();
        Admin a = new Admin();
        User stock = new User("stock");
        users.put(a.getUsername(), a);
        users.put(stock.getUsername(), stock);
        tagTypes.put("location", 0);
        tagTypes.put("person", 1);
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

    public void addtagType(String type, int i){
        if(!tagTypes.containsKey(type)){
            tagTypes.put(type, i);
        }
    }

    public Boolean canAdd(String type, Photo t){
        return tagTypes.get(type) != 0 || !t.hasType(type);
    }

    public String[] returnTagTypes(){
        return tagTypes.keySet().toArray(new String[0]);
    }
}

