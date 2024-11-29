package domain;

import java.util.*;


public class Schedule {
    private int scheduleID;
    private int attendeeID;
    private List<Integer> sessionIDs;

    public Schedule(int attendeeID) {
        this.scheduleID = attendeeID;
        this.attendeeID = attendeeID;
        this.sessionIDs = new ArrayList<>();
    }

    //changed return type from boolean to void
    public void addSession(int sessionID) {
        if(!sessionIDs.contains(sessionID)) {
            sessionIDs.add(sessionID);
        }
    }

    //changed return type from boolean to void
    public void removeSession(int sessionID) {
        sessionIDs.remove(Integer.valueOf(sessionID));
    }


    public boolean containsSession(int sessionID) {
        return sessionIDs.contains(sessionID);
    }

    public List<Integer> getSessionIDs() {
        return sessionIDs;
    }

    public String displaySchedule() {
        StringBuilder scheduleDetails = new StringBuilder("Scheduled Sessions:\n");
        for (int sessionID : sessionIDs) {
            scheduleDetails.append("- Session ID: ").append(sessionID).append("\n");
        }
        return scheduleDetails.toString();
    }
}
