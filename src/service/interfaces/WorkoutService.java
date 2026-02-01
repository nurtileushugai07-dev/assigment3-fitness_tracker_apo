package service.interfaces;

import exception.*;
import model.CardioWorkout;
import model.StrengthWorkout;

import java.util.List;

// DIP: Controller depends on this interface, not concrete class
public interface WorkoutService {

    // Cardio CRUD
    void createCardioWorkout(CardioWorkout workout) throws InvalidInputException, DuplicateResourceException, DatabaseOperationException;
    List<CardioWorkout> getAllCardioWorkouts() throws DatabaseOperationException;
    CardioWorkout getCardioWorkoutById(int id) throws ResourceNotFoundException, DatabaseOperationException;
    void updateCardioWorkout(int id, CardioWorkout workout) throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;
    void deleteCardioWorkout(int id) throws ResourceNotFoundException, DatabaseOperationException;

    // Strength CRUD
    void createStrengthWorkout(StrengthWorkout workout) throws InvalidInputException, DuplicateResourceException, DatabaseOperationException;
    List<StrengthWorkout> getAllStrengthWorkouts() throws DatabaseOperationException;
    StrengthWorkout getStrengthWorkoutById(int id) throws ResourceNotFoundException, DatabaseOperationException;
    void updateStrengthWorkout(int id, StrengthWorkout workout) throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;
    void deleteStrengthWorkout(int id) throws ResourceNotFoundException, DatabaseOperationException;

    // Find minimum using getAll()
    CardioWorkout getCardioWithMinDuration() throws DatabaseOperationException;
    StrengthWorkout getStrengthWithMinWeight() throws DatabaseOperationException;
}