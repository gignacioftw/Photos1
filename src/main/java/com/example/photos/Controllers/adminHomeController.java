package com.example.photos.Controllers;

import com.example.photos.Model.User;
import com.example.photos.Model.UserSystem;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import javax.swing.*;
import java.util.Arrays;
import java.util.Objects;

public class adminHomeController {
    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    Button close;
    @FXML
    ListView listOfUsers;
    @FXML
    Button listUser;
    @FXML
    Button deleteCancel;
    @FXML
    Button createCancel;
    @FXML
    Button deleteYes;
    @FXML
    Button deleteNo;
    @FXML
    Label verifyLabel;
    @FXML
    Label errorMessage1;
    @FXML
    Button deleteUser;
    @FXML
    Button deleteUser1;
    @FXML
    ChoiceBox<String> userList;
    @FXML
    Label errorMessage;
    @FXML
    Label createdMessage;
    @FXML
    Button createUser;
    @FXML
    TextField usernameInput;
    @FXML
    Button createUser1;
    @FXML
    Label nameLabel;

    UserSystem s;

    public void displayName(String username){
        createCancel.setVisible(false);
        deleteNo.setVisible(false);
        deleteYes.setVisible(false);
        verifyLabel.setVisible(false);
        createUser1.setVisible(false);
        usernameInput.setVisible(false);
        createdMessage.setVisible(false);
        errorMessage1.setVisible(false);
        deleteUser1.setVisible(false);
        userList.setVisible(false);
        deleteCancel.setVisible(false);
        listOfUsers.setVisible(false);
        close.setVisible(false);
        nameLabel.setText("Hello: " +username);
    }

    public void loadSystem(UserSystem s){
        this.s = s;
        String[] usernames = s.returnUsers();
        items.addAll(Arrays.asList(usernames));
        userList.setItems(items);
    }
    public void create(ActionEvent event) {
        createUser.setVisible(false);
        createUser1.setVisible(true);
        usernameInput.setVisible(true);
        createCancel.setVisible(true);
    }

    public void delete(ActionEvent event) {
        deleteUser.setVisible(false);
        deleteUser1.setVisible(true);
        userList.setVisible(true);
        deleteCancel.setVisible(true);

    }

    public void delete1(ActionEvent event){
        String username = userList.getValue();
        if(username == null){
            returnError1();
        }
        else {
            userList.setVisible(false);
            deleteUser1.setVisible(false);
            verifyLabel.setWrapText(true);
            verifyLabel.setText("Are you sure you want to delete: " +username +"?");
            verifyLabel.setVisible(true);
            deleteYes.setVisible(true);
            deleteNo.setVisible(true);
            deleteCancel.setVisible(false);
        }
    }

    public void deleteYes(ActionEvent event){
        if(userList.getValue().equals("admin")) {
            verifyLabel.setText("Error: you cannot delete admin");
            returnDButton();
        }
        else{
            s.deleteUser(userList.getValue());
            items.remove(userList.getValue());
            deleteNo.setVisible(false);
            deleteYes.setVisible(false);
            userList.setValue(null);
            returnDelete();
        }
    }
    public void deleteNo(ActionEvent event){
        deleteNo.setVisible(false);
        deleteYes.setVisible(false);
        verifyLabel.setVisible(false);
        deleteUser1.setVisible(true);
        userList.setVisible(true);
    }
    public void list(ActionEvent event) {
        listOfUsers.setVisible(true);
        close.setVisible(true);
        listOfUsers.setItems(items);
    }

    public void pauseEnd(ActionEvent event){

    }
    public void create1(ActionEvent event) {
        createUser1.setVisible(false);
        usernameInput.setVisible(false);
        createdMessage.setVisible(true);
        errorMessage.setText("Please enter a valid username");
        createdMessage.setText("User created");

        errorMessage.setVisible(false);
        String username = usernameInput.getText();
        if(username != null) {
            if (!username.isEmpty()) {
                if(s.check(username)){
                    createdMessage.setVisible(false);
                    errorMessage.setText("Duplicate Username");
                    errorMessage.setVisible(true);
                    returnDupe();
                }
                else {
                    createdMessage.setVisible(true);
                    createCancel.setVisible(false);
                    PauseTransition message = new PauseTransition();
                    message.setDuration(Duration.seconds(2));
                    message.setOnFinished(e -> {
                        createdMessage.setVisible(false);
                        createUser.setVisible(true);
                    });
                    message.play();
                    s.addUser(new User(username));
                    items.add(username);
                    usernameInput.setText(null);
                }
            } else {
                createdMessage.setVisible(false);
                errorMessage.setVisible(true);
                returnError();
            }
        }
        else{
            createdMessage.setVisible(false);
            errorMessage.setVisible(true);
            returnError();
        }
    }

    private void returnError() {
        createUser1.setVisible(true);
        usernameInput.setVisible(true);
        usernameInput.setText(null);
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            errorMessage.setVisible(false);
        });
        message.play();
    }
    private void returnError1() {
        errorMessage1.setVisible(true);
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            errorMessage1.setVisible(false);
        });
        message.play();
    }
    private void returnDupe() {
        createUser1.setVisible(true);
        usernameInput.setVisible(true);
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            errorMessage.setVisible(false);
            errorMessage.setText("Please enter a valid username");
            usernameInput.setText(null);
        });
        message.play();
    }

    private void returnDelete(){
        verifyLabel.setText("User deleted");
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            verifyLabel.setVisible(false);
            deleteUser.setVisible(true);
        });
        message.play();
    }

    private void returnDButton(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            deleteNo.setVisible(false);
            deleteYes.setVisible(false);
            verifyLabel.setVisible(false);
            deleteUser1.setVisible(true);
            userList.setVisible(true);
        });
        message.play();
    }

    public void createCancel(ActionEvent event) {
        createCancel.setVisible(false);
        createUser1.setVisible(false);
        createUser.setVisible(true);
        usernameInput.setVisible(false);
    }

    public void deleteCancel(ActionEvent event) {
        deleteCancel.setVisible(false);
        deleteUser1.setVisible(false);
        userList.setVisible(false);
        deleteUser.setVisible(true);
    }

    public void closeList(ActionEvent event){
        listOfUsers.setVisible(false);
        close.setVisible(false);
        listUser.setVisible(true);
    }
}
