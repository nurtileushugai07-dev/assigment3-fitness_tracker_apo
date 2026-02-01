package service;

import model.CardioWorkout;
import model.StrengthWorkout;
import repository.interfaces.CrudRepository;
import service.interfaces.WorkoutService;
import exception.*;

import java.util.List;

// SRP: Only handles workout business logic
// DIP: Depends on CrudRepository interface, not concrete class
public class WorkoutServiceImpl implements WorkoutService {

    private final CrudRepository<CardioWorkout> cardioRepo;
    private final CrudRepository<StrengthWorkout> strengthRepo;

    // DIP: Constructor injection - receives interfaces
    public WorkoutServiceImpl(CrudRepository<CardioWorkout> cardioRepo,
                              CrudRepository<StrengthWorkout> strengthRepo) {
        this.cardioRepo = cardioRepo;
        this.strengthRepo = strengthRepo;
    }

    // ==================== CARDIO METHODS ====================

    @Override
    public void createCardioWorkout(CardioWorkout workout)
            throws InvalidInputException, DuplicateResourceException, DatabaseOperationException {

        // Validation
        if (!workout.validate()) {
            throw new InvalidInputException("Invalid cardio workout data: check name, duration, calories, distance, heart rate");
        }

        // Duplicate check
//        List<CardioWorkout> existing = cardioRepo.getAll();
//        for (CardioWorkout w : existing) {
//            if (w.getName().equalsIgnoreCase(workout.getName())) {
//                throw new DuplicateResourceException("Cardio workout with name '" + workout.getName() + "' already exists");
//            }
//        }

        cardioRepo.create(workout);
    }

    @Override
    public List<CardioWorkout> getAllCardioWorkouts() throws DatabaseOperationException {
        return cardioRepo.getAll();
    }

    @Override
    public CardioWorkout getCardioWorkoutById(int id)
            throws ResourceNotFoundException, DatabaseOperationException {

        CardioWorkout workout = cardioRepo.getById(id);
        if (workout == null) {
            throw new ResourceNotFoundException("Cardio workout with id " + id + " not found");
        }
        return workout;
    }

    @Override
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

    @Override
    public void deleteCardioWorkout(int id)
            throws ResourceNotFoundException, DatabaseOperationException {

        CardioWorkout existing = cardioRepo.getById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Cardio workout with id " + id + " not found");
        }
        cardioRepo.delete(id);
    }

    // Find minimum duration using getAll()
    @Override
    public CardioWorkout getCardioWithMinDuration() throws DatabaseOperationException {
        List<CardioWorkout> all = cardioRepo.getAll();
        if (all.isEmpty()) return null;

        CardioWorkout min = all.get(0);
        for (CardioWorkout w : all) {
            if (w.getDurationMinutes() < min.getDurationMinutes()) {
                min = w;
            }
        }
        return min;
    }

    // ==================== STRENGTH METHODS ====================

    @Override
    public void createStrengthWorkout(StrengthWorkout workout)
            throws InvalidInputException, DuplicateResourceException, DatabaseOperationException {

        if (!workout.validate()) {
            throw new InvalidInputException("Invalid strength workout data: check name, duration, calories, sets, reps, weight");
        }

//        List<StrengthWorkout> existing = strengthRepo.getAll();
//        for (StrengthWorkout w : existing) {
//            if (w.getName().equalsIgnoreCase(workout.getName())) {
//                throw new DuplicateResourceException("Strength workout with name '" + workout.getName() + "' already exists");
//            }
//        }

        strengthRepo.create(workout);
    }

    @Override
    public List<StrengthWorkout> getAllStrengthWorkouts() throws DatabaseOperationException {
        return strengthRepo.getAll();
    }

    @Override
    public StrengthWorkout getStrengthWorkoutById(int id)
            throws ResourceNotFoundException, DatabaseOperationException {

        StrengthWorkout workout = strengthRepo.getById(id);
        if (workout == null) {
            throw new ResourceNotFoundException("Strength workout with id " + id + " not found");
        }
        return workout;
    }

    @Override
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

    @Override
    public void deleteStrengthWorkout(int id)
            throws ResourceNotFoundException, DatabaseOperationException {

        StrengthWorkout existing = strengthRepo.getById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Strength workout with id " + id + " not found");
        }
        strengthRepo.delete(id);
    }

    // Find minimum weight using getAll()
    @Override
    public StrengthWorkout getStrengthWithMinWeight() throws DatabaseOperationException {
        List<StrengthWorkout> all = strengthRepo.getAll();
        if (all.isEmpty()) return null;

        StrengthWorkout min = all.get(0);
        for (StrengthWorkout w : all) {
            if (w.getWeightKg() < min.getWeightKg()) {
                min = w;
            }
        }
        return min;
    }
}