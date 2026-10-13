package za.co.wethinkcode.examples.toyrobot.dao;

import za.co.wethinkcode.examples.toyrobot.models.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDao{

    private Connection connection;

    public ParticipantDao(Connection connection){
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

    public Participant findOnePartipant(int id){

        try {
            String sql = "SELECT * FROM participants WHERE id = " + "'" + id + "'";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Participant participant = new Participant(resultSet.getString(2), resultSet.getString(3));
                participant.setId(resultSet.getInt(1));
                return participant;
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
                Participant participantToAdd = new Participant(resultSet.getString(2), resultSet.getString(3));
                participantToAdd.setId(resultSet.getInt(1));
                participants.add(participantToAdd);
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
