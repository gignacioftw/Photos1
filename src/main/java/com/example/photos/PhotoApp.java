package com.example.photos;

import com.example.photos.Model.UserSystem;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PhotoApp extends Application {

    UserSystem s;
    @Override
    public void start(Stage logInstage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        Scene scene = new Scene(root);
        logInstage.setResizable(false);
        logInstage.setTitle("Photos");
        logInstage.setScene(scene);
        logInstage.show();

    }



    public static void main(String[] args) {
        launch();
    }
}