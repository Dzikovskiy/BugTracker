package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
     //   Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void signUpUser(String firstName, String login, String password, String email) {

        String insert = "INSERT INTO " + Const.USER_TABLE + " (" + Const.USERS_FIRSTNAME + "," + Const.USERS_LOGIN
                + "," + Const.USERS_PASSWORD + "," + Const.USERS_EMAIL + ")"
                + "VALUES ('" + firstName + "', '" + login + "', '" + password + "', '" + email + "')";
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate(insert);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
