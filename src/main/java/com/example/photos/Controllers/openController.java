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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.photos.Model.UserSystem.writeApp;

public class openController {
    @FXML
    TextField captionInput;
    @FXML
    Label addcapLabel;
    @FXML
    Label deletecapLabel;
    @FXML
    AnchorPane anchorcap;
    @FXML
    AnchorPane anchorpho;
    @FXML
    ToggleButton captionButton;
    @FXML
    Label errorLabel;
    @FXML
    ScrollPane buttonScroll;
    @FXML
    Label deleteLabel;
    @FXML
    Button deleteYes;
    @FXML
    Button deleteNo;
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

    private ArrayList<String> labels = new ArrayList<>();
    UserSystem s;
    String name;
    Album a;

    Button b;
    User u;
    public Stage stage;
    public Scene scene;
    public Parent root;

    public void displayName() throws FileNotFoundException {
        deleteYes.setVisible(false);
        deleteNo.setVisible(false);
        renameNo.setVisible(false);
        renameYes.setVisible(false);
        nameLabel.setText(u.getUsername());
        albumTitle.setText(a.getAlbumName());
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
            b.setTextAlignment(TextAlignment.CENTER);
            b.setContentDisplay(ContentDisplay.TOP);
            b.setMaxWidth(30);
            b.setStyle(("-fx-background-color:transparent"));
            b.setWrapText(true);
            vbox.getChildren().add(b);
            mouseClick(b);
        }
    }

    public void loadSystem(UserSystem system, String username, String albumName){
        this.s = system;
        this.u = (User) s.getUser(username);
        this.a = u.getAlbum(albumName);
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
            this.b = b;
            e.consume();
        });
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
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(selected.lastModified());
            c.set(Calendar.MILLISECOND,0);
            a.addPhoto(new Photo(selected.getName().substring(0, selected.getName().indexOf(".")), selected.getPath(), c));
            items.add(new Photo(selected.getName().substring(0, selected.getName().indexOf(".")), selected.getPath(), c));
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

    public void back(ActionEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/userHome.fxml"));
        root = loader.load();

        userHomeController userHomeController = loader.getController();
        userHomeController.loadSystem(s);
        userHomeController.displayName(u.getUsername());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    public void delete(ActionEvent Event){
        if(name == null){
            deleteLabel.setText("Please select an album");
            returnDelete();
        }
        else{
            deleteLabel.setWrapText(true);
            deleteLabel.setText("Are you sure you want to delete: " +trunc(name) + "?");
            deleteYes.setVisible(true);
            deleteNo.setVisible(true);
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
        b.setText(selected.getName().substring(0, selected.getName().indexOf(".")));
        b.setMaxWidth(30);
        b.setTextAlignment(TextAlignment.CENTER);
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

    private void returncapAdd(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            addcapLabel.setText("");
            captionInput.setVisible(true);
        });
        message.play();
    }

    private void returncapdDelete(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            deletecapLabel.setText("");
        });
        message.play();
    }
    private void returnAdd(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> addLabel.setText(""));
        message.play();
    }

    private void returnDelete(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> deleteLabel.setText(""));
        message.play();
    }

    private void returnTopRename(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> renameTopLabel.setText(""));
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

    private void returnError(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            errorLabel.setText("");
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
        deselectM();
    }

    public void deYes(ActionEvent event){
        deleteLabel.setText("Photo deleted successfully");
        deleteYes.setVisible(false);
        deleteNo.setVisible(false);
        for(Button b : buttons){
            if(b.getStyle().equals(("-fx-background-color:#dae7f3;"))){
                vbox.getChildren().remove(b);
            }
        }
        a.removePhoto(name);
        returnDelete();
        deselectM();
    }

    public void deNo(ActionEvent event){
        deleteLabel.setText("");
        deleteYes.setVisible(false);
        deleteNo.setVisible(false);
        deselectM();
    }

    public void deselect(ActionEvent event){
        deselectM();
    }

    private void deselectM() {
        for(Button but : buttons){
            if(but.getStyle().equals(("-fx-background-color:#dae7f3;"))) {
                but.setStyle("-fx-background-color:transparent");
                name = null;
                b = null;
            }
        }
    }

    public void caption(ActionEvent event) throws IOException {
        if(captionButton.isSelected()){
            buttonScroll.setContent(anchorcap);
            for (int i = 0; i < items.size(); i++) {
                for (Button button : buttons) {
                    if (items.get(i).getName().equals((button.getText()))) {
                        if (items.get(i).getCaption() == null) {
                            button.setText(null);
                        } else {
                            button.setText(items.get(i).getCaption());
                        }
                    }
                }
                if(!labels.contains(items.get(i).getName())){
                    labels.add(i, items.get(i).getName());
                }
            }
        }
        else{
            buttonScroll.setContent(anchorpho);
            for (Photo item : items) {
                for (int j = 0; j < buttons.size(); j++) {
                    if (item.getName().equals(labels.get(j))) {
                        buttons.get(j).setText(labels.get(j));
                    }
                }
            }
        }
    }

    public void addCap(ActionEvent event){
        String caption = captionInput.getText();
        String n = "Please select a photo";
        String nn = "Please enter a caption";
        if(!caption.isEmpty() && b != null){
            for (Photo item : items) {
                for (int j = 0; j < buttons.size(); j++) {
                    if (buttons.get(j) == b) {
                        if (item.getName().equals(labels.get(j))) {
                            item.addCaption(caption);
                            a.addCaption(item.getName(), caption);
                        }
                    }
                }

            }
            b.setText(caption);
            captionInput.setVisible(false);
            addcapLabel.setText("Caption successfully added");
            returncapAdd();
            captionInput.setText("");
        }
        else if(caption.isEmpty() && b == null){
            captionInput.setVisible(false);
            addcapLabel.setText(n +"\n"+nn);
            returncapAdd();
        }
        else if(!caption.isEmpty()){
            captionInput.setVisible(false);
            addcapLabel.setText(n);
            returncapAdd();
        }
        else {
            captionInput.setVisible(false);
            addcapLabel.setText(nn);
            returncapAdd();
        }
    }

    public void deleteCap(ActionEvent event){
        if(b == null){
            deletecapLabel.setText("Please select a photo");
            returncapdDelete();
        }
        else{
            for(Photo p : items){
                for(int i = 0; i< buttons.size(); i++){
                    if (buttons.get(i) == b) {
                        if (p.getName().equals(labels.get(i))) {
                            p.addCaption(null);
                            a.addCaption(p.getName(), null);
                            b.setText("");
                            deletecapLabel.setText("Caption deleted successfully");
                            returncapdDelete();
                        }
                    }
                }
            }
        }

    }
    public void tag(ActionEvent event) {

    }

    public void slideshow(ActionEvent event) {
    }
}
