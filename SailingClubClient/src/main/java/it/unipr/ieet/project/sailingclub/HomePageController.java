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
 * Classe that manage all the action avaible in the home page.
 */
public class HomePageController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String userCF = "";

    /**
     * Return to the login page
     * @param event mouse click event
     */
    @FXML
    protected void LogOutButtonPressed(MouseEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}

    }

    /**
     * Go to the payment page
     * @param event mouse click event
     */
    @FXML
    protected void PaymentTextPressed(MouseEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("payment.fxml"));
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            PaymentController controller = fxmlLoader.getController();
            controller.setUserCF(userCF);
            controller.fillList();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}

    }

    /**
     * go to the boat page
     * @param event mouse click event
     */
    @FXML
    protected void MyBoatTextPressed(MouseEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("myBoats.fxml"));
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            MyBoatsController controller = fxmlLoader.getController();
            controller.setUserCF(userCF);
            controller.fillList();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}

    }

    /**
     * go to the competition page
     * @param event mouse click event
     */
    @FXML
    protected void CompetitionTextPressed(MouseEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("competition.fxml"));
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            CompetitionController controller = fxmlLoader.getController();
            controller.setUserCF(userCF);
            controller.fillList();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}

    }

    /**
     * return the controller instance
     * @return this instance of the HomePageController class
     */
    public HomePageController getController(){
        return this;
    }

    /**
     * set the user fiscal code
     * @param userCF fiscal code
     */
    public void setUserCF(String userCF) {
        this.userCF = userCF;
    }
}
