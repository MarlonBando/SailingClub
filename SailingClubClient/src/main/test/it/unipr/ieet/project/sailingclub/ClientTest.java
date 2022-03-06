package it.unipr.ieet.project.sailingclub;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private String clientSendString(String socketMsg) throws IOException {
        Client client = new Client();
        client.ClientInit();
        String serverResponse;
        try {
            client.clientSendString(socketMsg);
            serverResponse = client.getBufferedReader().readLine();
        } catch (IOException e) {
            serverResponse="";
        }
        client.finish();
        return serverResponse;
    }

    /**
     * Verify if the login string is sent right and verify if the reception is ok
     */
    @Test
    void clientLogin(){
        String user = "userTest";
        String psw = "pswTest";
        String exceptionMessage="noException";
        String msgSocketTest = "-1" + "#" + user + "#" + psw;
        String serverResponse = "";
        try {
            serverResponse = clientSendString(msgSocketTest).trim();
        } catch (IOException e) {
            exceptionMessage = e.getMessage();
        }
        assertEquals(msgSocketTest,serverResponse);
        assertTrue(exceptionMessage.contains(exceptionMessage));
    }
}