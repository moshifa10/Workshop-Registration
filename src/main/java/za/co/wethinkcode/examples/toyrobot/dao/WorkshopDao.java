package za.co.wethinkcode.examples.toyrobot.dao;

import za.co.wethinkcode.examples.toyrobot.models.Workshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class WorkshopDao {
    private Connection connection;

    public WorkshopDao(Connection connection){
        this.connection = connection;
    }

    public boolean insertWorkshop(Workshop workshop){

        try {
            String sql = "INSERT INTO workshops (title, description, work_date, capacity) VALUES (?,?,?,?)";
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
