package com.example.photos.Controllers;

import com.example.photos.Model.Album;
import com.example.photos.Model.User;
import com.example.photos.Model.UserSystem;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

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
    Label renameConfirmLabel;
    @FXML
    Button renameAlbum;
    @FXML
    Button renameConfirm;
    @FXML
    Button renameCancel;
    @FXML
    Label renameLabel;
    @FXML
    TextField renameInput;
    @FXML
    Label createLabel;
    @FXML
    Button deleteConfirm;
    @FXML
    Button deleteCancel;
    @FXML
    Label deleteLabel;
    @FXML
    Button deleteAlbum;
    @FXML
    TextField createInput;
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
        deleteConfirm.setVisible(false);
        renameConfirm.setVisible(false);
        renameCancel.setVisible(false);
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
            b.setStyle(("-fx-background-color:transparent"));
            vbox.getChildren().add(b);
            Label l = new Label(albumName);
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

    public void loadSystem(UserSystem s){
        this.s = s;
    }

    public void create(ActionEvent event) throws IOException {
        String album = createInput.getText();
        User u = (User) s.getUser(username);
        String[] albums = u.getAlbumNames();
        if(!album.isEmpty()){
            if(u.hasAlbum(album)){
                createLabel.setText("Duplicate album");
                returnCreate();
            }
            else{
                u.addAlbum(new Album(album));
                items.add(album);
                InputStream stream = new FileInputStream("data/folder.png");
                Image image = new Image(stream);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setFitWidth(20);
                imageView.setPreserveRatio(true);
                Button b = new Button();
                b.setGraphic(imageView);
                b.setStyle(("-fx-background-color:transparent"));
                vbox.getChildren().add(b);
                buttons.add(b);
                Label l = new Label(album);
                vbox.getChildren().add(l);
                labels.add(l);
                mouseClick(b, l);
                createInput.setText("");
                createLabel.setText(album +" created successfully");
                returnCreate();
            }
        }
        else{
            createLabel.setText("Please enter a valid album name");
            returnCreate();
        }
    }

    public void deselect(ActionEvent event){
        deselectM();
    }
    public void delete(ActionEvent event){
        deleteLabel.setWrapText(true);
        if(name == null){
            deleteLabel.setText("Please select an album");
            returnDelete();
        }
        else{
            deleteLabel.setText("Are you sure you want to delete: " +name + "?");
            deleteCancel.setVisible(true);
            deleteConfirm.setVisible(true);
        }
    }

    public void deleteCancel(ActionEvent event){
        deleteCancel.setVisible(false);
        deleteConfirm.setVisible(false);
        deleteLabel.setText("");
        name = null;
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

    public void rename(ActionEvent event){
        String album = renameInput.getText();
        String n = "Please select an album";
        String a = "Please enter new name";
        User u = (User) s.getUser(username);
        if(!album.isEmpty() && name != null){
            if(u.hasAlbum(album)){
                renameLabel.setText("Duplicate album");
                returnRename();
            }
            else{
                renameInput.setVisible(false);
                renameConfirm.setVisible(true);
                renameCancel.setVisible(true);
                renameLabel.setText("");
                renameConfirmLabel.setWrapText(true);
                renameConfirmLabel.setText("Are you sure you want to \nrename: " +name +" to: " +album +"?");
            }
        }
        else if(album.isEmpty() && name == null){
            renameLabel.setText(n +"\n"+a);
        }
        else if(!album.isEmpty()){
            renameLabel.setText(n);
        }
        else{
            renameLabel.setText(a);
        }
    }


    public void deleteConfirm(ActionEvent event){
        deleteLabel.setText(name + " successfully deleted");
        for(Button b : buttons){
            if(b.getStyle().equals(("-fx-background-color:#dae7f3;"))){
                vbox.getChildren().remove(b);
            }
        }
        for(Label l : labels){
            if(l.getText().equals(name)){
                vbox.getChildren().remove(l);
            }
        }
        deleteConfirm.setVisible(false);
        deleteCancel.setVisible(false);
        returnDelete();
        User u = (User)s.getUser(username);
        u.deleteAlbum(name);
    }

    private void returnDelete(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(1));
        message.setOnFinished(e -> {
            deleteLabel.setText("");
        });
        message.play();
    }

    private void returnCreate(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            createLabel.setText("");
        });
        message.play();
    }

    private void returnRename(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            renameConfirmLabel.setText("");
        });
        message.play();
    }

    private void returnRenameConfirm(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            renameConfirmLabel.setText("");
            renameInput.setVisible(true);
            renameInput.setText("");
        });
        message.play();
    }


    public void updateText(String name){
        if(!deleteLabel.getText().isEmpty()){
            deleteLabel.setText("Are you sure you want to delete: " +name + "?");
        }
    }

    public void renameCancel(ActionEvent event) {
        renameConfirmLabel.setText("");
        renameInput.setVisible(true);
        renameCancel.setVisible(false);
        renameConfirm.setVisible(false);
        renameInput.setText("");
        deselectM();

    }

    private void deselectM() {
        for(Button but : buttons){
            if(but.getStyle().equals(("-fx-background-color:#dae7f3;"))) {
                but.setStyle("-fx-background-color:transparent");
            }
        }
        for(Label lab : labels){
            if(lab.getText().equals(name)){
                lab.setStyle(("-fx-background-color:transparent;"));
                name = null;
            }
        }
    }

    public void renameConfirm(ActionEvent event){
        User u = (User) s.getUser(username);
        for(Label l : labels){
            if(l.getText().equals(name)){
                u.renameAlbum(name, renameInput.getText());
                int i = vbox.getChildren().indexOf(l);
                vbox.getChildren().remove(l);
                l.setText(renameInput.getText());
                vbox.getChildren().add(i, l);
                renameConfirm.setVisible(false);
                renameCancel.setVisible(false);
                renameConfirmLabel.setText("Album renamed successfully");
                returnRenameConfirm();
                name = renameInput.getText();
            }
        }
    }
}
