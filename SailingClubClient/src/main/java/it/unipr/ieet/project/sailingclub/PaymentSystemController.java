package it.unipr.ieet.project.sailingclub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class managed all the payment operations
 */
public class PaymentSystemController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String userCF = "";
    private Client client = new Client();
    private int idPayment;

    @FXML
    TextField cardNumber;
    @FXML
    TextField cardOwner;
    @FXML
    TextField deadLineCard;
    @FXML
    PasswordField cvc;
    @FXML
    Text logMessage;


    /**
     * Initialize the server communication
     * @param url resource url
     * @param resourceBundle local-specific object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client.ClientInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go back to the previous page
     * @param event mouse click event
     */
    @FXML
    protected void backButtonPressed(MouseEvent event){
        try {
            client.finish();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("payment.fxml"));
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            PaymentController controller = fxmlLoader.getController();
            controller.setUserCF(userCF);
            controller.fillList();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException|NullPointerException ex){

        }

    }

    /**
     * Pay and display the result of the payment
     * @param e button pressed event
     */
    @FXML
    protected void payButtonPressed(ActionEvent e){
        int result = client.pay(idPayment);
        displayLogMessage(result);
    }

    private void displayLogMessage(int result){
        if(result == 1){
            logMessage.setText("Pagameto riuscito");
        }else if(result == 0){
            logMessage.setText("Pagamento già effettuato");
            logMessage.setFill(Paint.valueOf("RED"));
        }else{
            logMessage.setText("Errore nel pagamento");
            logMessage.setFill(Paint.valueOf("RED"));
        }
        logMessage.setVisible(true);
    }

    /**
     * method that permit to choose a pdf that represent a vat payment.
     * @param e button pressed event
     */
    @FXML
    protected void vatButtonPressed(ActionEvent e){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        fileChooser.setTitle("Seleziona la ricevuta del bonifico");
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            int result = client.pay(idPayment);
            displayLogMessage(result);
        }else{
            logMessage.setText("File non valido");
            logMessage.setVisible(true);
        }

    }

    /**
     * @return this controller instance
     */
    public PaymentSystemController getController(){
        return this;
    }

    /**
     * Set the identification number of the payment
     * @param idPayment payment identifier number
     */
    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    /**
     * Set the session fiscal code
     * @param userCF fiscal code
     */
    public void setUserCF(String userCF) {
        this.userCF = userCF;
    }
}
