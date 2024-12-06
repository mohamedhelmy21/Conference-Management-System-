package controller;

import dto.ConferenceDTO;
import dto.SessionDTO;
import dto.SpeakerDTO;
import service.ConferenceManagerService;

import java.time.LocalDateTime;
import java.util.List;

public class ConferenceManagerController {
    private final ConferenceManagerService conferenceManagerService;

    public ConferenceManagerController(ConferenceManagerService conferenceManagerService) {
        this.conferenceManagerService = conferenceManagerService;
    }

    // Create a new conference
    public ConferenceDTO createConference(String name, String description, LocalDateTime startDate, LocalDateTime endDate, int managerID) {
        return conferenceManagerService.createConference(name, description, startDate, endDate, managerID);
    }

    // Create a new session
    public SessionDTO createSession(int managerID, String name, LocalDateTime dateTime, String room, int capacity, int speakerID, String description, int conferenceID) {
        return conferenceManagerService.createSession(managerID, name, dateTime, room, capacity, speakerID, description, conferenceID);
    }

    // Delete a session
    public void deleteSession(int managerID, int sessionID, int conferenceID) {
        conferenceManagerService.deleteSession(managerID, sessionID, conferenceID);
    }

    // Create a new speaker account
    public SpeakerDTO createSpeakerAccount(int managerID, String name, String email, String password, String bio, String expertise, String organization) {
        return conferenceManagerService.createSpeakerAccount(managerID, name, email, password, bio, expertise, organization);
    }

    // Remove an attendee from the conference
    public void removeAttendeeFromConference(int conferenceID, int attendeeID) {
        conferenceManagerService.removeAttendeeFromConference(conferenceID, attendeeID);
    }

    // Send an update to all attendees
    public void sendConferenceUpdate(int conferenceID, String updateMessage) {
        conferenceManagerService.sendConferenceUpdate(conferenceID, updateMessage);
    }

    // Mark attendance for a session
    public void markAttendance(int sessionID, int attendeeID) {
        conferenceManagerService.markAttendance(sessionID, attendeeID);
    }

    // List all sessions in a conference
    public List<SessionDTO> listSessionsByConference(int conferenceID) {
        return conferenceManagerService.listSessionsByConference(conferenceID);
    }

    // Get conference details
    public ConferenceDTO viewConferenceDetails(int conferenceID) {
        return conferenceManagerService.viewConferenceDetails(conferenceID);
    }
}

