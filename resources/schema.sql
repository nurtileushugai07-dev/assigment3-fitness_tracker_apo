
CREATE TABLE exercises (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           muscle_group VARCHAR(50) NOT NULL,
                           equipment_needed VARCHAR(100)
);

CREATE TABLE cardio_workouts (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(100) NOT NULL,
                                 duration_minutes INT NOT NULL CHECK (duration_minutes > 0),
                                 calories_burned INT NOT NULL CHECK (calories_burned > 0),
                                 distance_km DECIMAL(10, 2) NOT NULL CHECK (distance_km > 0),
                                 average_heart_rate INT NOT NULL CHECK (average_heart_rate > 0),
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE strength_workouts (
                                   id SERIAL PRIMARY KEY,
                                   name VARCHAR(100) NOT NULL,
                                   duration_minutes INT NOT NULL CHECK (duration_minutes > 0),
                                   calories_burned INT NOT NULL CHECK (calories_burned > 0),
                                   sets INT NOT NULL CHECK (sets > 0),
                                   reps INT NOT NULL CHECK (reps > 0),
                                   weight_kg DECIMAL(10, 2) NOT NULL CHECK (weight_kg > 0),
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO exercises (name, muscle_group, equipment_needed) VALUES
                                                                 ('Push-ups', 'Chest', 'None'),
                                                                 ('Pull-ups', 'Back', 'Pull-up bar'),
                                                                 ('Squats', 'Legs', 'None'),
                                                                 ('Bench Press', 'Chest', 'Barbell'),
                                                                 ('Deadlift', 'Back', 'Barbell');

INSERT INTO cardio_workouts (name, duration_minutes, calories_burned, distance_km, average_heart_rate) VALUES
                                                                                                           ('Morning Run', 30, 300, 5.0, 145),
                                                                                                           ('Evening Cycling', 45, 400, 15.0, 130),
                                                                                                           ('Swimming', 40, 350, 2.0, 120);

INSERT INTO strength_workouts (name, duration_minutes, calories_burned, sets, reps, weight_kg) VALUES
                                                                                                   ('Bench Press Session', 20, 150, 4, 10, 60.0),
                                                                                                   ('Squat Session', 25, 200, 5, 12, 80.0),
                                                                                                   ('Deadlift Session', 30, 250, 4, 8, 100.0);