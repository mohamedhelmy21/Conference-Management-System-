package domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import observer.Observer;
import java.util.List;
import java.util.ArrayList;
import enums.Role;

@JsonTypeName("ATTENDEE")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Attendee extends User implements Observer{
    private Schedule schedule;
    private List<Integer> feedbacks;
    private int certificate;
    private int conferenceID = -1;

    public Attendee() {
        super(); // Calls User's default constructor
        this.schedule = null;
        this.feedbacks = new ArrayList<>();
        this.certificate = -1;
    }

    public Attendee(int userID, String name, String email, String password, Role role) {
        super(userID, name, email, password, role.ATTENDEE);
        this.schedule = new Schedule(userID);
        this.feedbacks = new ArrayList<>();
        this.certificate = -1;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Integer> getFeedbacks() {
        return feedbacks;
    }

    public Integer getCertificate() {
        return certificate;
    }

    public void addCertificate(int certificateID) {
        this.certificate = certificateID;
    }

    public void addFeedback(int feedbackID){
        feedbacks.add(feedbackID);
    }

    public int getConferenceID() {
        return conferenceID;
    }

    public void setConferenceID(int conferenceID) {
        this.conferenceID = conferenceID;
    }

    @Override
    public void notify(String message) {
        System.out.println("Notification to Attendee " + getName() + ": " + message);
    }
}
