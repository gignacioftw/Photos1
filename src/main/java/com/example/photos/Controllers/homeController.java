package com.example.photos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class homeController {

    @FXML
    Label nameLabel;

    public void displayName(String username){
        nameLabel.setText("Hello: " +username);
    }


}
