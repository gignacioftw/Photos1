package com.example.photos.Controllers;

import com.example.photos.Model.*;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
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

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

/**
 * Handles open.fxml
 * @author Gigna
 */
public class openController {
    @FXML
    Button openPicture;
    @FXML
    Label createTagAlbumLabel;
    @FXML
    Button searchButton;
    @FXML
    AnchorPane anchortagalbum;
    @FXML
    ScrollPane photoScroll;
    @FXML
    TilePane vbox1;
    @FXML
    Label searchLabel;
    @FXML
    TextField searchInput;
    @FXML
    ChoiceBox<String> tagChoice;
    @FXML
    Label deleteTagLabel;
    @FXML
    Label createTagLabel;
    @FXML
    CheckBox multipleCheck;
    @FXML
    TextField tagTypeInput;
    @FXML
    Label addTagLabel;
    @FXML
    TextField tagInput;
    @FXML
    ChoiceBox<String> tagType;
    @FXML
    Button copyConfirm;
    @FXML
    Button copyHide;
    @FXML
    Label copyLabel;
    @FXML
    ChoiceBox<String> albumChoice1;
    @FXML
    Button moveHide;
    @FXML
    Button moveConfirm;
    @FXML
    ChoiceBox<String> albumChoice;
    @FXML
    Label moveLabel;
    @FXML
    TextField captionInput;
    @FXML
    Label addcapLabel;
    @FXML
    Label deletecapLabel;
    @FXML
    AnchorPane anchortag;
    @FXML
    AnchorPane anchorcap;
    @FXML
    AnchorPane anchorpho;
    @FXML
    ToggleButton tagButton;
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
    
    private final ObservableList<Photo> items = FXCollections.observableArrayList();
    private final ObservableList<Button> buttons = FXCollections.observableArrayList();

    private final ObservableList<Photo> searchItems =  FXCollections.observableArrayList();

    private final ArrayList<Button> tagButtons =  new ArrayList<>();
    private final ArrayList<String> labels = new ArrayList<>();
    UserSystem s;
    String name;
    Album a;

    Button b;
    User u;
    public Stage stage;
    public Scene scene;
    public Parent root;

    public void displayName() throws FileNotFoundException {
        String[] s = u.getAlbumNames();
        albumChoice.getItems().addAll(s);
        albumChoice.getItems().remove(a.getAlbumName());
        albumChoice1.getItems().addAll(s);
        albumChoice1.getItems().remove(a.getAlbumName());
        deleteYes.setVisible(false);
        deleteNo.setVisible(false);
        renameNo.setVisible(false);
        renameYes.setVisible(false);
        nameLabel.setText(trunc10(u.getUsername()));
        albumTitle.setText(a.getAlbumName());
        Photo[] picture = a.getPhotos();
        items.addAll(picture);
        vbox.setOrientation(Orientation.HORIZONTAL);
        vbox.setHgap(5);
        vbox.setHgap(5);
        for (Photo p: items) {
            InputStream stream = new FileInputStream(p.getPath());
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
            labels.add(b.getText());
            vbox.getChildren().add(b);
            mouseClick(b);
        }
    }

