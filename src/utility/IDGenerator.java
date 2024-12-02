package utility;

import java.util.HashMap;
import java.util.Map;

public class IDGenerator {
    private static final Map<String, Integer> idCounters = new HashMap<>();

    public static synchronized int generateId(String entityType) {
        int currentId = idCounters.getOrDefault(entityType, 0);
        int newId = currentId + 1;
        idCounters.put(entityType, newId);
        return newId;
    }
}
