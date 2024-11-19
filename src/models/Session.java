package models;

import java.util.*;
import java.time.LocalDateTime;

public class Session {
    private int sessionID;
    private String name;
    private LocalDateTime dateTime;
    private String room;
    private int capacity;
    private List<Attendee> attendees;
    private Speaker speaker;
    private String description;

    public Session(int sessionID, String name, LocalDateTime dateTime, String room, int capacity, Speaker speaker, String description) {
        this.sessionID = sessionID;
        this.name = name;
        this.dateTime = dateTime;
        this.room = room;
        this.capacity = capacity;
        this.attendees = new ArrayList<>();
        this.speaker = speaker;
        this.description = description;
    }

    public boolean registerAttendee(Attendee attendee) {
        if (attendees.size() < capacity) {
            attendees.add(attendee);
            return true;
        }
        return false;
    }

    public boolean removeAttendee(int attendeeID){
        return attendees.removeIf(attendee -> attendee.getUserID() == attendeeID);
    }

    public int getAvailableSeats(){
        return capacity - attendees.size();
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

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
