package it.unipr.assignments.assignment1;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class extends the Thread class and allow the server to process a single client's request.
 * This class is connected to the SailinClub with jdbcs.
 */
public class ClientHandler extends Thread {
    final private BufferedReader bufferedReader;
    final private DataOutputStream dataOutputStream;
    final private ObjectOutputStream objectOutputStream;
    final private Socket socket;
    private Statement stmt;


    /**
     * This is the constructor for the ClientHandler class, he store the socket into the class and initialize all the buffer and stream.
     * @param socket this is the client socket
     * @throws IOException
     */
    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * This method will be used for each client connection and it reads from the socket a string with this format "oprationNumber#Param1#Param...#ParamN"
     * operationNumber is used in a switch to select the right function of the server.
     * 0 -> login, 2 param
     * 1 -> add a new member to the database, 5 param
     * 2 -> provide the boat list of a user, 1 param
     * 3 -> add a new boat to the database, 3 param
     * 4 -> provide the payment list of a user, 1 param
     * 5 -> update the payment state, 1 param
     * 6 -> provide the upcoming competition list, 1 param
     * 7 -> competition registration, 3 param
     * 8 -> add a payment that will be pay from a user, 4 param
     * 9 -> add a new competition, 3 param
     */
    @Override
    public void run(){
        int response;
        try {
            connectionInit();
                while (true){
                    System.out.println(socket);
                    String msgSocket = bufferedReader.readLine().trim();
                    String[] msgSocketStringArray =  msgSocket.split("#");
                    response = -1;
                    if(msgSocket.equals("END"))
                        break;
                    switch (msgSocketStringArray[0]){
                        case "0" : {
                            if(msgSocketStringArray.length != 3){
                                break;
                            }else{
                                response = login(msgSocketStringArray[1],msgSocketStringArray[2]);
                            }
                        }break;
                        case "1" : {
                            if(msgSocketStringArray.length != 6)
                                break;
                            else
                                response = newMember(msgSocketStringArray[1],msgSocketStringArray[2],msgSocketStringArray[3],msgSocketStringArray[4],msgSocketStringArray[5]);
                        }break;
                        case "2" : {
                            if(msgSocketStringArray.length != 2)
                                break;
                            else
                                sendBoatList(msgSocketStringArray[1]);
                        }break;
                        case "3" : {
                            if(msgSocketStringArray.length != 4)
                                break;
                            else
                                response = addBoat(msgSocketStringArray[1],msgSocketStringArray[2], Integer.parseInt(msgSocketStringArray[3]));
                        }break;
                        case "4" : {
                            if(msgSocketStringArray.length != 2)
                                break;
                            else
                                sendPaymentList(msgSocketStringArray[1]);
                        }break;
                        case"5" : {
                            if(msgSocketStringArray.length != 2)
                                break;
                            else
                                response = pay(Integer.parseInt(msgSocketStringArray[1]));
                        }break;
                        case"6" : {
                            if(msgSocketStringArray.length != 2)
                                break;
                            else
                                sendCompetitionList(msgSocketStringArray[1],LocalDate.now().toString());
                        }break;
                        case "7" : {
                            if(msgSocketStringArray.length != 3)
                                break;
                            else
                                response = raceRegistration(msgSocketStringArray[1],Integer.parseInt(msgSocketStringArray[2]));
                        }break;
                        case "8" : {
                            if(msgSocketStringArray.length!=5)
                                break;
                            else
                                response = addPayment(msgSocketStringArray[1],msgSocketStringArray[2],msgSocketStringArray[3],msgSocketStringArray[4]);
                        }break;
                        case "9" : {
                            if(msgSocketStringArray.length != 4)
                                break;
                            else
                                response = addCompetition(msgSocketStringArray[1],msgSocketStringArray[2],msgSocketStringArray[3]);
                        }
                        default:break;
                    }
                    returnResult(response);
                }
            socket.close();
        } catch (IOException | SQLException | NullPointerException e) {
            //e.printStackTrace();
        }
    }

