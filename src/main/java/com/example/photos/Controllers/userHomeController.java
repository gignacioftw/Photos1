package com.example.photos.Controllers;

import com.example.photos.Model.Album;
import com.example.photos.Model.User;
import com.example.photos.Model.UserSystem;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.photos.Controllers.openController.trunc;

/**
 * Handles userHome.fxml
 * @author Gigna
 */
public class userHomeController {
    private final ObservableList<String> items = FXCollections.observableArrayList();
    private final ObservableList<Button> buttons = FXCollections.observableArrayList();

    @FXML
    Label openLabel;
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
    Album a;
    String username;
    String name;

    public Stage stage;
    public Scene scene;
    public Parent root;
    public void displayName(String username) throws IOException {
        deleteCancel.setVisible(false);
        deleteConfirm.setVisible(false);
        renameConfirm.setVisible(false);
        renameCancel.setVisible(false);
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
            Album a = u.getAlbum(albumName);
            this.a = a;
            Button b = new Button();
            buttons.add(b);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(20);
            imageView.setPreserveRatio(true);
            b.setGraphic(imageView);
            b.setStyle(("-fx-background-color:transparent"));
            b.setTextAlignment(TextAlignment.CENTER);
            b.setContentDisplay(ContentDisplay.TOP);
            vbox.getChildren().add(b);
            b.setText(trunc15(albumName) + "\n" +a.getNumOfPhotos() + " photos" +"\n" +a.getDateRange());
            mouseClick(b);
            this.a = null;
        }
    }

    public static String trunc15(String s){
        if(s.length() > 15){
            return s.substring(0, 15) + "...";
        }
        else{
            return s;
        }
    }
    private void mouseClick(Button b) {
        b.setOnMouseClicked(e -> {
            String albumName = "";
            for(Button but : buttons){
                if(but == b){
                    b.setStyle(("-fx-background-color:#dae7f3;"));
                    for(String s : items){
                        if(b.getText().contains("...")){
                            if(s.contains(b.getText().substring(0, b.getText().indexOf(".")))){
                                albumName = s;
                            }
                        }
                        else if(!b.getText().contains("...")){
                            if(s.contains(b.getText().substring(0, b.getText().indexOf(("\n"))))){
                                albumName = s;
                            }
                        }
                    }
                }
                else{
                    but.setStyle(("-fx-background-color:transparent"));
                }
            }
            name = albumName;
        });
    }

    public void loadSystem(UserSystem s){
        this.s = s;
    }
    public void create() throws IOException {
        String album = createInput.getText();
        User u = (User) s.getUser(username);
        if(!album.isEmpty()){
            if(u.hasAlbum(album)){
                createLabel.setText("Duplicate album");
                returnCreate();
            }
            else{
                u.addAlbum(new Album(album));
                items.add(album);
                a = u.getAlbum(album);
                InputStream stream = new FileInputStream("data/folder.png");
                Image image = new Image(stream);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setFitWidth(20);
                imageView.setPreserveRatio(true);
                Button b = new Button();
                b.setGraphic(imageView);
                b.setStyle(("-fx-background-color:transparent"));
                b.setTextAlignment(TextAlignment.CENTER);
                b.setContentDisplay(ContentDisplay.TOP);
                b.setText(trunc15(album) + "\n" +a.getNumOfPhotos() + " photos" +"\n" +a.getDateRange());
                vbox.getChildren().add(b);
                buttons.add(b);
                mouseClick(b);
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

    public void deselect(){
        deselectM();
    }
    public void delete(){
        deleteLabel.setWrapText(true);
        if(name == null){
            deleteLabel.setText("Please select an album");
            returnDelete();
        }
        else{
            deleteLabel.setText("Are you sure you want to delete: " +trunc(name) + "?");
            deleteCancel.setVisible(true);
            deleteConfirm.setVisible(true);
        }
    }

    public void deleteCancel(){
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
    }

    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/logIn.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void open(ActionEvent event) throws IOException {
        if(name == null){
            openLabel.setText("Please select an album");
            returnOpen();
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/open.fxml"));
            root = loader.load();

            openController openController = loader.getController();
            openController.loadSystem(s, username, name);
            openController.displayName();

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void rename(){
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
                renameConfirmLabel.setText("Are you sure you want to \nrename: " +trunc(name) +" to: " +trunc(album) +"?");
            }
        }
        else if(album.isEmpty() && name == null){
            renameLabel.setText(n +"\n"+a);
            returnRename();
        }
        else if(!album.isEmpty()){
            renameLabel.setText(n);
            returnRename();
        }
        else{
            renameLabel.setText(a);
            returnRename();
        }
    }


    public void deleteConfirm(){
        deleteLabel.setText(name + " successfully deleted");
        for(Button b : buttons){
            if(b.getStyle().equals(("-fx-background-color:#dae7f3;"))){
                vbox.getChildren().remove(b);
            }
        }
        deleteConfirm.setVisible(false);
        deleteCancel.setVisible(false);
        returnDelete();
        User u = (User)s.getUser(username);
        u.deleteAlbum(name);
        deselectM();
    }

    private void returnDelete(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(1));
        message.setOnFinished(e -> deleteLabel.setText(""));
        message.play();
    }

    private void returnOpen(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(1));
        message.setOnFinished(e -> openLabel.setText(""));
        message.play();
    }

    private void returnCreate(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            createLabel.setText("");
            a = null;
        });
        message.play();
    }

    private void returnRename(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> renameLabel.setText(""));
        message.play();
    }

    private void returnRenameConfirm(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            renameConfirmLabel.setText("");
            renameInput.setVisible(true);
            renameInput.setText("");
            deselectM();
        });
        message.play();
    }

    public void renameCancel() {
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
                name = null;
            }
        }
    }

    public void renameConfirm(){
        User u = (User) s.getUser(username);
        for(Button b : buttons){
            if(b.getText().contains(trunc(name))){
                u.renameAlbum(name, renameInput.getText());
                int i = vbox.getChildren().indexOf(b);
                vbox.getChildren().remove(b);
                b.setText(trunc(renameInput.getText())+"\n" + u.getAlbum(renameInput.getText()).getNumOfPhotos() +" photos" + u.getAlbum(renameInput.getText()).getDateRange());
                vbox.getChildren().add(i, b);
                renameConfirm.setVisible(false);
                renameCancel.setVisible(false);
                renameConfirmLabel.setText("Album renamed successfully");
                items.remove(name);
                items.add(renameInput.getText());
                name = renameInput.getText();
                returnRenameConfirm();
            }
        }
    }
}
