package it.unipr.ieet.project.sailingclub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.*;
import java.util.List;

/**
 * Class that provide an interface to comunicate the information to the server
 */
public class Client {
    private int port;
    private String host;
    private Socket socket;
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;
    DataOutputStream dataOutputStream = null;
    ObjectInputStream objectInputStream = null;


    /**
     * Consntructor with selectable address and port
     * @param address ip address of the server
     * @param port port where the server provide the service
     */
    Client(String address,int port){
        this.port = port;
        this.host =address;
    }

    /**
     * Default contructor. port=11000 and host='localhost'
     */
    Client(){
        port=11000;
        host ="localhost";
    }


    /**
     * initialize all the buffer and stream used to comunicate with the socket
     * @throws IOException socket error
     */
    public void ClientInit() throws IOException {
        socket = new Socket(host,port);
        inputStreamReader = new InputStreamReader(socket.getInputStream());
        bufferedReader = new BufferedReader(inputStreamReader);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Method that sends the string to the server
     * @param socketMsg string sended to the server
     * @return an Integer that contains the result of the request
     */
    public int clientSendString(String socketMsg){
        int returnCode = -1;
        try {
            System.out.println("socketMessage: " + socketMsg);
            dataOutputStream.writeUTF(socketMsg+"\n");
            returnCode = bufferedReader.read();
            System.out.println("returnCode: " + returnCode);
        } catch (IOException e) {
            return -1;
        }
        return returnCode;
    }

    /**
     * Method that send the autentication request to the server
     * @param user user identifier (in this case the fiscal Code)
     * @param psw password of the user
     * @return the result of the request (1->success , 0->user or psw wrong, -1->comunication error)
     */
    public int clientLogin(String user,String psw){
        String socketMsg = "0" + "#" + user + "#" + psw;
        int result = clientSendString(socketMsg);
        if(result==1){
            try {
                this.finish();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Method used to send the request for the registration of a new partner
     * @param name name of the new partner
     * @param surname surname of the new partner
     * @param psw psw of the new partner
     * @param cf fiscal code of the new partner
     * @param address address of the new partner
     * @return the result of the request
     */
    public int clientUserRegistration(String name,String surname,String psw,String cf,String address){
        String socketMsg = "1" + "#" + name + "#" + surname + "#" + psw + "#" + cf + "#" + address;
        return clientSendString(socketMsg);
    }

    /**
     * Method that provide a list of all the boat owned by the partner identified by de fiscal code
     * @param CF partner's fiscal code
     * @return a list that cantains all the boats of the partner
     */
    public ObservableList<Boat> getBoatsList(String CF){
        List<String> stringList = null;
        ObservableList<Boat> list = FXCollections.observableArrayList();
        try {
            dataOutputStream.writeUTF("2" + "#" + CF + "\n");
        } catch (IOException e) {
            return null;
        }
        try {
            stringList = (List<String>)objectInputStream.readObject();
        }catch (IOException|ClassNotFoundException ex){
            ex.printStackTrace();
        }
        for (int i=0;i<stringList.toArray().length;i++){
            String[] line = stringList.get(i).split("#");
            list.add(new Boat(Integer.parseInt(line[0]),line[1],Integer.parseInt(line[2])));
        }
        return list;
    }

    /**
     * Method that provide a list of all the payment that the partner identified by the fiscal code has to pay
     * @param CF partne's fiscal code
     * @return a list that contains all the partner's payment
     */
    public ObservableList<Payment> getPaymentList(String CF){
        List<String> stringList = null;
        ObservableList<Payment> list = FXCollections.observableArrayList();
        try {
            dataOutputStream.writeUTF("4" + "#" + CF + "\n");
        } catch (IOException e) {
            return null;
        }
        try {
            stringList = (List<String>)objectInputStream.readObject();
        }catch (IOException|ClassNotFoundException ex){
            ex.printStackTrace();
        }
        for (int i=0;i<stringList.toArray().length;i++){
            String[] line = stringList.get(i).split("#");
            list.add(new Payment(Integer.parseInt(line[0]),line[1],line[2],line[3],Float.parseFloat(line[4]),Boolean.parseBoolean(line[5])));
        }
        return list;
    }

    /**
     * Method that provide all the competition and wich of them the partner identified by the fiscal code are registered to.
     * @param CF partne's fiscal code
     * @return a list that contains all the competition list
     */
    public ObservableList<Competition> getCompetitionList(String CF){
        List<String> stringList = null;
        ObservableList<Competition> list = FXCollections.observableArrayList();
        try {
            dataOutputStream.writeUTF("6" + "#" + CF + "\n");
        } catch (IOException e) {
            return null;
        }
        try {
            stringList = (List<String>)objectInputStream.readObject();
        }catch (IOException|ClassNotFoundException ex){
            ex.printStackTrace();
        }
        for (int i=0;i<stringList.toArray().length;i++){
            String[] line = stringList.get(i).split("#");
            list.add(new Competition(Integer.parseInt(line[0]),line[1],Float.parseFloat(line[2]),line[3],line[4]));
        }
        return list;
    }

    /**
     * Method that send the payment request to the server
     * @param idPayment integer that identify a payment
     * @return the result of the request
     */
    public int pay(int idPayment){
        String msgSocket = "5" + "#" + idPayment;
        return clientSendString(msgSocket);
    }

    /**
     * Method that sends the request for adding a new boat
     * @param CF fiscal code of the owner of the boat
     * @param name name of the boat
     * @param length length of the boat
     * @return the result of the request
     */
    public int addBoat(String CF,String name,int length){
        String socketMsg = "3" + "#" + CF + "#" + name +"#"+ length;
        return clientSendString(socketMsg);
    }

    /**
     * Method that sends a race registration request
     * @param CF partner's fiscal code
     * @param idCompetition identifier of the competition
     * @return the result of the request
     */
    public int raceRegistration(String CF,int idCompetition){
        String socketMsg = "7" + "#" + CF + "#" + idCompetition;
        return clientSendString(socketMsg);
    }

    /**
     * Methods that sends the request to add a new payment
     * @param CF partner's fiscal code that has to pay
     * @param amount amount of the payment
     * @param deadLine deadline of the payment
     * @param description description of the payment
     * @return the result of the request
     */
    public int addPayment(String CF,Float amount,String deadLine,String description){
        String socketMsg = "8" + "#" + CF + "#" + amount + "#" + deadLine + "#" + description;
        return clientSendString(socketMsg);
    }

    /**
     * send the request to add a new competition
     * @param name name of the competition
     * @param amount registration price
     * @param date date of the competition
     * @return the result of the request
     */
    public int addCompetition(String name,Float amount,String date){
        String socketMsg = "9" + "#" + name + "#" + amount + "#" + date;
        return clientSendString(socketMsg);
    }

    /**
     * close the socket
     * @throws IOException socket error
     */
    public void finish() throws IOException {
        socket.close();
    }

    /**
     * @return the buffered reader
     */
    public BufferedReader getBufferedReader(){
        return bufferedReader;
    }

    /**
     * @return the output stream
     */
    public DataOutput getDataOutputStream() {
        return dataOutputStream;
    }
}