    /**
     * This method insert a new competition into the SailingClub database.
     * @param name name of the competition
     * @param price price of the competition
     * @param date date of the competition
     * @return an integer that provide the status of the request: 1 = success, 0 = not success , -1 = error
     */
    private int addCompetition(String name, String price, String date) {
        String addCompetitionQuery = "INSERT INTO competition (name,price,date) VALUES ('"+name+"',"+price+",'"+date+"')";
        try {
            return stmt.executeUpdate(addCompetitionQuery);

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This method add a new payment into the database.
     * @param CF fiscal code of the partner that have to pay.
     * @param amount how mouch money the partner has to pay
     * @param deadLine dead line of the payment
     * @param description description of the payment
     * @return an integer that provide the status of the request: 1 = success, 0 = not success , -1 = error
     */
    private int addPayment(String CF, String amount, String deadLine, String description) {
        String controlQuery = "Select * FROM partner WHERE fiscalCode = '"+CF+"'";
        try {
            ResultSet rs = stmt.executeQuery(controlQuery);
            if(!rs.next())
                return 0;
            rs.close();
            String addPaymentQuery = "INSERT INTO payments (fcPartner,deadLine,description,amount) VALUES ('"+CF+"','"+deadLine+"','Iscrizione a "+description+"',"+Float.parseFloat(amount)+")";
            stmt.executeUpdate(addPaymentQuery);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This method update the status of a payment from NOT PAYED to PAYED.
     * @param idPayment the identification number of a payment in the database
     * @return an integer that provide the status of the request: 1 = success, 0 = not success , -1 = error
     */
    private int pay(int idPayment) {
        String controlQuery = "SELECT idPayment FROM payments WHERE state=TRUE AND idPayment = " + idPayment ;
        try {
            ResultSet rs = stmt.executeQuery(controlQuery);
            if(!rs.next()){
                String paymentDate = LocalDate.now().toString();
                String paymentQuery = "UPDATE payments SET state=TRUE,paymentDate='"+paymentDate+"' WHERE idPayment = " + idPayment;
                System.out.println(paymentQuery);
                try {
                    stmt.executeUpdate(paymentQuery);
                } catch (SQLException e) {
                    return -1;
                }
                return 1;
            }else{
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This method add a new Parner into the database.
     * @param nome partner's name
     * @param password partner's password
     * @param surname partner's surname
     * @param CF partner's fiscal code
     * @param address partner's address
     * @return an integer that provide the status of the request: 1 = success, 0 = not success , -1 = error
     */
    private int newMember(String nome,String password,String surname,String CF, String address){
        try{
            String controlQuery = "SELECT fiscalCode FROM partner WHERE fiscalCode='"+ CF + "'";
            System.out.println(controlQuery);
            ResultSet rs = stmt.executeQuery(controlQuery);
            if(!rs.next()){
                String registrationQuery = "INSERT INTO partner (fiscalCode,password,name,surname,address) VALUES ('"+CF+"','"+password+"','" +nome+"','"+surname+"','"+address+"')";
                System.out.println(registrationQuery);
                stmt.executeUpdate(registrationQuery);
                return 1;
            }else{
                return 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    /**
     * Authenticate partners who have attempted to sign in
     * @param CF fiscal code provided by the login form
     * @param psw password provided by the login form
     * @return an integer that provide the status of the request: 1 = authenticated, 0 = password or cf wrong , -1 = error
     */
    private int login(String CF,String psw){
        try {
            String loginQuery = "SELECT fiscalCode,isAdmin FROM partner WHERE fiscalCode='"+ CF + "' AND password = '"+psw+"'";
            ResultSet rs = stmt.executeQuery(loginQuery);
            if(rs.next()){
                if(rs.getBoolean("isAdmin"))
                    return 2;
                return 1;
            }else{
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This method add a new boat into the database
     * @param fcOwner fiscal code of the owner's boat
     * @param boatName boat name
     * @param length length of the boat
     * @return an integer that provide the status of the request: 1 = success, 0 = not success , -1 = error
     */
    private int addBoat(String fcOwner,String boatName,int length){
        System.out.println(fcOwner + " " + boatName + " " + length);
        String controlQuery = "SELECT idBoat from boats WHERE fcOwner='"+fcOwner+"' AND name='"+boatName+"'";
        try {
            ResultSet rs = stmt.executeQuery(controlQuery);
            if (!rs.next()) {
                String addBoatQuery = "INSERT INTO boats (name,fcOwner,length) VALUES ('" + boatName + "','" + fcOwner + "'," + length+ ")";
                System.out.println(addBoatQuery);
                stmt.executeUpdate(addBoatQuery);
                return 1;
            } else {
                return 0;
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    /**
     * This method registers a partner in a contest and automatically notifies them of the corresponding payment.
     * @param CF fiscal code of the partner
     * @param idCompetition identification number of the competition
     * @return an integer that provide the status of the request: 1 = success, 0 = not success , -1 = error
     */
    private int raceRegistration(String CF,int idCompetition){
        String controlQuery = "SELECT idRegistration FROM registrations JOIN partner ON fcPartner=fiscalCode JOIN competition ON fkCompetition = idCompetition WHERE fiscalCode = '"+CF+"' AND idCompetition = '"+idCompetition+"'";
        String competitionName;
        float price;
        String date;
        try{
            ResultSet rs = stmt.executeQuery("Select name,date,price from competition where idCompetition="+idCompetition);
            if(rs.next()){
                competitionName = rs.getString("name");
                price = rs.getFloat("price");
                date= rs.getDate("date").toString();
            }else{
                return -1;
            }
            rs.close();
            rs = stmt.executeQuery(controlQuery);
            if(!rs.next()){
                rs.close();
                String addRegistrationQuery = "INSERT INTO registrations (fcPartner,fkCompetition) VALUES ('"+ CF+"','"+idCompetition+"')";
                stmt.executeUpdate(addRegistrationQuery);
                String addPaymentQuery = "INSERT INTO payments (fcPartner,deadLine,description,amount) VALUES ('"+CF+"','"+date+"','Iscrizione a "+competitionName+"',"+price+")";
                stmt.executeUpdate(addPaymentQuery);
                return 1;
            }else{
                return 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }


    }

    /**
     * This method send through the socket a String list with all the boat information. It sends it through a ObjectOutputStream
     * This is the format of the string : "idBoat#boatName#boatlength"
     * @param CF fiscal code of the owner's boats
     */
    private void sendBoatList(String CF){
        System.out.println("CF: " + CF);
        String getBoatListQuery = "SELECT idBoat,name,length FROM boats WHERE fcOwner = '"+ CF +"'";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery(getBoatListQuery);
            while(rs.next()){
                String listItem = rs.getInt("idBoat") + "#" + rs.getString("name") + "#" +rs.getInt("length");
                list.add(listItem);
            }
            objectOutputStream.writeObject(list);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method send through the socket a String list with all the payment information. It sends it through a ObjectOutputStream
     * This is the format of the string : "idPayment#Paymentdescription#PaymentdeadLine#Paymentamount#PaymentState"
     * @param CF fiscal code of the owner's boats
     */
    private void sendPaymentList(String CF){
        System.out.println("CF: " + CF);
        String getPaymentListQuery = "SELECT idPayment,description,deadLine,paymentDate,amount,state FROM payments WHERE fcPartner = '"+ CF +"'";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery(getPaymentListQuery);
            while(rs.next()){
                String listItem = rs.getString("idPayment") +"#"+ rs.getString("description") + "#" + rs.getString("deadLine") + "#" + rs.getString("paymentDate") + "#" + rs.getFloat("amount") + "#" + rs.getBoolean("state");
                list.add(listItem);
            }
            objectOutputStream.writeObject(list);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Provide a string list of all the upcoming competition. It sends it through a ObjectOutputStream
     * This is the format of the string : "idCompetition#CompetitionName#CompetitionPrice#CompetitionDate#RegistrationStatus"
     * @param CF fiscalCode of the partner that send the request
     * @param date the local date of the request in this format "Year-Month-Day"
     */
    public void sendCompetitionList(String CF,String date){
        List<String> list = new ArrayList<>();
        String getRegistrationListQuery = "Select fkCompetition from registrations where fcPartner = '"+CF+"'";
        String getCompetitionListQuery = "Select * from competition WHERE date > '"+date+"'";
        try {
            ResultSet rsRegistration = stmt.executeQuery(getRegistrationListQuery);
            List<Integer> idRegistrationList = new ArrayList<>();
            while (rsRegistration.next()){
                idRegistrationList.add(rsRegistration.getInt("fkCompetition"));
            }
            rsRegistration.close();
            ResultSet rs = stmt.executeQuery(getCompetitionListQuery);
            while (rs.next()){
                int idCompetition = rs.getInt("idCompetition");
                String state = "NON iscritto";
                for(int i=0;i<idRegistrationList.toArray().length;i++){
                    if(idCompetition == (int)idRegistrationList.toArray()[i]){
                        state="Iscritto";
                        break;
                    }
                }
                list.add(idCompetition + "#" +rs.getString("name") + "#" + rs.getFloat("price") + "#" + rs.getDate("date").toString() + "#" + state);
            }
        objectOutputStream.writeObject(list);
        }catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void returnResult(int feedaback) throws IOException {
        dataOutputStream.write(feedaback);
        dataOutputStream.flush();
    }

    private void connectionInit() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sailingclub", "root", "SailingClub");
        stmt = connection.createStatement();
    }
}
