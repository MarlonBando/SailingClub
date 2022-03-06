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

/**
 * Class used to send the request to add a new competition
 */
public class AddCompetitionController {
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Client client = new Client();

    @FXML
    TextField competitionName;
    @FXML
    TextField competitionPrice;
    @FXML
    DatePicker competitionDate;
    @FXML
    Text errorMessage;


    /**
     * Method that send the request to create a new competition and display the result
     * @param event button pressed event
     */
    @FXML
    protected void createButtonPressed(ActionEvent event){
        try {
            client.ClientInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = competitionName.getText();
        try{
            Float price = Float.parseFloat(competitionPrice.getText());
            String date = competitionDate.getValue().toString();
            int response = client.addCompetition(name,price,date);
            if(response == 1){
                errorMessage.setText("Competizione creata con successo");
            }else if(response == 0){
                errorMessage.setText("Competizione già esistente");
            }else{
                errorMessage.setText("Errore comunicazione server");
            }
            errorMessage.setVisible(true);
        }catch (NumberFormatException e){
            competitionPrice.setText("ERRORE FORMATO");
        }

    }

    /**
     * Return to the previous page
     * @param event mouse clicked event
     */
    @FXML
    protected void BackTextPressed(MouseEvent event){
        try {
            client.finish();
            Parent root = FXMLLoader.load(getClass().getResource("adminHomePage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException|NullPointerException ex){}

    }
}
