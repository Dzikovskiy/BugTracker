package app;

import app.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket socket;
    private DatabaseHandler handler;

    public static void main(String[] args) {

        Server server = new Server();
        server.initServer();
        server.start();

    }

    private static String getQuery(InputStream inputStream) throws IOException {
        byte[] clientMessage = new byte[100];
        int messageLength = inputStream.read(clientMessage);
        String queryContent = new String(clientMessage, 0, messageLength);
        queryContent = queryContent.trim();   // TODO check if func .trim() ok
        return queryContent;
    }

    private void start() {
        System.out.println("Server started.");
        while (true) {
            try {
                Socket client = socket.accept();

                Runnable runnable = () -> {

                    OutputStream outputStream;
                    InputStream inputStream;
                    try {
                        outputStream = client.getOutputStream();
                        inputStream = client.getInputStream();

                        String clientAction;
                        String queryContent;
                        boolean exitFlag = true;// become true after Action.END

                        while (exitFlag) {
                            clientAction = getQuery(inputStream);

                            if (clientAction.equalsIgnoreCase(Actions.END)) {
                                exitFlag = false;
                            } else if (clientAction.equalsIgnoreCase(Actions.SIGN_UP)) {
                                signUpUser(getQuery(inputStream));
                            } else if (clientAction.equalsIgnoreCase(Actions.LOGIN)) {
                                loginUser(outputStream, getQuery(inputStream));
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
