package model;

public interface Validatable {

    // Abstract method - must implement
    boolean validate();

    // Default method - has implementation
    default String getValidationMessage() {
        return validate() ? "Data is valid" : "Data is invalid";
    }

    // Static method - called via interface name
    static void printValidationResult(Validatable item) {
        System.out.println("Validation result: " + item.getValidationMessage());
    }
}