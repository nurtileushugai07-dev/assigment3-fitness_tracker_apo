package service;

import model.CardioWorkout;
import model.StrengthWorkout;
import repository.CardioWorkoutRepository;
import repository.StrengthWorkoutRepository;
import exception.*;

import java.util.List;

public class WorkoutService {
    private CardioWorkoutRepository cardioRepo;
    private StrengthWorkoutRepository strengthRepo;

    public WorkoutService() {
        this.cardioRepo = new CardioWorkoutRepository();
        this.strengthRepo = new StrengthWorkoutRepository();
    }

    // Cardio Workout Methods
    public void createCardioWorkout(CardioWorkout workout)
            throws InvalidInputException, DatabaseOperationException {
        if (!workout.validate()) {
            throw new InvalidInputException("Invalid cardio workout data");
        }
        cardioRepo.create(workout);
    }

    public List<CardioWorkout> getAllCardioWorkouts() throws DatabaseOperationException {
        return cardioRepo.getAll();
    }

    public CardioWorkout getCardioWorkoutById(int id)
            throws ResourceNotFoundException, DatabaseOperationException {
        CardioWorkout workout = cardioRepo.getById(id);
        if (workout == null) {
            throw new ResourceNotFoundException("Cardio workout with id " + id + " not found");
        }
        return workout;
    }

    public void updateCardioWorkout(int id, CardioWorkout workout)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {
        if (!workout.validate()) {
            throw new InvalidInputException("Invalid cardio workout data");
        }
        CardioWorkout existing = cardioRepo.getById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Cardio workout with id " + id + " not found");
        }
        cardioRepo.update(id, workout);
    }

    public void deleteCardioWorkout(int id)
            throws ResourceNotFoundException, DatabaseOperationException {
        CardioWorkout existing = cardioRepo.getById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Cardio workout with id " + id + " not found");
        }
        cardioRepo.delete(id);
    }

    // Strength Workout Methods
    public void createStrengthWorkout(StrengthWorkout workout)
            throws InvalidInputException, DatabaseOperationException {
        if (!workout.validate()) {
            throw new InvalidInputException("Invalid strength workout data");
        }
        strengthRepo.create(workout);
    }

    public List<StrengthWorkout> getAllStrengthWorkouts() throws DatabaseOperationException {
        return strengthRepo.getAll();
    }

    public StrengthWorkout getStrengthWorkoutById(int id)
            throws ResourceNotFoundException, DatabaseOperationException {
        StrengthWorkout workout = strengthRepo.getById(id);
        if (workout == null) {
            throw new ResourceNotFoundException("Strength workout with id " + id + " not found");
        }
        return workout;
    }

    public void updateStrengthWorkout(int id, StrengthWorkout workout)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {
        if (!workout.validate()) {
            throw new InvalidInputException("Invalid strength workout data");
        }
        StrengthWorkout existing = strengthRepo.getById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Strength workout with id " + id + " not found");
        }
        strengthRepo.update(id, workout);
    }

    public void deleteStrengthWorkout(int id)
            throws ResourceNotFoundException, DatabaseOperationException {
        StrengthWorkout existing = strengthRepo.getById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Strength workout with id " + id + " not found");
        }
        strengthRepo.delete(id);
    }
}