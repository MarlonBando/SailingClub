package it.unipr.ieet.project.sailingclub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Sailing Club Application class that extends Application class
 */
public class SailingClubApplication extends Application {
    /**
     * Display the first page
     * @param stage application stage
     */
    @Override
    public void start(Stage stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SailingClubApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image(SailingClubApplication.class.getResource("img/logo.jpg").toString());
            stage.setTitle("SailingClub");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}
    }

    /**
     * Main method that launch the application
     * @param args command line, not used
     */
    public static void main(String[] args) {
        launch();
    }
}