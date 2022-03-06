package it.unipr.assignments.assignment1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main Class that rappresent the Saling Club Server
 */
public class Main {
    /**
     * Port number where the server receive clients.
     */
    private static final int PORT_NUMBER = 11000;

    /**
     * @param args command line, unused
     *
     * This is the server main loop where it accept all the client, for each connection a thread will be used.
     */
    public static void main(String[] args) {
        try{
            ServerSocket socket = new ServerSocket(PORT_NUMBER);
            while(true){
                Socket clientSocket = socket.accept();
                Thread t = new ClientHandler(clientSocket);
                t.run();
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
