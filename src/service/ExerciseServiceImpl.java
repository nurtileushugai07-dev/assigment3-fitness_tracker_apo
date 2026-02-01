package service;

import model.Exercise;
import repository.interfaces.CrudRepository;
import service.interfaces.ExerciseService;
import exception.*;

import java.util.List;

// SRP: Only handles exercise business logic
public class ExerciseServiceImpl implements ExerciseService {

    private final CrudRepository<Exercise> exerciseRepo;

    // DIP: Constructor injection
    public ExerciseServiceImpl(CrudRepository<Exercise> exerciseRepo) {
        this.exerciseRepo = exerciseRepo;
    }

    @Override
    public void createExercise(Exercise exercise)
            throws InvalidInputException, DuplicateResourceException, DatabaseOperationException {

        // Validation
        if (exercise.getName() == null || exercise.getName().trim().isEmpty()) {
            throw new InvalidInputException("Exercise name cannot be empty");
        }
        if (exercise.getMuscleGroup() == null || exercise.getMuscleGroup().trim().isEmpty()) {
            throw new InvalidInputException("Muscle group cannot be empty");
        }

        // Duplicate check
//        List<Exercise> existing = exerciseRepo.getAll();
//        for (Exercise e : existing) {
//            if (e.getName().equalsIgnoreCase(exercise.getName())) {
//                throw new DuplicateResourceException("Exercise with name '" + exercise.getName() + "' already exists");
//            }
//        }

        exerciseRepo.create(exercise);
    }

    @Override
    public List<Exercise> getAllExercises() throws DatabaseOperationException {
        return exerciseRepo.getAll();
    }

    @Override
    public Exercise getExerciseById(int id)
            throws ResourceNotFoundException, DatabaseOperationException {

        Exercise exercise = exerciseRepo.getById(id);
        if (exercise == null) {
            throw new ResourceNotFoundException("Exercise with id " + id + " not found");
        }
        return exercise;
    }

    @Override
    public void updateExercise(int id, Exercise exercise)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        if (exercise.getName() == null || exercise.getName().trim().isEmpty()) {
            throw new InvalidInputException("Exercise name cannot be empty");
        }

        Exercise existing = exerciseRepo.getById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Exercise with id " + id + " not found");
        }

        exerciseRepo.update(id, exercise);
    }

    @Override
    public void deleteExercise(int id)
            throws ResourceNotFoundException, DatabaseOperationException {

        Exercise existing = exerciseRepo.getById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Exercise with id " + id + " not found");
        }
        exerciseRepo.delete(id);
    }
}