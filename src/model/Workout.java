package model;

public abstract class Workout {
    protected int id;
    protected String name;
    protected int durationMinutes;
    protected int caloriesBurned;

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

    // Абстрактные методы
    public abstract String getWorkoutType();
    public abstract double calculateIntensity();

    // Конкретный метод
    public boolean isValid() {
        return name != null && !name.trim().isEmpty()
                && durationMinutes > 0
                && caloriesBurned > 0;
    }

    public void displayInfo() {
        System.out.println("Workout: " + name + " | Duration: " + durationMinutes +
                " min | Calories: " + caloriesBurned);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0");
        }
        this.durationMinutes = durationMinutes;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        if (caloriesBurned <= 0) {
            throw new IllegalArgumentException("Calories must be greater than 0");
        }
        this.caloriesBurned = caloriesBurned;
    }
}