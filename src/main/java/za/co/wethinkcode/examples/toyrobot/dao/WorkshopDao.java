package za.co.wethinkcode.examples.toyrobot.dao;

import za.co.wethinkcode.examples.toyrobot.models.Workshop;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkshopDao {
    private final Connection connection;

    public WorkshopDao(Connection connection) {
        this.connection = connection;
    }

    public boolean insertWorkshop(Workshop workshop) {
        String sql = "INSERT INTO workshops (title, description, work_date, capacity) VALUES (?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, workshop.getTitle());
            preparedStatement.setString(2, workshop.getDescription());

            if (workshop.getWorkdate() != null) {
                preparedStatement.setString(3, workshop.getWorkdate().toString());
            } else {
                preparedStatement.setNull(3, Types.VARCHAR);
            }
            preparedStatement.setInt(4, workshop.getCapacity());

            boolean isValid = preparedStatement.executeUpdate() == 1;

            if (isValid) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        workshop.setId(rs.getInt(1));
                    }
                }
            }
            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Workshop findOneWorkShop(int id) {
        String sql = "SELECT id, title, description, work_date, capacity FROM workshops WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToWorkshop(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Workshop> allWorkShops() {
        List<Workshop> workshops = new ArrayList<>();
        String sql = "SELECT id, title, description, work_date, capacity FROM workshops";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                workshops.add(mapRowToWorkshop(resultSet));
            }
            return workshops;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int countWorkshops() {
        String sql = "SELECT COUNT(*) FROM workshops";

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
        String sql = "DELETE FROM workshops WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, workshop.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Workshop mapRowToWorkshop(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        int capacity = resultSet.getInt("capacity");

        String rawDate = resultSet.getString("work_date");
        LocalDate localDate = null;

        if (rawDate != null && !rawDate.isBlank()) {
            String[] dateParts = rawDate.split("-");
            if (dateParts.length == 3) {
                localDate = LocalDate.of(
                        Integer.parseInt(dateParts[0]),
                        Integer.parseInt(dateParts[1]),
                        Integer.parseInt(dateParts[2])
                );
            }
        }

        Workshop workshop = new Workshop(title, description, localDate, capacity);
        workshop.setId(id);
        return workshop;
    }
}
