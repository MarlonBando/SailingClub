package it.unipr.ieet.project.sailingclub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that manged all the available action in the competition page
 */
public class CompetitionController implements Initializable {
    private String userCF;

    @FXML
    private TableView<Competition> competitionTable;

    @FXML
    private TableColumn<Competition,Integer> idCompetition;

    @FXML
    private TableColumn<Competition,String> name;

    @FXML
    private TableColumn<Competition,Float> price;

    @FXML
    private TableColumn<Competition,String> state;

    ObservableList<Competition> list = FXCollections.observableArrayList();

    private final Client client = new Client();

    /**
     * Initialize the communication with the server and initialize the table
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
        idCompetition.setCellValueFactory(new PropertyValueFactory<>("idCompetition"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        competitionTable.setItems(list);
    }

    /**
     * Fill the list and the table with the items send by the server
     */
    public void fillList(){
        try {
            list = client.getCompetitionList(userCF);
            competitionTable.setItems(list);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * Send to the server the request to register the client to a competition
     */
    @FXML
    protected void raceRegistration(){
        Competition competition = competitionTable.getSelectionModel().getSelectedItem();
        int idCompetition = competition.getIdCompetition();
        int result = client.raceRegistration(userCF,idCompetition);
    }

    /**
     * Back to the home page
     * @param event mouse click event
     */
    @FXML
    protected void returnToHomePage(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            HomePageController controller = fxmlLoader.getController();
            controller.setUserCF(userCF);
            client.finish();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}
    }

    /**
     * set the session fiscal code
     * @param userCF session fiscal code
     */
    public void setUserCF(String userCF) {
        this.userCF = userCF;
    }

    /**
     * @return this instance of CompetitionController
     */
    public CompetitionController getController(){
        return this;
    }
}
