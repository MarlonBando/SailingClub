package it.unipr.ieet.project.sailingclub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class used for the registration of a new partner
 */
public class SignUpController{
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Client client = new Client();

    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField password;
    @FXML
    private TextField fiscalCode;
    @FXML
    private TextField address;
    @FXML
    private Text errorMessage;

    /**
     * Send to the server the new partner's information and display the result of the request
     * @param event button pressed event
     */
    @FXML
    protected void SignUpButtonPressed(ActionEvent event){
        try {
            client.ClientInit();
        }catch (IOException e){}
        int response = client.clientUserRegistration(name.getText(),surname.getText(),password.getText(),fiscalCode.getText(),address.getText());
        if(response == 1){
            errorMessage.setText("Utente aggiunto con successo");
            errorMessage.setVisible(true);
        }else if(response == 0){
            fiscalCode.setText("Codice Fiscale già utilizzato");
        }else{
            errorMessage.setText("Non riesco a connettermi");
            errorMessage.setVisible(true);
        }
    }

    /**
     * Back to the previous page
     * @param event mouse click event
     */
    @FXML
    protected void BackTextPressed(MouseEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminHomePage.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException|NullPointerException ex){}

    }
}
