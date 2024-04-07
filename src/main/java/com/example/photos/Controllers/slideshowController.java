package com.example.photos.Controllers;

import com.example.photos.Model.Album;
import com.example.photos.Model.Photo;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class slideshowController {
    @FXML
    ImageView photoView;
    @FXML
    Label errorLabel;
    Album a;
    Photo[] p;

    int pp;
    Photo photo;
    Scene preScene;
    Stage stage;
    public void display() throws FileNotFoundException {
        p = a.getPhotos();
        InputStream stream = new FileInputStream(photo.getPath());
        Image image = new Image(stream);
        photoView.setImage(image);
        for(int i = 0; i < p.length; i++){
            if(p[i] == photo){
                pp = i;
            }
        }
    }
    public void getpreScene(Scene s){
        preScene = s;
    }
    public void loadSystem(Album a, Photo p){
        photo = p;
        this.a = a;
    }

    public void next() throws FileNotFoundException {
        if(pp < p.length - 1){
            InputStream stream = new FileInputStream(p[pp+1].getPath());
            Image image = new Image(stream);
            photoView.setImage(image);
            pp++;
        }
        else{
            errorLabel.setText("Last image");
            returnError();
        }
    }

    public void back() throws FileNotFoundException {
        if(pp > 0){
            InputStream stream = new FileInputStream(p[pp-1].getPath());
            Image image = new Image(stream);
            photoView.setImage(image);
            pp--;
        }
        else{
            errorLabel.setText("First image");
            returnError();
        }
    }

    public void returnPrev(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(preScene);
        stage.show();
    }

    public void returnError(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(1));
        message.setOnFinished(e -> errorLabel.setText(""));
        message.play();
    }
}