    public static String trunc10(String s){
        if(s.length() > 10){
            return s.substring(0, 10) + "...";
        }
        else{
            return s;
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
                    tagChoice.getItems().clear();
                    tagType.getItems().clear();
                    this.b = b;
                }
                else{
                    but.setStyle(("-fx-background-color:transparent"));
                    tagChoice.getItems().clear();
                    tagType.getItems().clear();
                }
            }
            name = b.getText();
            e.consume();
            int x = buttons.indexOf(b);
            if(buttonScroll.getContent().equals(anchortag)){
                for(int l = 0; l < a.getPhoto(labels.get(x)).returnTags().length; l++) {
                    if (!tagChoice.getItems().contains(a.getPhoto(labels.get(x)).returnTags()[l])) {
                        tagChoice.getItems().add(a.getPhoto(labels.get(x)).returnTags()[l]);
                    }
                }
                for(int k = 0; k < s.returnTagTypes().length; k++){
                    if(s.canAdd(s.returnTagTypes()[k], a.getPhoto(labels.get(x)))) {
                        tagType.getItems().add(s.returnTagTypes()[k]);
                    }
                }
            }
        });
    }

    public void add() throws FileNotFoundException {
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

    public void back(ActionEvent event) throws IOException {
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

    public void rename(){
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

    public void delete(){
        if(name == null){
            deleteLabel.setText("Please select an album");
            buttonScroll.setVvalue(.3);
            returnDelete();
        }
        else{
            buttonScroll.setVvalue(.3);
            deleteLabel.setWrapText(true);
            deleteLabel.setText("Are you sure you want to delete: " +trunc(name) + "?");
            deleteYes.setVisible(true);
            deleteNo.setVisible(true);
        }
    }
    public void move(){
        moveLabel.setWrapText(true);
        if(b == null){
            moveLabel.setText("Please select a photo");
            returnMove();
        }
        else{
            String[] s = u.getAlbumNames();
            if(s.length <= 1){
                moveLabel.setText("No other albums available");
                returnMove();
            }
            else{
                moveHide.setVisible(true);
                moveConfirm.setVisible(true);
                albumChoice.setVisible(true);
            }
        }
    }

    public void moveConfirm(){
        if(albumChoice.getValue() == null){
            moveLabel.setText("Please select an album");
        }
        else{
            for(Button b : buttons){
                if(b.getText().equals(name)){
                    if(u.getAlbum(albumChoice.getValue()).hasPhoto(name)){
                        moveLabel.setText("Duplicate photo. Please select another");
                        returnMove();
                    }
                    else{
                        u.getAlbum(albumChoice.getValue()).addPhoto(a.getPhoto(name));
                        items.remove(a.getPhoto(name));
                        a.removePhoto(name);
                        vbox.getChildren().remove(b);
                        moveLabel.setWrapText(true);
                        moveLabel.setText("Photo successfully moved to: " +albumChoice.getValue());
                        moveConfirm.setVisible(false);
                        moveHide.setVisible(false);
                        albumChoice.setVisible(false);
                        returnMove();
                    }
                }
            }
        }
    }

    public void hide() {
        albumChoice.setVisible(false);
        moveConfirm.setVisible(false);
        moveHide.setVisible(false);
        deselectM();
    }

    public void copy(){
        copyLabel.setWrapText(true);
        if(b == null){
            copyLabel.setText("Please select a photo");
            returnCopy();
        }
        else{
            String[] s = u.getAlbumNames();
            if(s.length <= 1){
                copyLabel.setText("No other albums available");
                returnCopy();
            }
            else{
                copyHide.setVisible(true);
                copyConfirm.setVisible(true);
                albumChoice1.setVisible(true);
            }
        }
    }

    public void copyConfirm(){
        if(albumChoice1.getValue() == null){
            copyLabel.setText("Please select an album");
        }
        else{
            for(Button b : buttons){
                if(b.getText().equals(name)){
                    if(u.getAlbum(albumChoice1.getValue()).hasPhoto(name)){
                        copyLabel.setText("Duplicate photo. Please select another");
                        returnCopy();
                    }
                    else{
                        u.getAlbum(albumChoice1.getValue()).addPhoto(a.getPhoto(name));
                        copyLabel.setWrapText(true);
                        copyLabel.setText("Photo successfully copied to: " +albumChoice1.getValue());
                        copyConfirm.setVisible(false);
                        copyHide.setVisible(false);
                        albumChoice1.setVisible(false);
                        deselectM();
                        returnCopy();
                    }
                }
            }
        }
    }

    public void chide(){
        albumChoice1.setVisible(false);
        copyConfirm.setVisible(false);
        copyHide.setVisible(false);
        deselectM();
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

    private void returncapDelete(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> deletecapLabel.setText(""));
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
        message.setOnFinished(e -> errorLabel.setText(""));
        message.play();
    }

    private void returnCreate(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            createTagLabel.setText("");
            tagTypeInput.setVisible(true);
            multipleCheck.setVisible(true);
            tagTypeInput.setText("");
            multipleCheck.setSelected(false);
        });
        message.play();
    }

    private void returnCreateTagAlbum(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> createTagAlbumLabel.setText(""));
        message.play();
    }
    private void returnMove(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> moveLabel.setText(""));
        message.play();
    }

    private void returnAddTagLabel(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            addTagLabel.setText("");
            tagType.setVisible(true);
            tagInput.setVisible(true);
        });
        message.play();
    }

    private void returnDeleteTagLabel(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> {
            deleteTagLabel.setText("");
            tagChoice.setVisible(true);
        });
        message.play();
    }

    private void returnCopy(){
        PauseTransition message = new PauseTransition();
        message.setDuration(Duration.seconds(2));
        message.setOnFinished(e -> copyLabel.setText(""));
        message.play();
    }

    public void reYes() {
        for(Button b : buttons){
            if(b.getText().equals(name)){
                int i = vbox.getChildren().indexOf(b);
                vbox.getChildren().remove(b);
                b.setText(renameInput.getText());
                vbox.getChildren().add(i, b);
                renameYes.setVisible(false);
                renameNo.setVisible(false);
                renameLabel.setText("Photo renamed successfully");
                items.remove(a.getPhoto(name));
                a.renamePhoto(name, renameInput.getText());
                items.add(a.getPhoto(renameInput.getText()));
                deselectM();
                returnYes();
            }
        }
    }

    public void reNo() {
        renameLabel.setText("");
        renameInput.setText("");
        renameInput.setVisible(true);
        renameYes.setVisible(false);
        renameNo.setVisible(false);
        deselectM();
    }

    public void deYes(){
        deleteLabel.setText("Photo deleted successfully");
        deleteYes.setVisible(false);
        deleteNo.setVisible(false);
        for(Button b : buttons){
            if(b.getStyle().equals(("-fx-background-color:#dae7f3;"))){
                vbox.getChildren().remove(b);
            }
        }
        items.remove(a.getPhoto(name));
        a.removePhoto(name);
        returnDelete();
        deselectM();
    }

    public void deNo(){
        deleteLabel.setText("");
        deleteYes.setVisible(false);
        deleteNo.setVisible(false);
        deselectM();
    }

    public void deselect(){
        deselectM();
    }

    private void deselectM(){
        for(Button but : buttons){
            if(but.getStyle().equals(("-fx-background-color:#dae7f3;"))) {
                but.setStyle("-fx-background-color:transparent");
                name = null;
                b = null;
                tagChoice.getItems().clear();
                tagType.getItems().clear();
            }
        }
    }

    public void caption(){
        if(captionButton.isSelected()){
            tagButton.setDisable(true);
            if (tagButton.isSelected()) {
                errorLabel.setText("Please select one mode at a time");
                tagButton.setSelected(false);
                returnError();
            }
            else {
                if (buttonScroll.getContent().equals(anchorpho)) {
                    setCapLabels(items);
                } else if (buttonScroll.getContent().equals(anchortag)) {
                    errorLabel.setText("Please select one mode at a time");
                    returnError();
                    captionButton.setSelected(false);
                } else if (buttonScroll.getContent().equals(anchortagalbum)) {
                    setCapLabelsTag(searchItems);
                }
            }
        }
        else{
            tagButton.setDisable(false);
            if (photoScroll.getContent().equals(vbox)){
                setContent(anchorpho, items, buttons);
            } else if (photoScroll.getContent().equals(vbox1)) {
                setContent(anchortagalbum, searchItems, tagButtons);
            }
        }
    }

    private void setCapLabels(ObservableList<Photo> searchItems) {
        buttonScroll.setContent(anchorcap);
        for (int i = 0; i < searchItems.size(); i++) {
            for (Button button : buttons) {
                if (searchItems.get(i).getName().equals((button.getText()))) {
                    if (searchItems.get(i).getCaption() == null) {
                        button.setText(null);
                    } else {
                        button.setText(searchItems.get(i).getCaption());
                    }
                }
            }
            if (!labels.contains(searchItems.get(i).getName())) {
                labels.add(i, searchItems.get(i).getName());
            }
        }
    }

    private void setCapLabelsTag(ObservableList<Photo> searchItems) {
        for (int i = 0; i < searchItems.size(); i++) {
            for(Button b : tagButtons){
                if(searchItems.get(i).getName().equals(b.getText())){
                    if (searchItems.get(i).getCaption() == null) {
                        b.setText(null);
                    } else {
                        b.setText(searchItems.get(i).getCaption());
                    }
                }
            }
            if (!labels.contains(searchItems.get(i).getName())) {
                labels.add(i, searchItems.get(i).getName());
            }
        }
    }

    private void setContent(AnchorPane anchorpho, ObservableList<Photo> items, List<Button> buttons) {
        buttonScroll.setContent(anchorpho);
        for (Photo item : items) {
            if (item.getName().equals(labels.get(this.items.indexOf(item)))){
                    buttons.get(items.indexOf(item)).setText(labels.get(this.items.indexOf(item)));
            }
        }
    }

    private void setContentTag(AnchorPane anchorpho, ObservableList<Photo> items) {
        buttonScroll.setContent(anchorpho);
        for (Photo item : items) {
            String[] s = item.returnTags();
            StringBuilder tags = new StringBuilder();
            for(String string : s){
                tags.append(string).append("\n");
            }
            String t = tags.toString();
            for (Button button : buttons) {
                if (t.equals(button.getText()) || button.getText() == null && t.isEmpty()){
                    button.setText(labels.get(this.items.indexOf(item)));
                }
            }
        }
    }

    public void addCap(){
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
            deselectM();
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

    public void deleteCap(){
        if(b == null){
            deletecapLabel.setText("Please select a photo");
            returncapDelete();
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
                            returncapDelete();
                        }
                    }
                }
            }
        }

    }

    public void tag() {
        if(tagButton.isSelected()) {
            captionButton.setDisable(true);
            if (captionButton.isSelected()) {
                errorLabel.setText("Please select one mode at a time");
                captionButton.setSelected(false);
                returnError();
            }
            else {
                if (buttonScroll.getContent().equals(anchorpho)) {
                    buttonScroll.setContent(anchortag);
                    setLabels(items);
                } else if (buttonScroll.getContent().equals(anchorcap)) {
                    errorLabel.setText("Please select one mode at a time");
                    tagButton.setSelected(false);
                    returnError();
                } else if (buttonScroll.getContent().equals(anchortagalbum)) {
                    setLabels(searchItems);
                }
            }
        }
        else{
            captionButton.setDisable(false);
            if (photoScroll.getContent().equals(vbox)){
                setContentTag(anchorpho, items);
            } else if (photoScroll.getContent().equals(vbox1)) {
                setContentTag(anchortagalbum, searchItems);
            }
        }
    }

    private void setLabels(ObservableList<Photo> searchItems) {
        for (int i = 0; i < searchItems.size(); i++) {
            for (Button button : buttons) {
                if (searchItems.get(i).getName().equals((button.getText()))) {
                    if (searchItems.get(i).returnTags().length == 0) {
                        button.setText(null);
                    } else {
                        StringBuilder tags = new StringBuilder();
                        for (int j = 0; j < searchItems.get(i).returnTags().length; j++) {
                            tags.append(searchItems.get(i).returnTags()[j]).append("\n");
                        }
                        button.setText(tags.toString());
                    }
                }
            }
            if (!labels.contains(searchItems.get(i).getName())) {
                labels.add(i, searchItems.get(i).getName());
            }
        }
    }

    public void addTag(){
        String nn = "Please select a tag type";
        String nnn = "Please enter tag value";
        String n = "Please select a photo";
        if(b == null && tagType.getValue() == null && tagInput.getText().isEmpty()){
            tagInput.setVisible(false);
            tagType.setVisible(false);
            addTagLabel.setText(n +"\n" + nn + "\n" + nnn);
            returnAddTagLabel();
        }
        else if(b != null && tagType.getValue() == null && tagInput.getText().isEmpty()){
            tagInput.setVisible(false);
            tagType.setVisible(false);
            addTagLabel.setText(nn +"\n" + nnn);
            returnAddTagLabel();
        }
        else if(b != null && tagType.getValue() != null && tagInput.getText().isEmpty()){
            tagInput.setVisible(false);
            tagType.setVisible(false);
            addTagLabel.setText(nnn);
            returnAddTagLabel();
        }
        else if(b != null && tagType.getValue() != null && !tagInput.getText().isEmpty()){
            tagInput.setVisible(false);
            tagType.setVisible(false);
            int x = 0;
            for(int i = 0; i < buttons.size(); i++){
                if(buttons.get(i) == b){
                    x = i;
                }
            }
            if(s.canAdd(tagType.getValue(), a.getPhoto(labels.get(x)))) {
                a.getPhoto(labels.get(x)).addTag(new Tag(tagType.getValue(), tagInput.getText()));
                StringBuilder tags = new StringBuilder();
                for(int i = 0; i < a.getPhoto(labels.get(x)).returnTags().length; i++){
                    tags.append(a.getPhoto(labels.get(x)).returnTags()[i]).append("\n");
                }
                addTagLabel.setText("Tag successfully added");
                if(!s.canAdd(tagType.getValue(), a.getPhoto(labels.get(x)))){
                    tagType.getItems().remove(tagType.getValue());
                }
                b.setText(tags.toString());
                tagInput.setText("");
                tagType.setValue(null);
                deselectM();
                returnAddTagLabel();
            }
        }
        else if(b == null && tagType.getValue() != null && tagInput.getText().isEmpty()){
            tagInput.setVisible(false);
            tagType.setVisible(false);
            addTagLabel.setText(n +"\n" +nnn);
            returnAddTagLabel();
        }
        else if(b == null && tagType.getValue()!= null && !tagInput.getText().isEmpty()){
            tagInput.setVisible(false);
            tagType.setVisible(false);
            addTagLabel.setText(n);
            returnAddTagLabel();
        }
        else if(b != null && tagType.getValue() == null && !tagInput.getText().isEmpty()){
            tagInput.setVisible(false);
            tagType.setVisible(false);
            addTagLabel.setText(nn);
            returnAddTagLabel();
        }
        else if(b == null && tagType.getValue() == null && !tagInput.getText().isEmpty()){
            tagInput.setVisible(false);
            tagType.setVisible(false);
            addTagLabel.setText(n + "\n" + nn);
            returnAddTagLabel();
        }
    }

    public void deleteTag(){
        String n = "Please select a photo";
        String nn = "Please select a tag";
        if(b == null && tagChoice.getValue() == null){
            tagChoice.setVisible(false);
            deleteTagLabel.setText(n + nn);
            returnDeleteTagLabel();
        }
        else if(b == null && tagChoice.getValue() != null){
            tagChoice.setVisible(false);
            deleteTagLabel.setText(n);
            returnDeleteTagLabel();
        }
        else if(b != null && tagChoice.getValue() == null){
            tagChoice.setVisible(false);
            deleteTagLabel.setText(nn);
            returnDeleteTagLabel();
        }
        else if(b != null && tagChoice.getValue() != null){
            tagChoice.setVisible(false);
            int x = buttons.indexOf(b);
            a.getPhoto(labels.get(x)).removeTag(tagChoice.getValue());
            StringBuilder tags = new StringBuilder();
            for(int i = 0; i < a.getPhoto(labels.get(x)).returnTags().length; i++){
                tags.append(a.getPhoto(labels.get(x)).returnTags()[i]).append("\n");
            }
            b.setText(tags.toString());
            for (int k = 0; k < s.returnTagTypes().length; k++) {
                if(s.returnTagTypes()[k].equals(tagChoice.getValue().substring(0, tagChoice.getValue().indexOf(":"))))
                    if (s.canAdd(s.returnTagTypes()[k], a.getPhoto(labels.get(x)))) {
                        tagType.getItems().add(this.s.returnTagTypes()[k]);
                    }
            }
            tagChoice.getItems().remove(tagChoice.getValue());
            deleteTagLabel.setText("Tag successfully deleted");
            tagChoice.setValue(null);
            deselectM();
            returnDeleteTagLabel();
        }
    }

    public void createTag(){
        if(tagTypeInput.getText().isEmpty()){
            tagTypeInput.setVisible(false);
            multipleCheck.setVisible(false);
            createTagLabel.setText("Please enter a tag type");
            returnCreate();
        }
        else{
            if(multipleCheck.isSelected()){
                s.addtagType(tagTypeInput.getText(), 1);
            }
            else{
                s.addtagType(tagTypeInput.getText(), 0);
            }
            createTagLabel.setText("Tag type successfully added");
            tagTypeInput.setVisible(false);
            multipleCheck.setVisible(false);
            tagType.getItems().add(tagTypeInput.getText());
            returnCreate();
        }
    }

    public void enlarge() throws IOException {
        if(b == null){
            errorLabel.setText("Please select a photo");
            returnError();
        }
        else{
            Parent root;
            errorLabel.setText("Opening photo");
            Stage picWindow = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/pic.fxml"));
            root = loader.load();
            picController picController = loader.getController();
            if(buttonScroll.getContent().equals(anchorpho)) {
                picController.loadPhoto(a.getPhoto(name), a);
            }
            else if(buttonScroll.getContent().equals(anchortag) || buttonScroll.getContent().equals(anchorcap)){
                picController.loadPhoto(a.getPhoto(labels.get(buttons.indexOf(b))), a);
            }
            picController.display();
            picWindow.setTitle(name);
            picWindow.setScene(new Scene(root));
            picWindow.show();
            returnError();
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T t : list){
            if(p.test(t)){
                result.add(t);
            }
        }
        return result;
    }
    public void search() throws FileNotFoundException, ParseException {
        openPicture.setDisable(true);
        searchButton.setDisable(true);
        vbox1.setOrientation(Orientation.HORIZONTAL);
        vbox1.setHgap(5);
        vbox1.setHgap(5);
        buttonScroll.setContent(anchortagalbum);

        if(!searchInput.getText().isEmpty()){
            List<String> tags = new ArrayList<>();
            for(Photo p : items) {
                tags.addAll(Arrays.asList(p.returnTags()));
            }
            if(searchInput.getText().contains("=")){
                if(searchInput.getText().contains("and")){
                    String first = searchInput.getText().substring(0, searchInput.getText().indexOf(" and "));
                    String second = searchInput.getText().substring(searchInput.getText().indexOf("d ") + 2);
                    first = first.replace("=", ": ");
                    second = second.replace("=", ": ");
                    String finalFirst = first;
                    String finalSecond = second;
                    List<String> result = filter(tags, n -> n.equals(finalFirst));
                    List<String> sresult = filter(tags, n -> n.equals(finalSecond));
                    List<Photo> photoResult = new ArrayList<>();
                    for (String string : sresult) {
                        for (String s : result){
                            for (Photo item : items) {
                                List<String> t = Arrays.asList(item.returnTags());
                                if (t.contains(string) && t.contains(s)) {
                                    if (!photoResult.contains(item)) {
                                        photoResult.add(item);
                                    }
                                }
                            }
                        }
                    }
                    addNewBut(photoResult);
                }
                else if(searchInput.getText().contains("or")){
                    String first = searchInput.getText().substring(0, searchInput.getText().indexOf(" or "));
                    String second = searchInput.getText().substring(searchInput.getText().indexOf("or ") + 3);
                    first = first.replace("=", ": ");
                    second = second.replace("=", ": ");
                    String finalFirst = first;
                    String finalSecond = second;
                    List<String> result = filter(tags, n -> n.equals(finalFirst));
                    List<String> sresult = filter(tags, n -> n.equals(finalSecond));
                    List<Photo> photoResult = new ArrayList<>();
                    for (String string : sresult) {
                        for (String s : result){
                            for (Photo item : items) {
                                List<String> t = Arrays.asList(item.returnTags());
                                if (t.contains(string) || t.contains(s)) {
                                    if (!photoResult.contains(item)) {
                                        photoResult.add(item);
                                    }
                                }
                            }
                        }
                    }
                    addNewBut(photoResult);
                }
                else{
                    String s = searchInput.getText().replace("=", ": ");
                    List<String> result = filter(tags, n -> n.equals(s));
                    List<Photo> photoResult = new ArrayList<>();
                    getResults(result, photoResult);
                    addNewBut(photoResult);
                }
            }
            else if(searchInput.getText().contains("-")){
                if(searchInput.getText().contains("to")){
                    String first = searchInput.getText().substring(0, searchInput.getText().indexOf(" to "));
                    String second = searchInput.getText().substring(searchInput.getText().indexOf("to ") + 3);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1= df.parse(first);
                    Date date2= df.parse(second);
                    List<Photo> photoResult = new ArrayList<>();
                    for (Photo p : items) {
                        Calendar c = p.getDate();
                        c.set(Calendar.HOUR_OF_DAY, 0);
                        c.set(Calendar.MINUTE, 0);
                        c.set(Calendar.SECOND, 0);
                        Date d = c.getTime();
                        if(d.after(date1) && d.before(date2) || d.equals(date1) || d.equals(date2)){
                            photoResult.add(p);
                        }
                    }
                    addNewBut(photoResult);
                }
                else{
                    String input = searchInput.getText();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = df.parse(input);
                    List<Photo> photoResult = new ArrayList<>();
                    for (Photo p : items) {
                        Calendar c = p.getDate();
                        c.set(Calendar.HOUR_OF_DAY, 0);
                        c.set(Calendar.MINUTE, 0);
                        c.set(Calendar.SECOND, 0);
                        Date d = c.getTime();
                        if(d.equals(date)){
                            photoResult.add(p);
                        }
                    }
                    addNewBut(photoResult);
                }
            }
            photoScroll.setContent(vbox1);
        }
    }

    private void addNewBut(List<Photo> photoResult) throws FileNotFoundException {
        for(Photo p : photoResult){
            for(String l : labels){
                if(p.getName().equals(l)) {
                    Button but = buttons.get(labels.indexOf(l));
                    Button newBut = getNewBut(p, but);
                    mouseClick(newBut);
                    buttons.add(newBut);
                    vbox1.getChildren().add(newBut);
                }
            }
        }
    }

    private void getResults(List<String> sresult, List<Photo> photoResult) {
        for (String string : sresult) {
            for (Photo item : items) {
                List<String> t = Arrays.asList(item.returnTags());
                if(t.contains(string)) {
                    if(!photoResult.contains(item)) {
                        photoResult.add(item);
                    }
                }
            }
        }
    }

    private Button getNewBut(Photo p, Button but) throws FileNotFoundException {
        Button newBut = new Button();
        newBut.setText(but.getText());
        InputStream stream = new FileInputStream(p.getPath());
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        newBut.setGraphic(imageView);
        newBut.setTextAlignment(but.getTextAlignment());
        newBut.setContentDisplay(but.getContentDisplay());
        newBut.setMaxWidth(but.getMaxWidth());
        newBut.setStyle(but.getStyle());
        newBut.setWrapText(true);
        tagButtons.add(newBut);
        searchItems.add(a.getPhoto(labels.get(buttons.indexOf(but))));
        return newBut;
    }

    public void clear(){
        for(int i = 0; i < vbox1.getChildren().size(); i++){
            buttons.remove((Button)vbox1.getChildren().get(i));
        }
        tagButton.setSelected(false);
        captionButton.setSelected(false);
        vbox1.getChildren().clear();
        searchInput.setText("");
        photoScroll.setContent(vbox);
        buttonScroll.setContent(anchorpho);
        setContent(anchorpho, items, buttons);
        searchItems.clear();
        tagButtons.clear();
        openPicture.setDisable(false);
        searchButton.setDisable(false);
    }

    public void createta(){
        if(!vbox1.getChildren().isEmpty()){
            if(!searchInput.getText().isEmpty()) {
                if(!u.hasAlbum(searchInput.getText())) {
                    u.addAlbum(new Album(searchInput.getText()));
                    for (Photo p : searchItems) {
                        u.getAlbum(searchInput.getText()).addPhoto(p);
                    }
                    createTagAlbumLabel.setWrapText(true);
                    createTagAlbumLabel.setText("Album: " + searchInput.getText() + " successfully created");
                    returnCreateTagAlbum();
                }
                else{
                    createTagAlbumLabel.setWrapText(true);
                    createTagAlbumLabel.setText("Duplicate Album");
                    returnCreateTagAlbum();
                }
            }
        }
    }
}
