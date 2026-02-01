package repository;

import model.StrengthWorkout;
import repository.interfaces.CrudRepository;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StrengthWorkoutRepository implements CrudRepository<StrengthWorkout> {

    @Override
    public void create(StrengthWorkout workout) throws DatabaseOperationException {
        String sql = "INSERT INTO strength_workouts (name, duration_minutes, calories_burned, sets, reps, weight_kg) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, workout.getName());
            stmt.setInt(2, workout.getDurationMinutes());
            stmt.setInt(3, workout.getCaloriesBurned());
            stmt.setInt(4, workout.getSets());
            stmt.setInt(5, workout.getReps());
            stmt.setDouble(6, workout.getWeightKg());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                workout.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error creating strength workout", e);
        }
    }

    @Override
    public List<StrengthWorkout> getAll() throws DatabaseOperationException {
        List<StrengthWorkout> list = new ArrayList<>();
        String sql = "SELECT * FROM strength_workouts";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error fetching strength workouts", e);
        }
        return list;
    }

    @Override
    public StrengthWorkout getById(int id) throws DatabaseOperationException {
        String sql = "SELECT * FROM strength_workouts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error fetching strength workout by id", e);
        }
        return null;
    }

    @Override
    public void update(int id, StrengthWorkout workout) throws DatabaseOperationException {
        String sql = "UPDATE strength_workouts SET name = ?, duration_minutes = ?, calories_burned = ?, sets = ?, reps = ?, weight_kg = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, workout.getName());
            stmt.setInt(2, workout.getDurationMinutes());
            stmt.setInt(3, workout.getCaloriesBurned());
            stmt.setInt(4, workout.getSets());
            stmt.setInt(5, workout.getReps());
            stmt.setDouble(6, workout.getWeightKg());
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error updating strength workout", e);
        }
    }

    @Override
    public void delete(int id) throws DatabaseOperationException {
        String sql = "DELETE FROM strength_workouts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error deleting strength workout", e);
        }
    }

    private StrengthWorkout mapRow(ResultSet rs) throws SQLException {
        return new StrengthWorkout(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("duration_minutes"),
                rs.getInt("calories_burned"),
                rs.getInt("sets"),
                rs.getInt("reps"),
                rs.getDouble("weight_kg")
        );
    }
}