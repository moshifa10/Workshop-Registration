package za.co.wethinkcode.examples.toyrobot.dao;

import za.co.wethinkcode.examples.toyrobot.models.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDao {

    private final Connection connection;

    public ParticipantDao(Connection connection) {
        this.connection = connection;

    }

    public boolean insertParticipant(Participant participant) {
        String sql = "INSERT INTO participants (full_name, email) VALUES (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, participant.getFullName());
            preparedStatement.setString(2, participant.getEmail());

            boolean isValid = preparedStatement.executeUpdate() == 1;

            if (isValid) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        participant.setId(rs.getInt(1));
                    }
                }
            }
            return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Participant findOnePartipant(int id) {
        String sql = "SELECT id, full_name, email FROM participants WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Participant participant = new Participant(
                            resultSet.getString("full_name"),
                            resultSet.getString("email")
                    );
                    participant.setId(resultSet.getInt("id"));
                    return participant;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Participant> allParticipants() {
        List<Participant> participants = new ArrayList<>();
        String sql = "SELECT id, full_name, email FROM participants";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Participant participantToAdd = new Participant(
                        resultSet.getString("full_name"),
                        resultSet.getString("email")
                );
                participantToAdd.setId(resultSet.getInt("id"));
                participants.add(participantToAdd);
            }
            return participants;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int countParticipants() {
        String sql = "SELECT COUNT(*) FROM participants";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deleteParticipant(Participant participant) {
        String sql = "DELETE FROM participants WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, participant.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
