package domain;

import observer.Observer;
import enums.Role;
import java.util.*;

public class Speaker extends User implements Observer{
    private String bio;
    private String expertise;
    private String organization;
    private List<Integer> sessionIDs;

    public Speaker(int userID, String name, String email, String password, Role role, String bio, String expertise, String organization) {
        super(userID, name, email, password, role.SPEAKER);
        this.bio = bio;
        this.expertise = expertise;
        this.organization = organization;
        this.sessionIDs = new ArrayList<>();
    }

    public void addSession(int sessionID){
        if (!sessionIDs.contains(sessionID)) {
            sessionIDs.add(sessionID);
        }
    }

    public boolean updateBio(String newBio){
        bio = newBio;
        return true;
    }

    public List<Integer> getUpcomingSessions(){
        return sessionIDs;
    }

    public String getBio() {
        return bio;
    }

    public String getOrganization() {
        return organization;
    }

    public String getExpertise() {
        return expertise;
    }

    @Override
    public void notify(String message) {
        System.out.println("Notification to Speaker " + getName() + ": " + message);
    }
}
