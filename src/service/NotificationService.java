package service;
import observer.*;

import java.util.ArrayList;
import java.util.List;

public class NotificationService implements Subject {
    private final List<Observer> observers = new ArrayList<>();

    private List<String> notificationHistory = new ArrayList<>();


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
        notificationHistory.add(message); // Save notifications for later
        for (Observer observer : observers) {
            observer.notify(message);
        }
    }

    public List<String> getNotificationHistory() {
        return new ArrayList<>(notificationHistory);
    }


    // Specific notifications
    public void notifyAttendees(String message) {
        notifyObservers("To Attendees: " + message);
    }

    public void notifySpeakers(String message) {
        notifyObservers("To Speakers: " + message);
    }
}

