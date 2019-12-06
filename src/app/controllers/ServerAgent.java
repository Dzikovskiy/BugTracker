package app.controllers;

import app.ClientSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ServerAgent {

    public static void sendDataToServer(String out) {
        try {
            Socket socket = ClientSocket.getSocket();
            OutputStream os = socket.getOutputStream();
            os.write(out.getBytes());
            System.out.println("Data sent to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDataFromServer() {
        String data = "";
        try {
            data = QueryHandler.getQuery(ClientSocket.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }


}
