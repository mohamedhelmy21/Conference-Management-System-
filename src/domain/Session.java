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
    private int availableSeats;
    private List<Integer> signedUpAttendees;
    private List<Integer> attendedAttendees;
    private int speakerID;
    private String description;
    private List<Observer> observers;
    private List<Integer> feedbacks;
    private int conferenceID;


    public Session() {
        //default constructor
    }

    public Session(int sessionID, String name, LocalDateTime dateTime, String room, int capacity, int speakerID, String description, int conferenceID) {
        this.sessionID = sessionID;
        this.name = name;
        this.dateTime = dateTime;
        this.room = room;
        this.capacity = capacity;
        this.signedUpAttendees = new ArrayList<>();
        this.attendedAttendees = new ArrayList<>();
        this.speakerID = speakerID;
        this.description = description;
        this.observers = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
        this.availableSeats = capacity;
        this.conferenceID = conferenceID;
    }

    public boolean addToSignedUp(int attendeeID){
        if(signedUpAttendees.size() >= capacity){
            return false;
        }
        if (!signedUpAttendees.contains(attendeeID)) {
            signedUpAttendees.add(attendeeID);
            return true;
        }
        return false;
    }

    public boolean removeFromSignedUp(int attendeeID){
        return signedUpAttendees.remove(Integer.valueOf(attendeeID));
    }

    public boolean registerAttendee(int attendeeID) {
        if (attendedAttendees.contains(attendeeID)) {
            attendedAttendees.add(attendeeID);
            return true;
        }
        return false;
    }

    public boolean isSignedUp(int attendeeID) {
        return signedUpAttendees.contains(attendeeID);
    }

    public boolean hasAttended(int attendeeID){
        return attendedAttendees.contains(attendeeID);
    }


    public int getAvailableSeats(){
        availableSeats = capacity - signedUpAttendees.size();
        return availableSeats;
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

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    public void setFeedbacks(List<Integer> feedbacks) {
        this.feedbacks = feedbacks;
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

    public int getConferenceID() {
        return conferenceID;
    }

    public void setConferenceID(int conferenceID) {
        this.conferenceID = conferenceID;
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

    @Override
    public String toString() {
        return "Session{" +
                "name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", room='" + room + '\'' +
                ", capacity=" + capacity +
                ", availableSeats=" + availableSeats +
                ", speakerID=" + speakerID +
                ", description='" + description + '\'' +
                ", sessionID=" + sessionID +
                '}';
    }
}
