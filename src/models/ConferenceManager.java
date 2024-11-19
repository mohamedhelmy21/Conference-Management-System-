package models;

import enums.Role;
import java.util.*;

public class ConferenceManager extends User{
    private List<Conference> managedConferences;

    public ConferenceManager(int userID, String name, String email, String password, Role role) {
        super(userID, name, email, password, role.MANAGER);
        this.managedConferences = new ArrayList<>();
    }

    public boolean createSession(Session session, Conference conference){
        return conference.addSession(session);
    }

    public boolean assignManagerToConference(Conference conference){
        return managedConferences.add(conference);
    }
}
