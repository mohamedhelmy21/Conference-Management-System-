package domain;

import observer.Subject;
import observer.Observer;

import java.util.*;
import java.time.LocalDateTime;

public class Conference implements Subject{
    private int conferenceID;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Integer> sessionsIDs;
    private List<Integer> attendeesIDs;
    private List<Integer> managersIDs;
    private List<Observer> observers;

    public Conference() {
        //default constructor
    }

    public Conference(int conferenceID, String name, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.conferenceID = conferenceID;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessionsIDs = new ArrayList<>();
        this.attendeesIDs = new ArrayList<>();
        this.managersIDs = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public int getConferenceID() {
        return conferenceID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public List<Integer> getSessionsIDs() {
        return sessionsIDs;
    }

    public List<Integer> getAttendeesIDs() {
        return attendeesIDs;
    }

    public List<Integer> getManagersIDs() {
        return managersIDs;
    }

    public void setConferenceID(int conferenceID) {
        this.conferenceID = conferenceID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setSessionsIDs(List<Integer> sessionsIDs) {
        this.sessionsIDs = sessionsIDs;
    }

    public void setAttendeesIDs(List<Integer> attendeesIDs) {
        this.attendeesIDs = attendeesIDs;
    }

    public void setManagersIDs(List<Integer> managersIDs) {
        this.managersIDs = managersIDs;
    }

    // Add a session to the conference
    public void addSession(int sessionID) {
        if (!sessionsIDs.contains(sessionID)) {
            sessionsIDs.add(sessionID);
            notifyObservers("A new session has been added: Session ID " + sessionID);
        }
    }

    // Remove a session from the conference
    public void removeSession(int sessionID) {
        if (sessionsIDs.contains(sessionID)) {
            sessionsIDs.remove(Integer.valueOf(sessionID));
            notifyObservers("A session has been removed: Session ID " + sessionID);
        } else {
            System.out.println("Session ID " + sessionID + " not found in the conference.");
        }
    }

    public void assignManager(int managerID) {
        managersIDs.add(managerID);
    }

    public void addAttendee(int attendeeID) {
        if (!attendeesIDs.contains(attendeeID)) {
            attendeesIDs.add(attendeeID);
        }
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
        if (observers != null) {
            for (Observer observer : observers) {
                observer.notify("Conference Update - " + name + ": " + message);
            }
        }
    }

    @Override
    public String toString() {
        return "Conference{" +
                "conferenceID=" + conferenceID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", sessionIDs=" + sessionsIDs +
                '}';
    }
}
