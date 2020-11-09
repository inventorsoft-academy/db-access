package co.inventorsoft.jdbc.configuration;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
@Configuration
public class Driver {

    public static Statement createStatement() {
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            try (Connection conn = getConnection()){

                System.out.println("Connection to Store DB succesfull!");
            }
            return getConnection().createStatement();
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
            return null;
        }
    }

    public static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }

}
