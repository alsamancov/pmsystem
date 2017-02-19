package net.proselyte.pmsystem.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Configuration class that provide application with Database Connection.
 *
 * @author Oleksii Samantsov
 */
public class ConnectionUtil {
    public static Connection connection;
    public static Statement statement;
    public static PreparedStatement preparedStatement;


    public static Connection getConnection(){
        Properties properties = new Properties();
        FileInputStream inputStream = null;
        try{
            inputStream = new FileInputStream("db.properties");
            properties.load(inputStream);
            Class.forName(properties.getProperty("DB_DRIVER"));
            try {
                connection = DriverManager.getConnection(properties.getProperty("DB_URL"),
                        properties.getProperty("DB_USERNAME"),
                        properties.getProperty("DB_PASSWORD"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found " + e);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }

    public static void closePreparedStatement(){
        if(preparedStatement != null){
            try{
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
}
