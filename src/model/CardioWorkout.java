package model;

public class CardioWorkout extends Workout implements Validatable, Trackable {
    private double distanceKm;
    private int averageHeartRate;

    public CardioWorkout(int id, String name, int durationMinutes, int caloriesBurned,
                         double distanceKm, int averageHeartRate) {
        super(id, name, durationMinutes, caloriesBurned);
        this.distanceKm = distanceKm;
        this.averageHeartRate = averageHeartRate;
    }

    public CardioWorkout(String name, int durationMinutes, int caloriesBurned,
                         double distanceKm, int averageHeartRate) {
        super(name, durationMinutes, caloriesBurned);
        this.distanceKm = distanceKm;
        this.averageHeartRate = averageHeartRate;
    }

    @Override
    public String getWorkoutType() { return "Cardio"; }

    @Override
    public double calculateIntensity() {
        return (averageHeartRate / (double) getDurationMinutes()) * distanceKm;
    }

    // LSP: behaves correctly when used via Workout reference
    @Override
    public void displayInfo() {
        System.out.println("[Cardio] " + getName()
                + " | Duration: " + getDurationMinutes() + " min"
                + " | Calories: " + getCaloriesBurned()
                + " | Distance: " + String.format("%.2f", distanceKm) + " km"
                + " | HR: " + averageHeartRate + " bpm"
                + " | Intensity: " + String.format("%.2f", calculateIntensity()));
    }

    // Validatable interface
    @Override
    public boolean validate() {
        return isValid() && distanceKm > 0 && averageHeartRate > 0;
    }

    // Trackable interface
    @Override
    public String getTrackingInfo() {
        return String.format("Cardio: %s | Distance: %.2f km | HR: %d bpm",
                getName(), distanceKm, averageHeartRate);
    }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }

    public int getAverageHeartRate() { return averageHeartRate; }
    public void setAverageHeartRate(int averageHeartRate) { this.averageHeartRate = averageHeartRate; }
}