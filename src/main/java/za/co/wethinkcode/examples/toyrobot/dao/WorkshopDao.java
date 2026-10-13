package za.co.wethinkcode.examples.toyrobot.dao;

import za.co.wethinkcode.examples.toyrobot.models.Participant;
import za.co.wethinkcode.examples.toyrobot.models.Workshop;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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



    public Workshop findOneWorkShop(int id){

        try {
            String sql = "SELECT * FROM workshops WHERE id = " + "'" + id + "'";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);



            while (resultSet.next()){
                String[] date = resultSet.getString(4).split("-");
                Workshop workshop = new Workshop(resultSet.getString(2), resultSet.getString(3), (LocalDate.of(Integer.getInteger(date[0]), Integer.getInteger(date[1]), Integer.getInteger(date[2]))), resultSet.getInt(5));
                workshop.setId(resultSet.getInt(1));
                return workshop;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<Participant> allParticipants (){
        try {
            List<Participant> participants =  new ArrayList<>();
            String sql = "SELECT * FROM participants";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                participants.add(new Participant(resultSet.getString(1),resultSet.getString(2)));
            }
            return participants.size() > 0 ? participants : null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    public int countRegistrations(){
        try {
            String sql = "SELECT COUNT(*) FROM participants";
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql).getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean deleteParticipant(Participant participant){

        try {
            String sql = "DELETE FROM participants WHERE id = " + "'" + participant.getId() + "'";

            Statement statement = connection.createStatement();
            return statement.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
