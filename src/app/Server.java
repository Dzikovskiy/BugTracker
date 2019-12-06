package app;

import app.controllers.QueryHandler;
import app.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    private ServerSocket socket;
    private DatabaseHandler handler;

    public static void main(String[] args) {

        Server server = new Server();
        server.initServer();
        server.start();

    }


    private void start() {
        System.out.println("Server started.");
        while (true) {
            try {
                Socket client = socket.accept();
                System.out.println("Client connected");
                Runnable runnable = () -> {

                    OutputStream outputStream;
                    InputStream inputStream;
                    try {
                        outputStream = client.getOutputStream();
                        inputStream = client.getInputStream();

                        String clientCommand;
                        boolean exitFlag = true;// become true after .END

                        while (exitFlag) {
                            clientCommand = QueryHandler.getQuery(inputStream);

                            if (clientCommand.equalsIgnoreCase(Commands.END)) {
                                exitFlag = false;
                            } else if (clientCommand.equalsIgnoreCase(Commands.SIGN_UP)) {
                                signUpUser(QueryHandler.getQuery(inputStream));
                            } else if (clientCommand.equalsIgnoreCase(Commands.LOGIN)) {
                                loginUser(outputStream, QueryHandler.getQuery(inputStream));
                            }

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };


                new Thread(runnable).start();//starting of new server thread if new client connected

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }



    private void loginUser(OutputStream outputStream, String queryContent) throws IOException {
        String[] arr = queryContent.split(" ");
        String login = arr[0];
        String password = arr[1];
        if (handler.loginUser(login, password)) {
            outputStream.write("logged".getBytes());
        } else {
            outputStream.write("error".getBytes());
        }
    }


    private void signUpUser(String queryContent) {
        String[] arr = queryContent.split(" ");
        String firstName = arr[0];
        String login = arr[1];
        String password = arr[2];
        String email = arr[3];

        User user = new User(firstName, login, password, email);

        handler.signUpUser(user);
    }

    private void initServer() {
        handler = new DatabaseHandler();//connecting to the database
        try {
            socket = new ServerSocket(1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (socket != null) {
            System.out.println("Server initialized.");
        }
    }
}
