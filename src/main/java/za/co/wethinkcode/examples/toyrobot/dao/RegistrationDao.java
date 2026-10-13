package za.co.wethinkcode.examples.toyrobot.dao;

import za.co.wethinkcode.examples.toyrobot.models.Participant;
import za.co.wethinkcode.examples.toyrobot.models.Registration;
import za.co.wethinkcode.examples.toyrobot.models.Workshop;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDao {
    private final Connection connection;

    // Standard base JOIN query used across findOne and allRegistrations to eliminate redundant logic
    private final String BASE_JOIN_SQL =
            "SELECT p.id AS participant_id, p.full_name, p.email, " +
                    "w.id AS workshop_id, w.title, w.description_text, w.work_date, w.capacity, " +
                    "r.id AS registration_id " +
                    "FROM registrations r " +
                    "JOIN participants p ON r.participant_id = p.id " +
                    "JOIN workshops w ON r.workshop_id = w.id ";

    public RegistrationDao(Connection connection) {
        this.connection = connection;
    }

    public boolean insertRegistration(Registration registration) {
        // FIX: Table name changed to registrations instead of workshops
        String sql = "INSERT INTO registrations (participant_id, workshop_id, registered_at) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, registration.getParticipant().getId());
            preparedStatement.setInt(2, registration.getWorkshop().getId());
            preparedStatement.setString(3, registration.getRegistered_at().toString());

            boolean isValid = preparedStatement.executeUpdate() == 1;

            // Extra step: Capture the newly assigned ID from the database and keep the object updated
            if (isValid) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        registration.setId(rs.getInt(1)); // If your setter has a different name use that here
                    }
                }
            }
            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Registration findOneRegistration(int id) {
        // FIX: Filtering query condition strictly by registration's unique row ID
        String sql = BASE_JOIN_SQL + "WHERE r.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            // FIX: Removed passing 'sql' string parameter inside executeQuery()
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToRegistration(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Registration> allRegistrations() {
        List<Registration> registrations = new ArrayList<>();

        // FIX: Executes complete table join to build fully fleshed out domain models
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(BASE_JOIN_SQL)) {

            while (resultSet.next()) {
                registrations.add(mapRowToRegistration(resultSet));
            }
            return registrations;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countWorkshops() {
        String sql = "SELECT COUNT(*) FROM workshops";
        // FIX: Auto-closing the statement context using try-with-resources block
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

    public boolean deleteWorkShop(Workshop workshop) {
        // FIX: Avoided SQL injection risks by adapting raw statement logic to a PreparedStatement
        String sql = "DELETE FROM workshops WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, workshop.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Registration mapRowToRegistration(ResultSet resultSet) throws SQLException {
        int regId = resultSet.getInt("registration_id");
        int partId = resultSet.getInt("participant_id");
        int workId = resultSet.getInt("workshop_id");
        int capacity = resultSet.getInt("capacity");

        String descriptionText = resultSet.getString("description_text");
        String fullName = resultSet.getString("full_name");
        String email = resultSet.getString("email");
        String title = resultSet.getString("title");

        String rawDate = resultSet.getString("work_date");
        LocalDate localDate = null;

        if (rawDate != null) {
            String[] workshopdate = rawDate.split("-");
            localDate = LocalDate.of(
                    Integer.parseInt(workshopdate[0]),
                    Integer.parseInt(workshopdate[1]),
                    Integer.parseInt(workshopdate[2])
            );
        }

        Participant participant = new Participant(partId, fullName, email);
        Workshop workshop = new Workshop(workId, title, descriptionText, localDate, capacity);

        return new Registration(regId, participant, workshop);
    }
}
