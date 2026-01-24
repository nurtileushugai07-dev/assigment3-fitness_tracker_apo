package repository;

import model.CardioWorkout;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardioWorkoutRepository {

    public void create(CardioWorkout workout) throws DatabaseOperationException {
        String sql = "INSERT INTO cardio_workouts (name, duration_minutes, calories_burned, distance_km, average_heart_rate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, workout.getName());
            stmt.setInt(2, workout.getDurationMinutes());
            stmt.setInt(3, workout.getCaloriesBurned());
            stmt.setDouble(4, workout.getDistanceKm());
            stmt.setInt(5, workout.getAverageHeartRate());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                workout.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error creating cardio workout", e);
        }
    }

    public List<CardioWorkout> getAll() throws DatabaseOperationException {
        List<CardioWorkout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM cardio_workouts";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CardioWorkout workout = new CardioWorkout(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("duration_minutes"),
                        rs.getInt("calories_burned"),
                        rs.getDouble("distance_km"),
                        rs.getInt("average_heart_rate")
                );
                workouts.add(workout);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error fetching cardio workouts", e);
        }

        return workouts;
    }

    public CardioWorkout getById(int id) throws DatabaseOperationException {
        String sql = "SELECT * FROM cardio_workouts WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CardioWorkout(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("duration_minutes"),
                        rs.getInt("calories_burned"),
                        rs.getDouble("distance_km"),
                        rs.getInt("average_heart_rate")
                );
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error fetching cardio workout by id", e);
        }

        return null;
    }

    public void update(int id, CardioWorkout workout) throws DatabaseOperationException {
        String sql = "UPDATE cardio_workouts SET name = ?, duration_minutes = ?, calories_burned = ?, distance_km = ?, average_heart_rate = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, workout.getName());
            stmt.setInt(2, workout.getDurationMinutes());
            stmt.setInt(3, workout.getCaloriesBurned());
            stmt.setDouble(4, workout.getDistanceKm());
            stmt.setInt(5, workout.getAverageHeartRate());
            stmt.setInt(6, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error updating cardio workout", e);
        }
    }

    public void delete(int id) throws DatabaseOperationException {
        String sql = "DELETE FROM cardio_workouts WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error deleting cardio workout", e);
        }
    }
}