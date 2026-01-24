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
    public String getWorkoutType() {
        return "Cardio";
    }

    @Override
    public double calculateIntensity() {
        // Интенсивность = (ЧСС / продолжительность) * расстояние
        return (averageHeartRate / (double) durationMinutes) * distanceKm;
    }

    @Override
    public boolean validate() {
        return isValid() && distanceKm > 0 && averageHeartRate > 0;
    }

    @Override
    public String getTrackingInfo() {
        return String.format("Cardio: %s | Distance: %.2f km | HR: %d bpm | Intensity: %.2f",
                name, distanceKm, averageHeartRate, calculateIntensity());
    }

    @Override
    public void displayInfo() {
        System.out.println(getTrackingInfo());
    }

    // Getters and Setters
    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Distance must be greater than 0");
        }
        this.distanceKm = distanceKm;
    }

    public int getAverageHeartRate() {
        return averageHeartRate;
    }

    public void setAverageHeartRate(int averageHeartRate) {
        if (averageHeartRate <= 0) {
            throw new IllegalArgumentException("Heart rate must be greater than 0");
        }
        this.averageHeartRate = averageHeartRate;
    }
}