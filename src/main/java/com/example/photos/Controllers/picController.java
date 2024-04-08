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

/**
 * Handles pic.fxml
 * @author Gigna
 */
public class picController {
    @FXML
    Label tags;
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
    public void display() throws FileNotFoundException {
        InputStream stream = new FileInputStream(p.getPath());
        Image i = new Image(stream);
        photoView.setImage(i);
        photoView.setPreserveRatio(true);
        caption.setWrapText(true);
        caption.setText(p.getCaption());
        StringBuilder tags = new StringBuilder();
        for(int j = 0; j < p.returnTags().length; j++){
            tags.append(p.returnTags()[j]).append("\n");
        }
        this.tags.setWrapText(true);
        this.tags.setText(tags.toString());
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
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
