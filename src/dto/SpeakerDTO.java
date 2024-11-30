package dto;

import java.util.List;

public class SpeakerDTO {
    private int speakerID;
    private String name;
    private String bio;
    private List<String> sessions;

    public SpeakerDTO(int speakerID, String name, String bio, List<String> sessions) {
        this.speakerID = speakerID;
        this.name = name;
        this.bio = bio;
        this.sessions = sessions;
    }

    public int getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(int speakerID) {
        this.speakerID = speakerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getSessions() {
        return sessions;
    }

    public void setSessions(List<String> sessions) {
        this.sessions = sessions;
    }
}
