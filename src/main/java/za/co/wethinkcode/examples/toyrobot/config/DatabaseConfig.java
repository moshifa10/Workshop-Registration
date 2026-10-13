package za.co.wethinkcode.examples.toyrobot.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {

    private Connection connection;

    public DatabaseConfig(){
        try {
            this.connection = DriverManager.getConnection("JDBC:sqlite:workshop_registration.db");
            this.createParticipantsTable(this.connection);
            this.createWorkshopsTable(this.connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection connection() {
        return this.connection;
    }

    public boolean createParticipantsTable(Connection connection){

        try {
            Statement statement = connection.createStatement();
            Path path = Path.of("sql/participants.sql");
            String sql = Files.readString(path);
            return statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createWorkshopsTable(Connection connection){
        try {
            Statement statement = connection.createStatement();
            Path path = Path.of("sql/workshops.sql");
            String sql = Files.readString(path);
            return statement.execute(sql);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
