package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// SRP: Only responsible for reflection operations
// Demonstrates RTTI (Run-Time Type Inspection)
public class ReflectionUtils {

    // Get class name at runtime
    public static void printClassName(Object obj) {
        System.out.println("Class name: " + obj.getClass().getName());
        System.out.println("Simple name: " + obj.getClass().getSimpleName());
    }

    // Get superclass name
    public static void printSuperClass(Object obj) {
        Class<?> superClass = obj.getClass().getSuperclass();
        if (superClass != null) {
            System.out.println("Superclass: " + superClass.getSimpleName());
        } else {
            System.out.println("No superclass (is Object)");
        }
    }

    // List all interfaces
    public static void printInterfaces(Object obj) {
        Class<?>[] interfaces = obj.getClass().getInterfaces();
        System.out.println("Implemented interfaces:");
        if (interfaces.length == 0) {
            System.out.println("  None");
        }
        for (Class<?> iface : interfaces) {
            System.out.println("  - " + iface.getSimpleName());
        }
    }

    // List all fields (including inherited)
    public static void printFields(Object obj) {
        System.out.println("Fields (declared in this class):");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            System.out.println("  - " + f.getType().getSimpleName() + " " + f.getName());
        }

        // Also show parent class fields
        Class<?> superClass = obj.getClass().getSuperclass();
        if (superClass != null && !superClass.equals(Object.class)) {
            System.out.println("Fields (inherited from " + superClass.getSimpleName() + "):");
            Field[] parentFields = superClass.getDeclaredFields();
            for (Field f : parentFields) {
                System.out.println("  - " + f.getType().getSimpleName() + " " + f.getName());
            }
        }
    }

    // List all methods (declared in this class only)
    public static void printMethods(Object obj) {
        System.out.println("Methods (declared in this class):");
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method m : methods) {
            System.out.println("  - " + m.getReturnType().getSimpleName() + " " + m.getName() + "()");
        }
    }

    // Full inspection - prints everything
    public static void fullInspection(Object obj) {
        System.out.println("\n========== REFLECTION INSPECTION ==========");
        printClassName(obj);
        printSuperClass(obj);
        printInterfaces(obj);
        printFields(obj);
        printMethods(obj);
        System.out.println("============================================\n");
    }
}