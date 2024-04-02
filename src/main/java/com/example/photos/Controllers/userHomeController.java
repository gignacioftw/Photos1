package com.example.photos.Controllers;

import com.example.photos.Model.Album;
import com.example.photos.Model.User;
import com.example.photos.Model.UserSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static com.example.photos.Model.UserSystem.readApp;
import static com.example.photos.Model.UserSystem.writeApp;
import static javafx.scene.layout.VBox.*;

public class userHomeController {
    private ObservableList<String> items = FXCollections.observableArrayList();

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

    public void displayName(String username) throws IOException, ClassNotFoundException {
        InputStream stream = new FileInputStream("data/folder.png");
        Image image = new Image(stream);
        nameLabel.setText("Hello: " +username);
        this.username = username;
        User u = (User) s.getUser(username);
        String[] albumNames = u.getAlbumNames();
        items.addAll(albumNames);
        vbox.setHgap(10);
        vbox.setVgap(10);
        //vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setOrientation(Orientation.VERTICAL);
        for (String albumName : items) {
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(20);
            imageView.setPreserveRatio(true);
            vbox.getChildren().add(imageView);
            Label l = new Label(albumName);
            vbox.getChildren().add(l);
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
}
