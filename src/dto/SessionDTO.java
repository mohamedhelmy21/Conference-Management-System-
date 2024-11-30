package dto;

import java.time.LocalDateTime;

public class SessionDTO {
    private int sessionID;
    private String name;
    private LocalDateTime dateTime;
    private String room;
    private int capacity;
    private String speakerName;

    public SessionDTO(int sessionID, String name, LocalDateTime dateTime, String room, int capacity, String speakerName) {
        this.sessionID = sessionID;
        this.name = name;
        this.dateTime = dateTime;
        this.room = room;
        this.capacity = capacity;
        this.speakerName = speakerName;
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

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }
}
