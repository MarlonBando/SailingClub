package it.unipr.ieet.project.sailingclub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that managed all the available action int the myBoat page
 */
public class MyBoatsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String userCF;
    private Client client = new Client();

    ObservableList<Boat> list = FXCollections.observableArrayList(
    );

    @FXML
    private TableView<Boat> boatTable;

    @FXML
    private TableColumn<Boat,Integer> id;

    @FXML
    private TableColumn<Boat,String> boatName;

    @FXML
    private TableColumn<Boat,Float> length;

    @FXML
    private TextField boatNameTextField;

    @FXML
    private TextField lengthTextField;

    @FXML
    private Button moorButton;

    /**
     * Back to the home page
     * @param event mouse click event
     */
    @FXML
    protected void returnToHomePage(MouseEvent event){
        try {
            client.finish();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            HomePageController controller = fxmlLoader.getController();
            controller.setUserCF(userCF);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}
    }


    /**
     * Initialize the communication with the server and initialize the table.
     * @param url resource url
     * @param rb local-specific objects
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            client.ClientInit();
        } catch (IOException |NullPointerException e) {
            e.printStackTrace();
            return;
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        boatName.setCellValueFactory(new PropertyValueFactory<>("boatName"));
        length.setCellValueFactory(new PropertyValueFactory<>("length"));
        boatTable.setItems(list);
    }

    /**
     * File the boat list and the table list with the items received by the server
     */
    public void fillList(){
        try {
            list = client.getBoatsList(userCF);
            System.out.println(list);
            boatTable.setItems(list);
        }catch (NullPointerException e){
            e.printStackTrace();
            return;
        }
    }

    /**
     * Method that send to the server the request to add a new boat
     */
    @FXML
    protected void moor(){
        String name = boatNameTextField.getText();
        try{
            int length = Integer.parseInt(lengthTextField.getText());
            System.out.println(userCF + " " + name + " " + length);
            client.addBoat(userCF,name,length);
        }catch (NumberFormatException ex){
            lengthTextField.setText("FORMAT NON VALIDO");
        }
    }

    /**
     * @return this instanche of the MyBoatsController class
     */
    public MyBoatsController getController(){
        return this;
    }

    /**
     * @return the session fiscal code
     */
    public String getUserCF() {
        return userCF;
    }

    /**
     * Set the session fiscal code
     * @param userCF fiscal code
     */
    public void setUserCF(String userCF) {
        this.userCF = userCF;
    }
}