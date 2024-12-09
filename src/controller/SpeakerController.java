package controller;

import domain.Speaker;
import dto.FeedbackDTO;
import dto.SessionDTO;
import dto.SpeakerDTO;
import enums.Role;
import exception.RepositoryException;
import service.SpeakerService;

import java.io.IOException;
import java.util.List;

public class SpeakerController {
    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    public SpeakerDTO viewSpeakerProfile(int speakerID) {
        return speakerService.getSpeakerProfile(speakerID);
    }

    public String viewSpeakerBio(int speakerID) {
        return speakerService.getSpeakerBio(speakerID);
    }

    public void updateSpeakerBio(int speakerID, String newBio) {
        speakerService.updateSpeakerBio(speakerID, newBio);
    }

    public void updateSpeakerExpertise(int speakerID, String newExpertise){
        speakerService.updateSpeakerExpertise(speakerID, newExpertise);
    }

    public void updateSpeakerOrganization(int speakerID, String newOrganization){
        speakerService.updateSpeakerOrganization(speakerID, newOrganization);
    }


    public List<SessionDTO> listSpeakerSessions(int speakerID) {
        return speakerService.getSpeakerSessions(speakerID);
    }

    public List<FeedbackDTO> viewSessionFeedback(int sessionID) {
        return speakerService.getSessionFeedback(sessionID);
    }

    public double viewSessionAverageRating(int sessionID) {
        return speakerService.getSessionAverageRating(sessionID);
    }

    public void assignSessionToSpeaker(int speakerID, int sessionID) {
        speakerService.addSessionToSpeaker(speakerID, sessionID);
    }

    public SpeakerDTO registerSpeaker(String name, String email, String password, String bio, String expertise, String organization) {
        return speakerService.createSpeaker(name, email, password, bio, expertise, organization);
    }
}
