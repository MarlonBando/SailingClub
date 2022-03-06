package it.unipr.ieet.project.sailingclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Class that managed the admin home page available action.
 */
public class AdminHomePageController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Go to the new partner registration page
     * @param event Mouse click event
     */
    @FXML
    protected void addUserTextPressed(MouseEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}
    }

    /**
     * Go to the add payment page
     * @param event mouse click event
     */
    @FXML
    protected void addPaymentTextPressed(MouseEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addPayment.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}

    }

    /**
     * Go to the add competition page
     * @param event mouse click event
     */
    @FXML
    protected void addCompetitionTextPressed(MouseEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addCompetition.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}

    }

    /**
     * Go back to the login page
     * @param event mouse click event
     */
    @FXML
    protected void logOutButtonPressed(MouseEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}

    }

    /**
     * @return this instance of the AdminHomePageController
     */
    public AdminHomePageController getController(){
        return this;
    }

}
