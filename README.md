# Fitness Tracker API — SOLID Architecture & Advanced OOP

## A. SOLID Documentation

### Single Responsibility Principle (SRP)
Each class has exactly one reason to change:
- **Workout** — only defines base workout structure
- **CardioWorkout / StrengthWorkout** — only handle their specific workout type
- **WorkoutServiceImpl** — only handles workout business logic and validation
- **ExerciseServiceImpl** — only handles exercise business logic
- **CardioWorkoutRepository** — only handles cardio DB operations
- **SortingUtils** — only handles sorting
- **ReflectionUtils** — only handles reflection

### Open-Closed Principle (OCP)
The system is open for extension but closed for modification:
- To add a new workout type (e.g., `FlexibilityWorkout`), create a new subclass of `Workout` and implement `CrudRepository<FlexibilityWorkout>` — no existing code needs to change
- `CrudRepository<T>` is a generic interface — new repositories can be added without modifying existing ones

### Liskov Substitution Principle (LSP)
Subclasses can replace their parent class without breaking behavior:
- `CardioWorkout` and `StrengthWorkout` can be used anywhere a `Workout` reference is expected
- In `Main.java`: `Workout[] workouts = {cardio, strength}` — both behave correctly through the base type
- Both subclasses correctly override `displayInfo()`, `getWorkoutType()`, and `calculateIntensity()`

### Interface Segregation Principle (ISP)
Interfaces are small and focused:
- `Validatable` — only defines validation (`validate()`)
- `Trackable` — only defines tracking (`getTrackingInfo()`)
- `CrudRepository<T>` — only defines CRUD operations
- `WorkoutService` — only defines workout service operations
- `ExerciseService` — only defines exercise service operations

### Dependency Inversion Principle (DIP)
High-level modules depend on abstractions, not concrete classes:
- `WorkoutServiceImpl` depends on `CrudRepository<T>` interface, not concrete repositories
- `Main` depends on `WorkoutService` and `ExerciseService` interfaces
- Constructor injection is used to pass dependencies:
```java
WorkoutService service = new WorkoutServiceImpl(
    new CardioWorkoutRepository(),
    new StrengthWorkoutRepository()
);
```

---

## B. Advanced OOP Features

### Generics
Used in `CrudRepository<T>` interface:
```java
public interface CrudRepository<T> {
    void create(T entity);
    List<T> getAll();
    T getById(int id);
    void update(int id, T entity);
    void delete(int id);
}
```
Each repository implements this with a specific type: `CrudRepository<CardioWorkout>`, `CrudRepository<StrengthWorkout>`, `CrudRepository<Exercise>`.

### Lambdas
Used in `SortingUtils.java` for sorting lists:
```java
// Sort by calories descending
Collections.sort(list, (a, b) -> Integer.compare(b.getCaloriesBurned(), a.getCaloriesBurned()));

// Sort by name ascending
Collections.sort(list, (a, b) -> a.getName().compareTo(b.getName()));
```
Lambdas replace the need to create anonymous Comparator classes, making the code shorter and more readable.

### Reflection (RTTI)
Used in `ReflectionUtils.java`:
- `getClass().getName()` — gets class name at runtime
- `getClass().getSuperclass()` — gets parent class
- `getClass().getInterfaces()` — lists implemented interfaces
- `getDeclaredFields()` — lists all fields
- `getDeclaredMethods()` — lists all methods

### Interface Default and Static Methods
**Validatable interface:**
```java
// Default method — has implementation
default String getValidationMessage() {
    return validate() ? "Data is valid" : "Data is invalid";
}

// Static method — called via interface name
static void printValidationResult(Validatable item) { ... }
```

**Trackable interface:**
```java
// Default method
default void printTrackingInfo() { ... }

// Static method — called via interface name
static void printAllTracking(List<? extends Trackable> items) { ... }
```

---

## C. OOP Documentation

### Abstract Class: Workout
- **Fields:** id, name, durationMinutes, caloriesBurned
- **Abstract methods:** `getWorkoutType()`, `calculateIntensity()`
- **Concrete methods:** `isValid()`, `displayInfo()`

### Subclasses
| Class | Extra Fields | Intensity Formula |
|---|---|---|
| CardioWorkout | distanceKm, averageHeartRate | (HR / duration) × distance |
| StrengthWorkout | sets, reps, weightKg | (sets × reps × weight) / duration |

### Composition
`Exercise` is a standalone entity representing individual exercises (muscle group, equipment). It can be associated with strength workouts, demonstrating composition.

### Polymorphism
```java
Workout[] workouts = {cardioWorkout, strengthWorkout};
for (Workout w : workouts) {
    w.displayInfo();           // Different output per type
    w.calculateIntensity();    // Different formula per type
}
```

### UML Diagram
See `docs/uml.png`

---

## D. Database

### Schema
- **exercises** — id, name (UNIQUE), muscle_group, equipment_needed
- **cardio_workouts** — id, name (UNIQUE), duration_minutes, calories_burned, distance_km, average_heart_rate
- **strength_workouts** — id, name (UNIQUE), duration_minutes, calories_burned, sets, reps, weight_kg

