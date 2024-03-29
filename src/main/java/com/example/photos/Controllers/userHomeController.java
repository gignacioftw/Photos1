package com.example.photos.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class userHomeController {
    @FXML
    Label nameLabel;

    public void displayName(String username){
        nameLabel.setText("Hello: " +username);
    }
}
