package models;

import enums.Role;
import java.util.*;

public class Speaker extends User {
    private String bio;
    private String expertise;
    private String organization;
    private List<Session> sessions;

    public Speaker(int userID, String name, String email, String password, Role role, String bio, String expertise, String organization) {
        super(userID, name, email, password, role.SPEAKER);
        this.bio = bio;
        this.expertise = expertise;
        this.organization = organization;
        this.sessions = new ArrayList<>();
    }

    public boolean addSession(Session session){
        return sessions.add(session);
    }

    public boolean updateBio(String newBio){
        bio = newBio;
        return true;
    }

    public List<Session> getUpcomingSessions(){
        return sessions;
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
}
