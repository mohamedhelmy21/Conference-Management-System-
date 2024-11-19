package models;

import java.util.*;
import enums.Role;


public class Attendee extends User{
    private Schedule schedule;
    private List<Feedback> feedbacks;
    private List<Certificate> certificate;

    public Attendee(int userID, String name, String email, String password, Role role) {
        super(userID, name, email, password, role.ATTENDEE);
        this.schedule = new Schedule(userID);
        this.feedbacks = new ArrayList<>();
        this.certificate = new ArrayList<>();
    }

    public boolean registerForSession(Session session){
        return schedule.addSession(session);
    }

    public boolean submitFeedback(int sessionID, Feedback feedback){
        //Add feedback logic
        feedbacks.add(feedback);
    }

}
