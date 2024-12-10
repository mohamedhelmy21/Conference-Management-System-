package dto;

import java.time.LocalDateTime;
import java.util.List;

public class SessionDTO {
    private int sessionID;
    private String name;
    private LocalDateTime dateTime;
    private String room;
    private int capacity;
    private List<Integer> signedUpAttendees;
    private List<Integer> attendedAttendees;
    private int speakerID;
    private String description;
    private int conferenceID;

    public SessionDTO(int sessionID, String name, LocalDateTime dateTime, String room, int capacity, List<Integer> signedUpAttendees, List<Integer> attendedAttendees, int speakerID, String description, int conferenceID) {
        this.sessionID = sessionID;
        this.name = name;
        this.dateTime = dateTime;
        this.room = room;
        this.capacity = capacity;
        this.signedUpAttendees = signedUpAttendees;
        this.attendedAttendees = attendedAttendees;
        this.speakerID = speakerID;
        this.description = description;
        this.conferenceID = conferenceID;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(int speakerID) {
        this.speakerID = speakerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getConferenceID() {
        return conferenceID;
    }

    public List<Integer> getSignedUpAttendees() {
        return signedUpAttendees;
    }

    public void setSignedUpAttendees(List<Integer> signedUpAttendees) {
        this.signedUpAttendees = signedUpAttendees;
    }

    public List<Integer> getAttendedAttendees() {
        return attendedAttendees;
    }

    public void setAttendedAttendees(List<Integer> attendedAttendees) {
        this.attendedAttendees = attendedAttendees;
    }
}
