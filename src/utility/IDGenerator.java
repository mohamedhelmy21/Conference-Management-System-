package utility;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IDGenerator {
    private static final Map<String, Integer> idCounters = new HashMap<>();
    private static final String FILE_PATH = "idCounters.dat";

    static {
        // Load the ID counters from the file when the class is loaded
        loadCounters();
    }

    public static synchronized int generateId(String entityType) {
        int currentId = idCounters.getOrDefault(entityType, 0);
        int newId = currentId + 1;
        idCounters.put(entityType, newId);

        // Save the updated counters to the file
        saveCounters();

        return newId;
    }

    private static void loadCounters() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Map<String, Integer> loadedCounters = (Map<String, Integer>) ois.readObject();
                idCounters.putAll(loadedCounters);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading ID counters: " + e.getMessage());
            }
        }
    }

    private static void saveCounters() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(idCounters);
        } catch (IOException e) {
            System.err.println("Error saving ID counters: " + e.getMessage());
        }
    }
}
