package domain;

import observer.Observer;
import observer.Subject;
import java.util.*;
import java.time.LocalDateTime;

public class Session implements Subject{
    private int sessionID;
    private String name;
    private LocalDateTime dateTime;
    private String room;
    private int capacity;
    private List<Integer> attendeeIDs;
    private int speakerID;
    private String description;
    private List<Observer> observers;
    private List<Integer> feedbacks;

    public Session(int sessionID, String name, LocalDateTime dateTime, String room, int capacity, int speakerID, String description) {
        this.sessionID = sessionID;
        this.name = name;
        this.dateTime = dateTime;
        this.room = room;
        this.capacity = capacity;
        this.attendeeIDs = new ArrayList<>();
        this.speakerID = speakerID;
        this.description = description;
        this.observers = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public boolean registerAttendee(int attendeeID) {
        if (attendeeIDs.size() < capacity && !attendeeIDs.contains(attendeeID)) {
            attendeeIDs.add(attendeeID);
            return true;
        }
        return false;
    }

    public void removeAttendee(int attendeeID) {
        if (attendeeIDs.contains(attendeeID)) {
            attendeeIDs.remove(Integer.valueOf(attendeeID)); // Remove attendee
            notifyObservers("Attendee removed: ID " + attendeeID); // Notify observers
        } else {
            System.out.println("Attendee with ID " + attendeeID + " is not registered for this session.");
        }
    }

    public int getAvailableSeats(){
        return capacity - attendeeIDs.size();
    }

    public void addFeedback(int feedbackID) {
        if (!feedbacks.contains(feedbackID)) {
            feedbacks.add(feedbackID);
        }
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

    public List<Integer> getAttendeeIDs() {
        return attendeeIDs;
    }

    public void setAttendeeID(List<Integer> attendeeID) {
        this.attendeeIDs = attendeeID;
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

    public List<Integer> getFeedbacks() {
        return feedbacks;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.notify(message);
        }
    }
}
