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
    public String getWorkoutType() { return "Strength"; }

    @Override
    public double calculateIntensity() {
        return (sets * reps * weightKg) / getDurationMinutes();
    }

    // LSP: behaves correctly when used via Workout reference
    @Override
    public void displayInfo() {
        System.out.println("[Strength] " + getName()
                + " | Duration: " + getDurationMinutes() + " min"
                + " | Calories: " + getCaloriesBurned()
                + " | Sets: " + sets + "x" + reps
                + " | Weight: " + String.format("%.1f", weightKg) + " kg"
                + " | Intensity: " + String.format("%.2f", calculateIntensity()));
    }

    // Validatable
    @Override
    public boolean validate() {
        return isValid() && sets > 0 && reps > 0 && weightKg > 0;
    }

    // Trackable
    @Override
    public String getTrackingInfo() {
        return String.format("Strength: %s | %dx%d | Weight: %.1f kg",
                getName(), sets, reps, weightKg);
    }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
}