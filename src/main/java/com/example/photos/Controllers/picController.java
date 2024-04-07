package com.example.photos.Controllers;

import com.example.photos.Model.Album;
import com.example.photos.Model.Photo;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;

public class picController {
    @FXML
    Label caption;
    @FXML
    Label date;
    @FXML
    ImageView photoView;
    Photo p;
    Album a;
    Stage stage;
    Parent root;
    Scene scene;
    public void display() throws FileNotFoundException {
        InputStream stream = new FileInputStream(p.getPath());
        Image i = new Image(stream);
        photoView.setImage(i);
        photoView.setPreserveRatio(true);
        caption.setWrapText(true);
        caption.setText(p.getCaption());
        SimpleDateFormat d = new SimpleDateFormat("yyyy-mm-dd");
        date.setText(d.format(p.getDate().getTime()));
    }

    public void loadPhoto(Photo p, Album a){
        this.a = a;
        this.p = p;
    }

    public void openshow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/slides.fxml"));
        root = loader.load();
        slideshowController slideshowController = loader.getController();
        slideshowController.loadSystem(a, p);
        slideshowController.display();
        slideshowController.getpreScene(caption.getScene());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
