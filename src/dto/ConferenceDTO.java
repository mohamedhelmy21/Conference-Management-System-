package dto;

import java.time.LocalDateTime;
import java.util.List;

public class ConferenceDTO {
    private int conferenceID;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> sessions;
    private List<String> attendees;

    public ConferenceDTO(int conferenceID, String name, String description, LocalDateTime startDate, LocalDateTime endDate, List<String> sessions, List<String> attendees) {
        this.conferenceID = conferenceID;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessions = sessions;
        this.attendees = attendees;
    }

    public int getConferenceID() {
        return conferenceID;
    }

    public void setConferenceID(int conferenceID) {
        this.conferenceID = conferenceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<String> getSessions() {
        return sessions;
    }

    public void setSessions(List<String> sessions) {
        this.sessions = sessions;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }
}
