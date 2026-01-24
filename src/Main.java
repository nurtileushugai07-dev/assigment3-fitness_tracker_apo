import model.*;
import service.WorkoutService;
import exception.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorkoutService service = new WorkoutService();

        System.out.println("=== FITNESS TRACKER API DEMO ===\n");

        try {
            // 1. CREATE Cardio Workout
            System.out.println("1. CREATING CARDIO WORKOUTS:");
            CardioWorkout running = new CardioWorkout("Morning Running", 30, 300, 5.0, 145);
            service.createCardioWorkout(running);
            System.out.println("✓ Created: " + running.getName());

            CardioWorkout cycling = new CardioWorkout("Evening Cycling", 45, 400, 15.0, 130);
            service.createCardioWorkout(cycling);
            System.out.println("✓ Created: " + cycling.getName());

            // 2. CREATE Strength Workout
            System.out.println("\n2. CREATING STRENGTH WORKOUTS:");
            StrengthWorkout benchPress = new StrengthWorkout("Bench Press", 20, 150, 4, 10, 60.0);
            service.createStrengthWorkout(benchPress);
            System.out.println("✓ Created: " + benchPress.getName());

            StrengthWorkout squats = new StrengthWorkout("Squats", 25, 200, 5, 12, 80.0);
            service.createStrengthWorkout(squats);
            System.out.println("✓ Created: " + squats.getName());

            // 3. READ ALL workouts
            System.out.println("\n3. READING ALL WORKOUTS:");
            System.out.println("\nCardio Workouts:");
            List<CardioWorkout> cardioList = service.getAllCardioWorkouts();
            for (CardioWorkout w : cardioList) {
                w.displayInfo();
            }

            System.out.println("\nStrength Workouts:");
            List<StrengthWorkout> strengthList = service.getAllStrengthWorkouts();
            for (StrengthWorkout w : strengthList) {
                w.displayInfo();
            }

            // 4. POLYMORPHISM
            System.out.println("\n4. DEMONSTRATING POLYMORPHISM:");
            Workout[] workouts = {running, cycling, benchPress, squats};
            for (Workout w : workouts) {
                System.out.println("Type: " + w.getWorkoutType() +
                        " | Intensity: " + String.format("%.2f", w.calculateIntensity()));
            }

            // 5. INTERFACES
            System.out.println("\n5. DEMONSTRATING INTERFACES:");
            System.out.println("Validatable interface:");
            System.out.println("Running valid? " + running.validate());
            System.out.println("Bench Press valid? " + benchPress.validate());

            System.out.println("\nTrackable interface:");
            System.out.println(running.getTrackingInfo());
            System.out.println(benchPress.getTrackingInfo());

            // 6. UPDATE workout
            System.out.println("\n6. UPDATING WORKOUT:");
            running.setDurationMinutes(35);
            running.setDistanceKm(6.0);
            service.updateCardioWorkout(running.getId(), running);
            System.out.println("✓ Updated running workout");

            CardioWorkout updated = service.getCardioWorkoutById(running.getId());
            updated.displayInfo();

            // 7. DELETE workout
            System.out.println("\n7. DELETING WORKOUT:");
            service.deleteCardioWorkout(cycling.getId());
            System.out.println("✓ Deleted cycling workout (ID: " + cycling.getId() + ")");

            System.out.println("\nRemaining Cardio Workouts:");
            cardioList = service.getAllCardioWorkouts();
            for (CardioWorkout w : cardioList) {
                w.displayInfo();
            }

            // 8. VALIDATION
            System.out.println("\n8. TESTING VALIDATION:");
            try {
                CardioWorkout invalid = new CardioWorkout("", 30, 300, 5.0, 145);
                service.createCardioWorkout(invalid);
            } catch (InvalidInputException e) {
                System.out.println("✓ Caught validation error: " + e.getMessage());
            }

            // 9. NOT FOUND  ResourceNotFoundException
            System.out.println("\n9. TESTING RESOURCE NOT FOUND:");
            try {
                service.getCardioWorkoutById(9999);
            } catch (ResourceNotFoundException e) {
                System.out.println("✓ Caught error: " + e.getMessage());
            }

            System.out.println("\n=== DEMO COMPLETED SUCCESSFULLY ===");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}