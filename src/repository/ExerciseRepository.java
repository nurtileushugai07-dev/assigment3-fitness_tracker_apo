package repository;

import model.Exercise;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseRepository {

    public void create(Exercise exercise) throws DatabaseOperationException {
        String sql = "INSERT INTO exercises (name, muscle_group, equipment_needed) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, exercise.getName());
            stmt.setString(2, exercise.getMuscleGroup());
            stmt.setString(3, exercise.getEquipmentNeeded());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                exercise.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error creating exercise", e);
        }
    }

    public List<Exercise> getAll() throws DatabaseOperationException {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM exercises";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Exercise exercise = new Exercise(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("muscle_group"),
                        rs.getString("equipment_needed")
                );
                exercises.add(exercise);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error fetching exercises", e);
        }

        return exercises;
    }

    public Exercise getById(int id) throws DatabaseOperationException {
        String sql = "SELECT * FROM exercises WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Exercise(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("muscle_group"),
                        rs.getString("equipment_needed")
                );
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error fetching exercise by id", e);
        }

        return null;
    }

    public void update(int id, Exercise exercise) throws DatabaseOperationException {
        String sql = "UPDATE exercises SET name = ?, muscle_group = ?, equipment_needed = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, exercise.getName());
            stmt.setString(2, exercise.getMuscleGroup());
            stmt.setString(3, exercise.getEquipmentNeeded());
            stmt.setInt(4, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error updating exercise", e);
        }
    }

    public void delete(int id) throws DatabaseOperationException {
        String sql = "DELETE FROM exercises WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error deleting exercise", e);
        }
    }
}