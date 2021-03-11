package sk.kosickaakademia.kristian.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    public Connection getConnection() throws IOException, SQLException {
        Properties prop = new Properties();
        InputStream loader = getClass().getClassLoader().getResourceAsStream("dat.properties");
        prop.load(loader);
        String url = "jdbc:mysql://localhost:3306/jokes";//prop.getProperty("url");
        String user = "root";//prop.getProperty("name");
        String pass = "";//prop.getProperty("password");
        Connection connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }

}
