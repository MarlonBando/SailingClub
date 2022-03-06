package it.unipr.ieet.project.sailingclub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Login controller classe managed all the action avaible in the login page
 */
public class LoginController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Client client;
    private Boolean clientInitialize = false;

    @FXML
    private Text errorMessage;

    @FXML
    private TextField usrTextField;

    @FXML
    private PasswordField pswTextField;

    /**
     * Method that extract the login information such fiscal code and password and sends the information to the server for the user autentication the user.
     * @param event Button pressed event
     */
    @FXML
    protected void LoginButtonPressed(ActionEvent event){
        int requestStatus = client.clientLogin(usrTextField.getText(),pswTextField.getText());
        if(requestStatus > 0){
            String page = "homePage.fxml";
            if(requestStatus == 2){
                page = "adminHomePage.fxml";
            }else if(requestStatus != 1){
                return;
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(page));
                root = fxmlLoader.load();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                if(requestStatus == 1){
                    HomePageController controller = fxmlLoader.<HomePageController>getController();
                    controller.setUserCF(usrTextField.getText());
                }else{
                    AdminHomePageController controller = fxmlLoader.<AdminHomePageController>getController();
                }
                client.finish();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch (IOException e){}

        }else{
            errorMessage.setVisible(true);
        }
    }

    /**
     * Initialization method that initialize tha Client class used to send the information to the server.
     * @param url resource url
     * @param resourceBundle local-specific object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = new Client();
        try {
            client.ClientInit();
        } catch (IOException e) {
            errorMessage.setText("Non riesco a connettermi al server");
            errorMessage.setVisible(true);
        }
    }
}
