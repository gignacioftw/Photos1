package com.example.photos.Controllers;

import com.example.photos.Model.Album;
import com.example.photos.Model.Photo;
import com.example.photos.Model.User;
import com.example.photos.Model.UserSystem;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class openController {
    @FXML
    Label albumTitle;
    @FXML
    Label nameLabel;
    @FXML
    TilePane vbox;
    @FXML
    Label addLabel;

    private ObservableList<Photo> items = FXCollections.observableArrayList();
    private ObservableList<Button> buttons = FXCollections.observableArrayList();
    private ObservableList<Label> labels = FXCollections.observableArrayList();
    UserSystem s;
    String name;
    Album a;
    public Stage stage;
    public Scene scene;
    public Parent root;
    public void displayName(String username, String albumName) throws FileNotFoundException {
        this.name = albumName;
        nameLabel.setText("Hello: " +username);
        albumTitle.setText(albumName);
        int i = 0;
        Photo[] picture = a.getPhotos();
        items.addAll(picture);
        vbox.setOrientation(Orientation.HORIZONTAL);
        vbox.setHgap(5);
        vbox.setHgap(5);
        for (Photo p: items) {
            InputStream stream = new FileInputStream(p.getPath());
            File file = new File(p.getPath());
            Image image = new Image(stream);
            Button b = new Button();
            buttons.add(b);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(150);
            imageView.setPreserveRatio(true);
            b.setGraphic(imageView);
            b.setStyle(("-fx-background-color:transparent"));
            vbox.getChildren().add(b);
            Label l = new Label(file.getName());
            l.setMaxWidth(50);
            l.setWrapText(true);
            labels.add(l);
            vbox.getChildren().add(l);
            mouseClick(b, l);
        }
    }

    private void mouseClick(Button b, Label l) {
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
                }
                else{
                    lab.setStyle(("-fx-background-color:transparent"));
                }
            }
            name = l.getText();
            e.consume();
        });
    }

    public void loadSystem(UserSystem system, String username){
        this.s = system;
        this.a = (((User)s.getUser(username)).getAlbum(username));
    }

    public void add(ActionEvent event) throws FileNotFoundException {
        FileChooser f = new FileChooser();
        f.setTitle("Open Resource File");
        f.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png", "*.bmp", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
        File selected = f.showOpenDialog(stage);
        if(selected != null){
            a.addPhoto(new Photo(selected.getName(), selected.getPath()));
            addLabel.setText(selected.getName());
            items.add(new Photo(selected.getName(), selected.getPath()));
            InputStream stream = new FileInputStream(selected.getPath());
            Image image = new Image(stream);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            Button b = new Button();
            b.setGraphic(imageView);
            b.setStyle(("-fx-background-color:transparent"));
            vbox.getChildren().add(b);
            buttons.add(b);
            Label l = new Label(selected.getName());
            l.setMaxWidth(150);
            l.setWrapText(true);
            vbox.getChildren().add(l);
            labels.add(l);
            mouseClick(b, l);
            addLabel.setText(selected.getName() +" added successfully");
            addLabel.setWrapText(true);
            returnAdd();
        }
        else{
            addLabel.setText("No file selected");
            returnAdd();
        }
    }

    private void returnAdd(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            addLabel.setText("");
        });
        message.play();
    }
}
