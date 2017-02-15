package net.proselyte.pmsystem.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Configuration class that provide application with Database Connection.
 *
 * @author Oleksii Samantsov
 */
public class ConnectionUtil {
    public static Connection getConnection(){
        Properties properties = new Properties();
        FileInputStream inputStream = null;
        Connection connection = null;
        try{
            inputStream = new FileInputStream("db.properties");
            properties.load(inputStream);
            Class.forName(properties.getProperty("DB_DRIVER"));
            connection = DriverManager.getConnection(properties.getProperty("DB_URL"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }
}
