package domain;


import observer.Observer;
import java.util.List;
import java.util.ArrayList;
import enums.Role;


public class Attendee extends User implements Observer{
    private Schedule schedule;
    private List<Integer> feedbacks;
    private List<Integer> certificates;

    public Attendee(int userID, String name, String email, String password, Role role) {
        super(userID, name, email, password, role.ATTENDEE);
        this.schedule = new Schedule(userID);
        this.feedbacks = new ArrayList<>();
        this.certificates = new ArrayList<>();
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Integer> getFeedbacks() {
        return feedbacks;
    }

    public List<Integer> getCertificates() {
        return certificates;
    }

    public void addCertificate(int certificateID) {
        if (!certificates.contains(certificateID)) {
            certificates.add(certificates.size());
        }
    }

    /**
     * Registers the attendee for a session.
     *
     * @param sessionID ID of the session to register for.
     * @param session   The session object to notify (optional).
     */
    public void registerForSession(int sessionID, Session session) {
        if (!schedule.containsSession(sessionID)) {
            schedule.addSession(sessionID); // Add to schedule
            if (session != null) {
                session.registerAttendee(getUserID()); // Notify the session
            }
        }
    }




    public void submitFeedback(int feedbackID){
        feedbacks.add(feedbackID);
    }


    @Override
    public void notify(String message) {
        System.out.println("Notification to Attendee " + getName() + ": " + message);
    }
}