### Constraints
- All tables have `SERIAL PRIMARY KEY`
- `UNIQUE` on name fields to prevent duplicates
- `CHECK` constraints: all numeric fields must be > 0
- `NOT NULL` on all required fields
- `DEFAULT CURRENT_TIMESTAMP` on created_at

### Sample Inserts
See `resources/schema.sql`

---

## E. Architecture

### Layer Roles
```
Main (Controller)
  → No business logic
  → Creates services via DIP (constructor injection)
  → Delegates all operations to service layer

WorkoutServiceImpl / ExerciseServiceImpl (Service)
  → Applies validation (validate())
  → Checks for duplicates
  → Checks resource existence before update/delete
  → Uses repository interfaces (DIP)

CardioWorkoutRepository / StrengthWorkoutRepository / ExerciseRepository (Repository)
  → Implements CrudRepository<T> generic interface
  → Only JDBC operations
  → No business logic

PostgreSQL (Database)
  → Stores data with constraints
```

### Request/Response Example
```
Main calls: workoutService.createCardioWorkout(workout)
  → Service validates: workout.validate() → true
  → Service checks duplicates: getAll() → no match
  → Service calls: cardioRepo.create(workout)
    → Repository executes: INSERT INTO cardio_workouts ...
    → Sets generated ID on workout object
  → Returns to Main
Main prints: "Created: Morning Running (ID: 1)"
```

---

## F. Execution Instructions

### Prerequisites
- Java JDK 8+
- PostgreSQL 12+
- PostgreSQL JDBC Driver (`postgresql-42.7.0.jar`)

### Database Setup
```bash
# In pgAdmin or psql:
CREATE DATABASE fitness_tracker;
# Then run schema.sql in Query Tool
```

### Update Password
Edit `utils/DatabaseConnection.java`:
```java
private static final String PASSWORD = "your_password";
```

### Compile
```bash
# Windows:
javac -cp ".;postgresql-42.7.0.jar" -d bin src/model/*.java src/exception/*.java src/utils/*.java src/repository/interfaces/*.java src/repository/*.java src/service/interfaces/*.java src/service/*.java src/Main.java

# Linux/Mac:
javac -cp ".:postgresql-42.7.0.jar" -d bin src/model/*.java src/exception/*.java src/utils/*.java src/repository/interfaces/*.java src/repository/*.java src/service/interfaces/*.java src/service/*.java src/Main.java
```

### Run
```bash
# Windows:
java -cp "bin;postgresql-42.7.0.jar" Main

# Linux/Mac:
java -cp "bin:postgresql-42.7.0.jar" Main
```

---

## G. Screenshots
See `docs/screenshots/`for:
1. Successful CRUD operations
2. Validation failure (InvalidInputException)
3. Duplicate resource error (DuplicateResourceException)
4. Resource not found error (ResourceNotFoundException)
5. Reflection utility output
6. Sorted lists using lambdas
7. Minimum value found using getAll()

---

## H. Reflection

### What I Learned
- How to apply SOLID principles in a real Java project
- How generics make repository code reusable across entity types
- How lambdas simplify sorting logic compared to anonymous classes
- How reflection allows runtime inspection of class structure
- How interface default/static methods add functionality without breaking existing code
- How constructor injection (DIP) makes code more flexible and testable

### Challenges Faced
- Understanding DIP and how to inject dependencies via constructor
- Balancing SRP — deciding what logic belongs in Service vs Repository
- Making generic CrudRepository work with different entity types
- Writing correct lambda expressions for different Comparator scenarios

### Value of SOLID Architecture
- **SRP** makes each class easy to understand and modify
- **OCP** allows adding new workout types without changing existing code
- **LSP** ensures subclasses work correctly through base class references
- **ISP** keeps interfaces small and focused — classes only implement what they need
- **DIP** decouples layers — services don't know which specific repository is used

---

## Project Structure
```
assignment4-fitness-tracker/
├── src/
│   ├── model/
│   │   ├── Workout.java (Abstract)
│   │   ├── CardioWorkout.java
│   │   ├── StrengthWorkout.java
│   │   ├── Exercise.java
│   │   ├── Validatable.java (Interface with default + static)
│   │   └── Trackable.java (Interface with default + static)
│   ├── repository/
│   │   ├── interfaces/
│   │   │   └── CrudRepository.java (Generic interface)
│   │   ├── CardioWorkoutRepository.java
│   │   ├── StrengthWorkoutRepository.java
│   │   └── ExerciseRepository.java
│   ├── service/
│   │   ├── interfaces/
│   │   │   ├── WorkoutService.java
│   │   │   └── ExerciseService.java
│   │   ├── WorkoutServiceImpl.java
│   │   └── ExerciseServiceImpl.java
│   ├── exception/
│   │   ├── InvalidInputException.java
│   │   ├── DuplicateResourceException.java
│   │   ├── ResourceNotFoundException.java
│   │   └── DatabaseOperationException.java
│   ├── utils/
│   │   ├── DatabaseConnection.java
│   │   ├── SortingUtils.java (Lambdas)
│   │   └── ReflectionUtils.java (RTTI)
│   └── Main.java
├── resources/
│   └── schema.sql
├── docs/
│   ├── screenshots/
│   └── uml.png
├── README.md
└── .gitignore
```