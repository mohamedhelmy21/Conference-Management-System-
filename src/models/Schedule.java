package models;

import java.util.*;


public class Schedule {
    private int scheduleID;
    private int attendeeID;
    private List<Session> sessions;

    public Schedule(int attendeeID) {
        this.scheduleID = attendeeID;
        this.attendeeID = attendeeID;
        this.sessions = new ArrayList<>();
    }

    //changed return type from boolean to void
    public void addSession(Session session) {
        if(!sessions.contains(session)) {
            sessions.add(session);
        }
    }

    //changed return type from boolean to void
    public void removeSession(int sessionID) {
        sessions.removeIf(session -> session.getSessionID() == sessionID);
    }

    public List<Session> getConflicts() {
        // Basic conflict resolution: two sessions at the same time
        List<Session> conflicts = new ArrayList<>();
        for (int i = 0; i < sessions.size(); i++) {
            for (int j = i + 1; j < sessions.size(); j++) {
                if (sessions.get(i).getDateTime().equals(sessions.get(j).getDateTime())) {
                    conflicts.add(sessions.get(i));
                    conflicts.add(sessions.get(j));
                }
            }
        }
        return conflicts;
     }

    public List<Session> getSessions() {
        return sessions;
    }
}
