package com.example.photos.Controllers;

import com.example.photos.Model.UserSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.photos.Model.UserSystem.readApp;

public class logInController {

    public UserSystem s;
    public Button loginButton;
    public Label errorMessage;
    public Button helpButton;
    @FXML
    TextField nameTextField;

    public Stage stage;
    public Scene scene;
    public Parent root;

    public void login(ActionEvent event) throws IOException, ClassNotFoundException {
        s = readApp();

        String username = nameTextField.getText();

        if(s.check(username)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/home.fxml"));
            root = loader.load();

            homeController homeController = loader.getController();
            homeController.displayName(username);

            //root = FXMLLoader.load(getClass().getResource("logIn.xml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            errorMessage.setText("Please enter a valid username");
        }
    }
    public void help(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/logInhelp.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}