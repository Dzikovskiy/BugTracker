package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.Socket;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ClientSocket.getSocket();
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("BugTracker");
        primaryStage.setScene(new Scene(root, 800, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
