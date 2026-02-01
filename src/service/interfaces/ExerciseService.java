package service.interfaces;

import exception.*;
import model.Exercise;

import java.util.List;

public interface ExerciseService {
    void createExercise(Exercise exercise) throws InvalidInputException, DuplicateResourceException, DatabaseOperationException;
    List<Exercise> getAllExercises() throws DatabaseOperationException;
    Exercise getExerciseById(int id) throws ResourceNotFoundException, DatabaseOperationException;
    void updateExercise(int id, Exercise exercise) throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;
    void deleteExercise(int id) throws ResourceNotFoundException, DatabaseOperationException;
}