package domain;

import enums.Role;
import java.util.*;

public class ConferenceManager extends User{
    private List<Integer> managedConferenceIDs;

    public ConferenceManager(int userID, String name, String email, String password, Role role) {
        super(userID, name, email, password, role.MANAGER);
        this.managedConferenceIDs = new ArrayList<>();
    }

    public void assignManagerToConference(int conferenceID){
        if (!managedConferenceIDs.contains(conferenceID)) {
            managedConferenceIDs.add(conferenceID);
        }
    }
}
