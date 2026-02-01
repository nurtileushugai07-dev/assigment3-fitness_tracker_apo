import model.*;
import repository.CardioWorkoutRepository;
import repository.ExerciseRepository;
import repository.StrengthWorkoutRepository;
import service.ExerciseServiceImpl;
import service.WorkoutServiceImpl;
import service.interfaces.ExerciseService;
import service.interfaces.WorkoutService;
import utils.ReflectionUtils;
import utils.SortingUtils;
import exception.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // DIP: Create repositories and inject into services
        WorkoutService workoutService = new WorkoutServiceImpl(
                new CardioWorkoutRepository(),
                new StrengthWorkoutRepository()
        );
        ExerciseService exerciseService = new ExerciseServiceImpl(
                new ExerciseRepository()
        );

        System.out.println("   FITNESS TRACKER API - SOLID VERSION  ");


        try {
            // 1. CREATE - Creating entities

            System.out.println("--- 1. CREATING ENTITIES ---\n");

            CardioWorkout running = new CardioWorkout("Morning Running", 30, 300, 5.0, 145);
            workoutService.createCardioWorkout(running);
            System.out.println("Created: " + running.getName() + " (ID: " + running.getId() + ")");

            CardioWorkout cycling = new CardioWorkout("Evening Cycling", 45, 400, 15.0, 130);
            workoutService.createCardioWorkout(cycling);
            System.out.println("Created: " + cycling.getName() + " (ID: " + cycling.getId() + ")");

            CardioWorkout swimming = new CardioWorkout("Swimming", 40, 350, 2.0, 120);
            workoutService.createCardioWorkout(swimming);
            System.out.println("Created: " + swimming.getName() + " (ID: " + swimming.getId() + ")");

            StrengthWorkout benchPress = new StrengthWorkout("Bench Press", 20, 150, 4, 10, 60.0);
            workoutService.createStrengthWorkout(benchPress);
            System.out.println("Created: " + benchPress.getName() + " (ID: " + benchPress.getId() + ")");

            StrengthWorkout squats = new StrengthWorkout("Squats", 25, 200, 5, 12, 80.0);
            workoutService.createStrengthWorkout(squats);
            System.out.println("Created: " + squats.getName() + " (ID: " + squats.getId() + ")");

            Exercise pushUps = new Exercise("Push-ups", "Chest", "None");
            exerciseService.createExercise(pushUps);
            System.out.println("Created exercise: " + pushUps.getName() + " (ID: " + pushUps.getId() + ")");


            // 2. READ ALL - Get all entities

            System.out.println("\n--- 2. READ ALL WORKOUTS ---\n");

            List<CardioWorkout> allCardio = workoutService.getAllCardioWorkouts();
            System.out.println("All Cardio Workouts:");
            for (CardioWorkout w : allCardio) {
                w.displayInfo();
            }

            List<StrengthWorkout> allStrength = workoutService.getAllStrengthWorkouts();
            System.out.println("\nAll Strength Workouts:");
            for (StrengthWorkout w : allStrength) {
                w.displayInfo();
            }

            // 3. POLYMORPHISM - Base class reference

            System.out.println("\n--- 3. POLYMORPHISM DEMO ---\n");

            Workout[] workouts = {running, cycling, swimming, benchPress, squats};
            for (Workout w : workouts) {
                System.out.println("Type: " + w.getWorkoutType()
                        + " | Name: " + w.getName()
                        + " | Intensity: " + String.format("%.2f", w.calculateIntensity()));
            }


            // 4. INTERFACES - default and static methods
            System.out.println("\n--- 4. INTERFACE DEMO (default + static) ---\n");

            // Validatable - static method
            System.out.println("Validatable static method:");
            Validatable.printValidationResult(running);
            Validatable.printValidationResult(benchPress);

            // Validatable - default method
            System.out.println("\nValidatable default method:");
            System.out.println("Running: " + running.getValidationMessage());
            System.out.println("Bench Press: " + benchPress.getValidationMessage());

            // Trackable - default method
            System.out.println("\nTrackable default method:");
            running.printTrackingInfo();
            benchPress.printTrackingInfo();

            // Trackable - static method
            System.out.println("\nTrackable static method:");
            Trackable.printAllTracking(allCardio);


            // 5. LAMBDAS + SORTING

            System.out.println("\n--- 5. SORTING WITH LAMBDAS ---\n");

            // Sort cardio by calories descending
            List<CardioWorkout> cardioForSort = workoutService.getAllCardioWorkouts();
            SortingUtils.sortByCaloriesDesc(cardioForSort);
            System.out.println("Cardio sorted by calories (high → low):");
            for (CardioWorkout w : cardioForSort) {
                System.out.println("  " + w.getName() + " | Calories: " + w.getCaloriesBurned());
            }

            // Sort cardio by duration ascending
            cardioForSort = workoutService.getAllCardioWorkouts();
            SortingUtils.sortByDurationAsc(cardioForSort);
            System.out.println("\nCardio sorted by duration (low → high):");
            for (CardioWorkout w : cardioForSort) {
                System.out.println("  " + w.getName() + " | Duration: " + w.getDurationMinutes() + " min");
            }

            // Sort strength by weight descending
            List<StrengthWorkout> strengthForSort = workoutService.getAllStrengthWorkouts();
            SortingUtils.sortByWeightDesc(strengthForSort);
            System.out.println("\nStrength sorted by weight (high → low):");
            for (StrengthWorkout w : strengthForSort) {
                System.out.println("  " + w.getName() + " | Weight: " + w.getWeightKg() + " kg");
            }

            // ============================================================
            // 6. FIND MINIMUM using getAll()
            // ============================================================
            System.out.println("\n--- 6. FINDING MINIMUM USING getAll() ---\n");

            CardioWorkout minDuration = workoutService.getCardioWithMinDuration();
            if (minDuration != null) {
                System.out.println("Cardio with minimum duration:");
                minDuration.displayInfo();
                System.out.println("Minimum duration: " + minDuration.getDurationMinutes() + " minutes");
            }

            StrengthWorkout minWeight = workoutService.getStrengthWithMinWeight();
            if (minWeight != null) {
                System.out.println("\nStrength with minimum weight:");
                minWeight.displayInfo();
                System.out.println("Minimum weight: " + minWeight.getWeightKg() + " kg");
            }


            // 7. UPDATE

            System.out.println("\n--- 7. UPDATE WORKOUT ---\n");

            running.setDurationMinutes(35);
            running.setCaloriesBurned(320);
            workoutService.updateCardioWorkout(running.getId(), running);
            System.out.println("Updated Running workout:");
            CardioWorkout updated = workoutService.getCardioWorkoutById(running.getId());
            updated.displayInfo();


            // 8. REFLECTION (RTTI)

            System.out.println("\n--- 8. REFLECTION DEMO ---\n");

            ReflectionUtils.fullInspection(running);
            ReflectionUtils.fullInspection(benchPress);
            ReflectionUtils.fullInspection(pushUps);


            // 9. DELETE

            System.out.println("\n--- 9. DELETE WORKOUT ---\n");

            workoutService.deleteCardioWorkout(cycling.getId());
            System.out.println("Deleted: Evening Cycling (ID: " + cycling.getId() + ")");

            System.out.println("\nRemaining Cardio Workouts:");
            List<CardioWorkout> remaining = workoutService.getAllCardioWorkouts();
            for (CardioWorkout w : remaining) {
                w.displayInfo();
            }


            // 10. EXCEPTION HANDLING DEMO
            System.out.println("\n--- 10. EXCEPTION HANDLING DEMO ---\n");

            // InvalidInputException - empty name
            try {
                CardioWorkout invalid = new CardioWorkout("", 30, 300, 5.0, 145);
                workoutService.createCardioWorkout(invalid);
            } catch (InvalidInputException e) {
                System.out.println("Caught InvalidInputException: " + e.getMessage());
            }

            // DuplicateResourceException - duplicate name
            try {
                CardioWorkout duplicate = new CardioWorkout("Morning Running", 30, 300, 5.0, 145);
                workoutService.createCardioWorkout(duplicate);
            } catch (DuplicateResourceException e) {
                System.out.println("Caught DuplicateResourceException: " + e.getMessage());
            } catch (InvalidInputException e) {
                System.out.println("Caught InvalidInputException: " + e.getMessage());
            }

            // ResourceNotFoundException - ID not found
            try {
                workoutService.getCardioWorkoutById(9999);
            } catch (ResourceNotFoundException e) {
                System.out.println("Caught ResourceNotFoundException: " + e.getMessage());
            }

            System.out.println("      DEMO COMPLETED SUCCESSFULLY!      ");

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}