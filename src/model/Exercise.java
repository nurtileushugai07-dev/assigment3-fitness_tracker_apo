package model;

// Composition: StrengthWorkout has Exercise
public class Exercise {
    private int id;
    private String name;
    private String muscleGroup;
    private String equipmentNeeded;

    public Exercise(int id, String name, String muscleGroup, String equipmentNeeded) {
        this.id = id;
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.equipmentNeeded = equipmentNeeded;
    }

    public Exercise(String name, String muscleGroup, String equipmentNeeded) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.equipmentNeeded = equipmentNeeded;
    }

    public void displayInfo() {
        System.out.println("[Exercise] " + name + " | Muscle: " + muscleGroup + " | Equipment: " + equipmentNeeded);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }

    public String getEquipmentNeeded() { return equipmentNeeded; }
    public void setEquipmentNeeded(String equipmentNeeded) { this.equipmentNeeded = equipmentNeeded; }
}