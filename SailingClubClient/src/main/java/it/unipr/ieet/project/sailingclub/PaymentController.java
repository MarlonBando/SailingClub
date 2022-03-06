package it.unipr.ieet.project.sailingclub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe that manage all the action avaible in the payment page.
 */
public class PaymentController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String userCF;
    private int idPayment;
    private final Client client = new Client();

    @FXML
    private TableView<Payment> paymentTable;

    @FXML
    private TableColumn<Payment,String> description;

    @FXML
    private TableColumn<Payment,String> deadLine;

    @FXML
    private TableColumn<Payment,String> paymentDate;

    @FXML
    private TableColumn<Payment,CheckBox> state;

    @FXML
    private TableColumn<Payment,Float> amount;

    ObservableList<Payment> list = FXCollections.observableArrayList();

    /**
     * Return to the Home page.
     * @param event mouse click event
     */
    @FXML
    protected void returnToHomePage(MouseEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            HomePageController controller = fxmlLoader.getController();
            controller.setUserCF(userCF);
            client.finish();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){}
    }

    /**
     * Initialize the payment table and initilize the comunication with the server.
     * @param url resource url
     * @param rb local-specific object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            client.ClientInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        deadLine.setCellValueFactory(new PropertyValueFactory<>("deadLine"));
        paymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        paymentTable.setItems(list);
    }

    /**
     * Go to the payment system page.
     * @param event button pressed event
     */
    @FXML
    protected void pay(ActionEvent event){
        try {
            Payment payment = paymentTable.getSelectionModel().getSelectedItem();
            idPayment = payment.getIdPayment();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        System.out.println(idPayment);
        try{
            client.finish();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paymentSystem.fxml"));
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            PaymentSystemController controller = fxmlLoader.getController();
            controller.setIdPayment(idPayment);
            controller.setUserCF(userCF);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * set the session fical code
     * @param userCF fiscal code
     */
    public void setUserCF(String userCF) {
        this.userCF = userCF;
    }

    /**
     * @return this PaymentController object
     */
    public PaymentController getController(){
        return this;
    }

    /**
     * Fill list and add the list items to the table
     */
    public void fillList(){
        try {
            list = client.getPaymentList(userCF);
            paymentTable.setItems(list);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
