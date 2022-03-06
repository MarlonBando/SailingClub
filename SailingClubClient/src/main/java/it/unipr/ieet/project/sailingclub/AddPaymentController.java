package it.unipr.ieet.project.sailingclub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class that send the request to the server to add a new payment
 */
public class AddPaymentController {
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Client client = new Client();

    @FXML
    TextField fiscalCode;
    @FXML
    TextField amount;
    @FXML
    DatePicker deadLine;
    @FXML
    TextArea description;
    @FXML
    Text errorMessage;


    /**
     * method that send the request to add a new payment and display the result
     * @param event button pressed event
     */
    @FXML
    protected void addPaymentButtonPressed(ActionEvent event){
        try {
            client.ClientInit();
            String CF = fiscalCode.getText();
            Float amountPayment = Float.parseFloat(amount.getText());
            LocalDate date = deadLine.getValue();
            String descriptionPayment = description.getText();
            client.addPayment(CF,amountPayment,date.toString(),descriptionPayment);
        } catch (NumberFormatException e){
            amount.setText("ERRORE FORMATO");
            e.printStackTrace();
        } catch (IOException e){
            errorMessage.setVisible(true);
            e.printStackTrace();
        }
    }

    /**
     * Go to the previous page
     * @param event Mouse clicked event
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
