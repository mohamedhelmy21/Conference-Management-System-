package domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import enums.Role;
import java.util.*;

@JsonTypeName("MANAGER")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ConferenceManager extends User{
    private List<Integer> managedConferenceIDs;

    public ConferenceManager() {
        super();
        managedConferenceIDs = new ArrayList<>();
    }

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
