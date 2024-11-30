package dto;

import java.util.List;

public class ScheduleDTO {
    private int scheduleID;
    private String attendeeName;
    private List<String> sessionsNames;

    public ScheduleDTO(int scheduleID, String attendeeName, List<String> sessionsNames) {
        this.scheduleID = scheduleID;
        this.attendeeName = attendeeName;
        this.sessionsNames = sessionsNames;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public List<String> getSessionsNames() {
        return sessionsNames;
    }

    public void setSessionsNames(List<String> sessionsNames) {
        this.sessionsNames = sessionsNames;
    }
}
