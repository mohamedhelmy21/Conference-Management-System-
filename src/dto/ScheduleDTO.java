package dto;

import java.util.List;

public class ScheduleDTO {
    private int scheduleID;
    private int attendeeID;
    private List<Integer> sessionsIDs;

    public ScheduleDTO(int scheduleID, int attendeeID, List<Integer> sessionsIDs) {
        this.scheduleID = scheduleID;
        this.attendeeID = attendeeID;
        this.sessionsIDs = sessionsIDs;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getAttendeeID() {
        return attendeeID;
    }

    public void setAttendeeID(int attendeeID) {
        this.attendeeID = attendeeID;
    }

    public List<Integer> getSessionsIDs() {
        return sessionsIDs;
    }

    public void setSessionsIDs(List<Integer> sessionsIDs) {
        this.sessionsIDs = sessionsIDs;
    }
}
