package com.example.photos.Controllers;

import com.example.photos.Model.Admin;
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
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import static com.example.photos.Model.UserSystem.*;

public class logInController {
    public UserSystem s;
    public File dir = new File("data");
    public Button loginButton;
    public Label errorMessage;
    public Button helpButton;
    @FXML
    TextField nameTextField;
    public Stage stage;
    public Scene scene;
    public Parent root;

    public void login(ActionEvent event) throws IOException, ClassNotFoundException, URISyntaxException {
        s = readApp();
        String username = nameTextField.getText();

        if(s.check(username)) {
            if (s.getUser(username) instanceof Admin) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/adminHome.fxml"));
                root = loader.load();

                adminHomeController adminHomeController = loader.getController();
                adminHomeController.displayName(username);
                adminHomeController.loadSystem(s);

                //root = FXMLLoader.load(getClass().getResource("logIn.xml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/userHome.fxml"));
                root = loader.load();

                userHomeController userHomeController = loader.getController();
                userHomeController.loadSystem(s);
                userHomeController.displayName(username);

                //root = FXMLLoader.load(getClass().getResource("logIn.xml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        else{
            errorMessage.setText("Please enter a valid username");
        }
        stage.setOnHidden( e -> {
            try {
                writeApp(s);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
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