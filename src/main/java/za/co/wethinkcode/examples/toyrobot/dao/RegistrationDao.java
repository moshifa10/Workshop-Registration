package za.co.wethinkcode.examples.toyrobot.dao;

import za.co.wethinkcode.examples.toyrobot.models.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationDao {
    private Connection connection;

    public RegistrationDao(Connection connection){
        this.connection = connection;
    }

    public boolean insertRegistration(Registration registration){

        try {
            String sql = "INSERT INTO workshops (participant_id, workshop_id, registered_at) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, registration.getParticipant().getId()) ;
            preparedStatement.setInt(2, registration.getWorkshop().getId());
            preparedStatement.setString(3, registration.getRegistered_at().toString());
            boolean isValid = preparedStatement.executeUpdate() == 1;
            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
