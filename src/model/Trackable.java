package model;

public interface Trackable {

    // Abstract method - must implement
    String getTrackingInfo();

    // Default method
    default void printTrackingInfo() {
        System.out.println("[Tracking] " + getTrackingInfo());
    }

    // Static method
    static void printAllTracking(java.util.List<? extends Trackable> items) {
        System.out.println("=== All Tracking Info ===");
        for (Trackable item : items) {
            item.printTrackingInfo();
        }
    }
}