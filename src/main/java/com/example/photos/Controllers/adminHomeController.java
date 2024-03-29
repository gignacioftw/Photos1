package com.example.photos.Controllers;

import com.example.photos.Model.User;
import com.example.photos.Model.UserSystem;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.Objects;

public class adminHomeController {
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
        createUser1.setVisible(false);
        usernameInput.setVisible(false);
        createdMessage.setVisible(false);
        nameLabel.setText("Hello: " +username);
    }

    public void loadSystem(UserSystem s){
        this.s = s;
    }
    public void create(ActionEvent event) {
        createUser.setVisible(false);
        createUser1.setVisible(true);
        usernameInput.setVisible(true);
    }

    public void delete(ActionEvent event) {
    }

    public void list(ActionEvent event) {

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
                    PauseTransition message = new PauseTransition();
                    message.setDuration(Duration.seconds(2));
                    message.setOnFinished(e -> {
                        createdMessage.setVisible(false);
                        createUser.setVisible(true);
                    });
                    message.play();
                    s.addUser(new User(username));
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
}
