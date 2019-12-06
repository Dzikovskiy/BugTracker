package app;

import app.animations.Shaker;
import app.controllers.SceneOpener;
import app.model.Task;
import app.model.User;

import java.net.Socket;
import java.sql.*;

public class DatabaseHandler extends Configs {
    private Connection dbConnection;

    private Connection getDbConnection() throws SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
                + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";//SET GLOBAL time_zone = '+3:00';

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void insetTask(Task task) {

        String insert = "INSERT INTO " + Const.TASK_TABLE + " (" + Const.TASKS_TASK + "," + Const.TASKS_CREATOR
                + "," + Const.TASKS_COLUMN + ")"
                + "VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, task.getTask());
            preparedStatement.setString(2, task.getCreator());
            preparedStatement.setString(3, String.valueOf(task.getColumn()));

            preparedStatement.executeUpdate();

            System.out.println("Task added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTasks() {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.TASK_TABLE;

        try {
            Statement statement = getDbConnection().createStatement();
            resultSet = statement.executeQuery(select);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE "
                + Const.USERS_LOGIN + "=? AND " + Const.USERS_PASSWORD + "=?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public boolean loginUser(String loginText, String passwordText) {

        User user = new User();
        user.setLogin(loginText);
        user.setPassword(passwordText);

        ResultSet resultSet = getUser(user);

        int counter = 0;

        try {
            while (resultSet.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (counter >= 1) {
            System.out.println("User logged in");
            return true;
        } else {
            System.out.println("Login error: wrong login or password ");
            return false;

        }

    }

    public void signUpUser(User user) {

        String insert = "INSERT INTO " + Const.USER_TABLE + " (" + Const.USERS_FIRSTNAME + "," + Const.USERS_LOGIN
                + "," + Const.USERS_PASSWORD + "," + Const.USERS_EMAIL + ")"
                + "VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());

            preparedStatement.executeUpdate();

            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
