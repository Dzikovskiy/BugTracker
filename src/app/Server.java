package app;

import app.controllers.QueryHandler;
import app.model.Task;
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
                            } else if (clientCommand.equalsIgnoreCase(Commands.SAVE_TASK)) {
                                saveTask(outputStream, QueryHandler.getQuery(inputStream));
                            } else if (clientCommand.equalsIgnoreCase(Commands.GET_TASKS)) {
                                sendTasks(outputStream);
                            } else if (clientCommand.equalsIgnoreCase(Commands.MOVE_TASK)) {
                                moveTask(outputStream, QueryHandler.getQuery(inputStream));
                            } else if (clientCommand.equalsIgnoreCase(Commands.DELETE_TASK)) {
                                deleteTask(outputStream, QueryHandler.getQuery(inputStream));
                            } else if (clientCommand.equalsIgnoreCase(Commands.EDIT_TASK)) {
                                editTask(outputStream, QueryHandler.getQuery(inputStream));
                            } else if (clientCommand.equalsIgnoreCase(Commands.BACK_TASK)) {
                                backTask(outputStream, QueryHandler.getQuery(inputStream));
                            } else if (clientCommand.equalsIgnoreCase(Commands.TRASH_TASK)) {
                                trashTask(outputStream, QueryHandler.getQuery(inputStream));
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

    private void trashTask(OutputStream outputStream, String query) throws IOException {
        String[] data = query.split(" ");
        if (handler.trashTask(data[0])) {
            outputStream.write("trashed".getBytes());
            System.out.println("Tasks moved to trash");
        } else {
            outputStream.write("error".getBytes());
        }
    }

    private void backTask(OutputStream outputStream, String query) throws IOException {
        String[] data = query.split(" ");
        if (handler.backTask(data[0])) {
            outputStream.write("backed".getBytes());
            System.out.println("Tasks backed");
        } else {
            outputStream.write("error".getBytes());
        }
    }

    private void editTask(OutputStream outputStream, String query) throws IOException {
        String[] dataCommaSplitted = query.split(",");
        String[] data = dataCommaSplitted[1].split(" ");
        String taskData = dataCommaSplitted[0];
        String creator = data[0];
        String id = data[1];
        if (handler.editTask(taskData, creator, id)) {
            outputStream.write("edited".getBytes());
        } else {
            outputStream.write("error".getBytes());
        }
    }

    private void deleteTask(OutputStream outputStream, String query) throws IOException {
        String[] data = query.split(" ");
        if (handler.deleteTask(data[0])) {
            outputStream.write("deleted".getBytes());
            System.out.println("Tasks deleted");
        } else {
            outputStream.write("error".getBytes());
        }
    }

    private void moveTask(OutputStream outputStream, String query) throws IOException {

        String[] data = query.split(" ");
        Task task = new Task();
        task.setId(data[0]);
        task.setStage(data[1]);
        if (handler.moveTask(task)) {
            outputStream.write("edited".getBytes());
            System.out.println("Tasks edited");
        } else {
            outputStream.write("error".getBytes());
        }

    }

    private void sendTasks(OutputStream outputStream) throws IOException {
        // String dataString = "";
        StringBuilder builderString = new StringBuilder(1024);
        if (handler.getTasks(builderString)) {
            outputStream.write("tasks".getBytes());
            outputStream.write(builderString.toString().getBytes());
            System.out.println("Tasks sent from server");
        } else {
            outputStream.write("error".getBytes());
        }
    }

    private void saveTask(OutputStream outputStream, String query) throws IOException {
        String[] dataCommaSplitted = query.split(",");
        String[] data = dataCommaSplitted[1].split(" ");
        String taskData = dataCommaSplitted[0];
        String creator = data[0];

        if (handler.saveTask(taskData, creator)) {
            outputStream.write("saved".getBytes());
        } else {
            outputStream.write("error".getBytes());
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
