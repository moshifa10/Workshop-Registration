package za.co.wethinkcode.examples.toyrobot.dao;

import za.co.wethinkcode.examples.toyrobot.models.Participant;
import za.co.wethinkcode.examples.toyrobot.models.Workshop;

import javax.annotation.processing.Generated;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {

    private Connection connection;

    public Dao(Connection connection){
        this.connection = connection;
    }

    public boolean insertParticipant(Participant participant){

        try {
            String sql = "INSERT INTO participants (full_name, email) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, participant.getFullName());
            preparedStatement.setString(2, participant.getEmail());
            participant.setId(preparedStatement.getGeneratedKeys().getInt(1));
            boolean isValid = preparedStatement.executeUpdate() == 1;
            participant.setId(preparedStatement.getGeneratedKeys().getInt(1));
            return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertWorkshop(Workshop workshop){

        try {
            String sql = "INSERT INTO workshops (title, description, work_date, capacity) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, workshop.getTitle()) ;
            preparedStatement.setString(2, workshop.getDescription());
            preparedStatement.setString(3, workshop.getWorkdate().toString());
            preparedStatement.setInt(4, workshop.getCapacity());
            workshop.setId(preparedStatement.getGeneratedKeys().getInt(1));
            boolean isValid = preparedStatement.executeUpdate() == 1;
            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean insertRegistration(Participant participant, Workshop workshop){

        try {
            String sql = "INSERT INTO workshops (title, description, work_date, capacity) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, workshop.getTitle()) ;
            preparedStatement.setString(2, workshop.getDescription());
            preparedStatement.setString(3, workshop.getWorkdate().toString());
            preparedStatement.setInt(4, workshop.getCapacity());
            workshop.setId(preparedStatement.getGeneratedKeys().getInt(1));
            boolean isValid = preparedStatement.executeUpdate() == 1;
            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
