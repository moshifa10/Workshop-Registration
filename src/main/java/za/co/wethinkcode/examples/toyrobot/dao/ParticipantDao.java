package za.co.wethinkcode.examples.toyrobot.dao;

import za.co.wethinkcode.examples.toyrobot.models.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ParticipantDao{

    private Connection connection;

    public ParticipantDao(Connection connection){
        this.connection = connection;
    }

    public boolean insert(Participant participant){

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
}
