package model;

public abstract class Workout {
    private int id;
    private String name;
    private int durationMinutes;
    private int caloriesBurned;

    public Workout(int id, String name, int durationMinutes, int caloriesBurned) {
        this.id = id;
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
    }

    public Workout(String name, int durationMinutes, int caloriesBurned) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
    }

    // Abstract methods
    public abstract String getWorkoutType();
    public abstract double calculateIntensity();

    // Concrete method
    public boolean isValid() {
        return name != null && !name.trim().isEmpty()
                && durationMinutes > 0
                && caloriesBurned > 0;
    }

    // Polymorphic method - can be overridden in subclasses
    public void displayInfo() {
        System.out.println("[" + getWorkoutType() + "] " + name
                + " | Duration: " + durationMinutes + " min"
                + " | Calories: " + caloriesBurned
                + " | Intensity: " + String.format("%.2f", calculateIntensity()));
    }

    // Getters and Setters (encapsulation)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public int getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(int caloriesBurned) { this.caloriesBurned = caloriesBurned; }
}