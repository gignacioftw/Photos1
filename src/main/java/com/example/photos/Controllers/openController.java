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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class openController {
    @FXML
    Label renameTopLabel;
    @FXML
    TextField renameInput;
    @FXML
    Label renameLabel;
    @FXML
    Button renamePhoto;
    @FXML
    Button renameYes;
    @FXML
    Button renameNo;
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
    UserSystem s;
    String name;
    Album a;
    public Stage stage;
    public Scene scene;
    public Parent root;
    public void displayName(String username, String albumName) throws FileNotFoundException {
        renameNo.setVisible(false);
        renameYes.setVisible(false);
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
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            b.setGraphic(imageView);
            b.setText(p.getName());
            b.setAlignment(Pos.TOP_LEFT);
            b.setTextAlignment(TextAlignment.LEFT);
            b.setContentDisplay(ContentDisplay.TOP);
            b.setStyle(("-fx-background-color:transparent"));
            b.setWrapText(true);
            vbox.getChildren().add(b);
            mouseClick(b);
        }
    }

    private void mouseClick(Button b) {
        b.setOnMouseClicked(e -> {
            for(Button but : buttons){
                if(but == b){
                    b.setStyle(("-fx-background-color:#dae7f3;"));
                }
                else{
                    but.setStyle(("-fx-background-color:transparent"));
                }
            }
            name = b.getText();
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
            Button b = getButton(selected);
            vbox.getChildren().add(b);
            buttons.add(b);
            mouseClick(b);
            addLabel.setText("Photo added successfully");
            addLabel.setWrapText(true);
            returnAdd();
        }
        else{
            addLabel.setText("No file selected");
            returnAdd();
        }
    }
    public void rename(ActionEvent event){
        String photo = renameInput.getText();
        String n = "Please select a photo";
        String nn = "Please enter new name";
        if(!photo.isEmpty() && name != null){
            for(Photo p : a.getPhotos()) {
                if (a.hasPhoto(photo)) {
                    renameTopLabel.setText("Duplicate photo");
                    returnTopRename();
                } else {
                    renameInput.setVisible(false);
                    renameYes.setVisible(true);
                    renameNo.setVisible(true);
                    renameLabel.setText("");
                    renameLabel.setWrapText(true);
                    renameLabel.setText("Are you sure you want to \nrename: " + trunc(name) + " to: " +trunc(renameInput.getText()) +"?");
                }
            }
        }
        else if(photo.isEmpty() && name == null){
            renameTopLabel.setText(n +"\n"+nn);
            returnTopRename();
        }
        else if(!photo.isEmpty()){
            renameTopLabel.setText(n);
            returnTopRename();
        }
        else{
            renameTopLabel.setText(nn);
            returnTopRename();
        }
    }
    private static Button getButton(File selected) throws FileNotFoundException {
        InputStream stream = new FileInputStream(selected.getPath());
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        Button b = new Button();
        b.setGraphic(imageView);
        b.setStyle(("-fx-background-color:transparent"));
        b.setText(selected.getName());
        b.setWrapText(true);
        b.setContentDisplay(ContentDisplay.TOP);
        return b;
    }
    public static String trunc(String s){
        if(s.length() > 5){
            return (s.substring(0, 5) + "...");
        }
        else{
            return s;
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

    private void returnRename(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            renameLabel.setText("");
        });
        message.play();
    }
    private void returnTopRename(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            renameTopLabel.setText("");
        });
        message.play();
    }

    private void returnYes(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            renameLabel.setText("");
            renameInput.setText("");
            renameInput.setVisible(true);
            renameYes.setVisible(false);
            renameNo.setVisible(false);
        });
        message.play();
    }

    public void reYes(ActionEvent event) {
        for(Button b : buttons){
            if(b.getText().equals(name)){
                int i = vbox.getChildren().indexOf(b);
                vbox.getChildren().remove(b);
                b.setText(renameInput.getText());
                vbox.getChildren().add(i, b);
                renameYes.setVisible(false);
                renameNo.setVisible(false);
                renameLabel.setText("Photo renamed successfully");
                a.renamePhoto(name, renameInput.getText());
                returnYes();
            }
        }
    }

    public void reNo(ActionEvent event) {
        renameLabel.setText("");
        renameInput.setText("");
        renameInput.setVisible(true);
        renameYes.setVisible(false);
        renameNo.setVisible(false);
    }
}
