package utils;

import model.CardioWorkout;
import model.StrengthWorkout;
import model.Workout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// SRP: Only responsible for sorting
// Uses Lambda expressions for Comparators
public class SortingUtils {

    // Sort cardio workouts by calories (ascending) - LAMBDA
    public static void sortByCaloriesAsc(List<CardioWorkout> list) {
        Collections.sort(list, (a, b) -> Integer.compare(a.getCaloriesBurned(), b.getCaloriesBurned()));
    }

    // Sort cardio workouts by calories (descending) - LAMBDA
    public static void sortByCaloriesDesc(List<CardioWorkout> list) {
        Collections.sort(list, (a, b) -> Integer.compare(b.getCaloriesBurned(), a.getCaloriesBurned()));
    }

    // Sort cardio workouts by duration (ascending) - LAMBDA
    public static void sortByDurationAsc(List<CardioWorkout> list) {
        Collections.sort(list, (a, b) -> Integer.compare(a.getDurationMinutes(), b.getDurationMinutes()));
    }

    // Sort cardio workouts by duration (descending) - LAMBDA
    public static void sortByDurationDesc(List<CardioWorkout> list) {
        Collections.sort(list, (a, b) -> Integer.compare(b.getDurationMinutes(), a.getDurationMinutes()));
    }

    // Sort cardio workouts by distance (ascending) - LAMBDA
    public static void sortByDistanceAsc(List<CardioWorkout> list) {
        Collections.sort(list, (a, b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()));
    }

    // Sort cardio workouts by name (ascending) - LAMBDA
    public static void sortByNameAsc(List<CardioWorkout> list) {
        Collections.sort(list, (a, b) -> a.getName().compareTo(b.getName()));
    }

    // Sort strength workouts by weight (descending) - LAMBDA
    public static void sortByWeightDesc(List<StrengthWorkout> list) {
        Collections.sort(list, (a, b) -> Double.compare(b.getWeightKg(), a.getWeightKg()));
    }

    // Sort strength workouts by calories (descending) - LAMBDA
    public static void sortStrengthByCaloriesDesc(List<StrengthWorkout> list) {
        Collections.sort(list, (a, b) -> Integer.compare(b.getCaloriesBurned(), a.getCaloriesBurned()));
    }

    // Sort strength workouts by name (ascending) - LAMBDA
    public static void sortStrengthByNameAsc(List<StrengthWorkout> list) {
        Collections.sort(list, (a, b) -> a.getName().compareTo(b.getName()));
    }

    // Generic: sort any Workout list by intensity (descending) - LAMBDA
    public static void sortByIntensityDesc(List<? extends Workout> list) {
        list.sort((a, b) -> Double.compare(b.calculateIntensity(), a.calculateIntensity()));
    }
}