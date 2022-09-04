package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {

    private static Connection connection = null;

    //Limit access to connect factory
    private ConnectionFactory()
    {}

    public static Connection getConnection()
    {
        if(connection == null) {
            System.out.println("Connection is being created!");

            //read data from .properties file
            ResourceBundle bundle = ResourceBundle.getBundle("DbConfig");

            String url = bundle.getString("url");
            String username = bundle.getString("username");
            String password = bundle.getString("password");

            try
            {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, username, password);
            }
            catch (SQLException e)
            {
                System.out.println("Something went wrong while connecting to database");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Something went wrong while connecting to database");
            }
        }
        return connection;
    }
}
