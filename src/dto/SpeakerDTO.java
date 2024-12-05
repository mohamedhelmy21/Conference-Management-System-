package dto;

import java.util.List;

public class SpeakerDTO {
    private int speakerID;
    private String name;
    private String email;
    private String bio;
    private String expertise;
    private String organization;
    private List<Integer> sessions;

    public SpeakerDTO(int speakerID, String name, String email, String bio, String expertise, String organization, List<Integer> sessions) {
        this.speakerID = speakerID;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.expertise = expertise;
        this.organization = organization;
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

    public List<Integer> getSessions() {
        return sessions;
    }

    public void setSessions(List<Integer> sessions) {
        this.sessions = sessions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
