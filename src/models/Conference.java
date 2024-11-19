package models;

import java.util.*;

public class Conference {
    private int conferenceID;
    private String name;
    private String room;
    private String description;
    private String List<Session> sessions;

    public Conference(int conferenceID, String name, String room, String description) {
        this.conferenceID = conferenceID;
        this.name = name;
        this.room = room;
        this.description = description;
        this.sessions = new ArrayList<>();
    }

    public boolean addSession(Session session){
        return sessions.add(session);
    }

    public boolean removeSession(int sessionID){
        reutrn sessions.removeIf(session -> session.getSessionID == sessionID);
    }

    public List<Session> getSessions(){
        return sessions;
    }
}
