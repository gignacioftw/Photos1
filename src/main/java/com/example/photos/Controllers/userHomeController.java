package com.example.photos.Controllers;

import com.example.photos.Model.Album;
import com.example.photos.Model.User;
import com.example.photos.Model.UserSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static com.example.photos.Model.UserSystem.readApp;
import static com.example.photos.Model.UserSystem.writeApp;
import static java.awt.Color.blue;
import static javafx.scene.layout.VBox.*;

public class userHomeController {
    private ObservableList<String> items = FXCollections.observableArrayList();
    private ObservableList<Button> buttons = FXCollections.observableArrayList();
    private ObservableList<Label> labels = FXCollections.observableArrayList();
    @FXML
    Button deleteCancel;
    @FXML
    Label deleteLabel;
    @FXML
    Button deleteAlbum;
    @FXML
    TextField albumInput;
    @FXML
    Button createAlbum;
    @FXML
    TilePane vbox;
    @FXML
    Label nameLabel;
    UserSystem s;

    String username;
    String name;
    public void displayName(String username) throws IOException, ClassNotFoundException {
        deleteCancel.setVisible(false);
        int i = 0;
        InputStream stream = new FileInputStream("data/folder.png");
        Image image = new Image(stream);
        nameLabel.setText("Hello: " +username);
        this.username = username;
        User u = (User) s.getUser(username);
        String[] albumNames = u.getAlbumNames();
        items.addAll(albumNames);
        vbox.setHgap(10);
        vbox.setVgap(10);
        vbox.setOrientation(Orientation.VERTICAL);
        for (String albumName : items) {
            Button b = new Button();
            buttons.add(b);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(20);
            imageView.setPreserveRatio(true);
            b.setGraphic(imageView);
            vbox.getChildren().add(b);
            Label l = new Label(albumName);
            labels.add(l);
            vbox.getChildren().add(l);
            b.setOnMouseClicked(e -> {
                for(Button but : buttons){
                    if(but == b){
                        b.setStyle(("-fx-background-color:#dae7f3;"));
                    }
                    else{
                        but.setStyle(("-fx-background-color:transparent"));
                    }
                }
                for(Label lab : labels){
                    if(lab == l){
                        l.setStyle(("-fx-background-color:#dae7f3;"));
                        updateText(l.getText());
                    }
                    else{
                        lab.setStyle(("-fx-background-color:transparent"));
                    }
                }
                name = l.getText();
                e.consume();
            });
        }
    }
    public void loadSystem(UserSystem s){
        this.s = s;
    }

    public void create(ActionEvent event) throws IOException {
        String album = albumInput.getText();
        User u = (User)s.getUser(username);
        u.addAlbum(new Album(album));
        items.add(album);
        InputStream stream = new FileInputStream("data/folder.png");
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);
        vbox.getChildren().add(imageView);
        vbox.getChildren().add(new Label(album));
    }

    public void delete(ActionEvent event){
        deleteLabel.setWrapText(true);
        if(name == null){
            deleteLabel.setText("Please select an album");
        }
        else{
            deleteLabel.setText("Are you sure you want to delete: " +name + "?");
            deleteCancel.setVisible(true);
        }
    }

    public void deleteCancel(ActionEvent event){
        deleteCancel.setVisible(false);
        deleteLabel.setText("");
        cancel();
    }

    public void cancel(){
        for(Button b : buttons){
            if(b.getStyle().equals(("-fx-background-color:#dae7f3;"))){
            b.setStyle(("-fx-background-color:transparent"));
            }
        }
        for(Label l : labels){
            if(l.getStyle().equals(("-fx-background-color:#dae7f3;"))){
                l.setStyle(("-fx-background-color:transparent"));
            }
        }
    }
    public void updateText(String name){
        if(!deleteLabel.getText().isEmpty()){
            deleteLabel.setText("Are you sure you want to delete: " +name + "?");
        }
    }

}
