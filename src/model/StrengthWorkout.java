package model;

public class StrengthWorkout extends Workout implements Validatable, Trackable {
    private int sets;
    private int reps;
    private double weightKg;

    public StrengthWorkout(int id, String name, int durationMinutes, int caloriesBurned,
                           int sets, int reps, double weightKg) {
        super(id, name, durationMinutes, caloriesBurned);
        this.sets = sets;
        this.reps = reps;
        this.weightKg = weightKg;
    }

    public StrengthWorkout(String name, int durationMinutes, int caloriesBurned,
                           int sets, int reps, double weightKg) {
        super(name, durationMinutes, caloriesBurned);
        this.sets = sets;
        this.reps = reps;
        this.weightKg = weightKg;
    }

    @Override
    public String getWorkoutType() {
        return "Strength";
    }

    @Override
    public double calculateIntensity() {
        // Интенсивность = сеты * повторения * вес / время
        return (sets * reps * weightKg) / durationMinutes;
    }

    @Override
    public boolean validate() {
        return isValid() && sets > 0 && reps > 0 && weightKg > 0;
    }

    @Override
    public String getTrackingInfo() {
        return String.format("Strength: %s | %dx%d sets | Weight: %.1f kg | Intensity: %.2f",
                name, sets, reps, weightKg, calculateIntensity());
    }

    @Override
    public void displayInfo() {
        System.out.println(getTrackingInfo());
    }

    // Getters and Setters
    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        if (sets <= 0) {
            throw new IllegalArgumentException("Sets must be greater than 0");
        }
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        if (reps <= 0) {
            throw new IllegalArgumentException("Reps must be greater than 0");
        }
        this.reps = reps;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
        this.weightKg = weightKg;
    }
}