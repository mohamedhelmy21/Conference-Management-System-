package service;

import domain.Conference;
import domain.ConferenceManager;
import domain.User;
import dto.ConferenceDTO;
import dto.SessionDTO;
import dto.SpeakerDTO;
import dto.UserDTO;
import enums.Role;
import exception.RepositoryException;
import repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


public class ConferenceManagerService {
    private final UserRepository userRepository;
    private final ConferenceService conferenceService;
    private final SessionService sessionService;
    private final SpeakerService speakerService;

    public ConferenceManagerService(UserRepository userRepository, ConferenceService conferenceService, SessionService sessionService, SpeakerService speakerService) {
        this.userRepository = userRepository;
        this.conferenceService = conferenceService;
        this.sessionService = sessionService;
        this.speakerService = speakerService;
    }

    private ConferenceManager getConferenceManagerByID(int managerID) {
        try {
            User user = userRepository.findById(managerID);
            if (user == null || !(user instanceof ConferenceManager)) {
                throw new IllegalArgumentException("Conference Manager not found");
            }
            return (ConferenceManager) user;
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving conference manager", e);
        }
    }

    public ConferenceDTO createConference(String name, String description, LocalDateTime startDate, LocalDateTime endDate, int managerID){
        try{
            ConferenceManager conferenceManager = getConferenceManagerByID(managerID);

            Conference conference = conferenceService.createConference(name, description, startDate, endDate, managerID);

            conferenceManager.assignManagerToConference(conference.getConferenceID());

            userRepository.save(conferenceManager);

            return conferenceService.mapToDTO(conference);
        } catch (IOException e) {
            throw new RepositoryException("Error creating conference.", e);
        }
    }

    public SessionDTO createSession(int managerID, String name, LocalDateTime dateTime, String room, int capacity, int speakerID, String description, int conferenceID) {
        try {
            // Ensure the manager exists
            getConferenceManagerByID(managerID);

            // Delegate session creation to SessionService
            return sessionService.createSession(name, dateTime, room, capacity, speakerID, description, conferenceID);
        } catch (Exception e) {
            throw new RepositoryException("Error creating session.", e);
        }
    }

    public void deleteSession(int managerID, int sessionID, int conferenceID) {
        try{
            getConferenceManagerByID(managerID);

            sessionService.deleteSession(sessionID, conferenceID);
        } catch (Exception e) {
            throw new RepositoryException("Error deleting session.", e);
        }
    }

    public SpeakerDTO createSpeakerAccount(int managerID, String name, String email, String password, String bio, String expertise, String organization) {
        try {
            getConferenceManagerByID(managerID);

            return speakerService.createSpeaker(name, email, password, bio, expertise, organization);
        } catch (Exception e) {
            throw new RepositoryException("Error creating speaker account.", e);
        }
    }

    public void removeAttendeeFromConference(int conferenceID, int attendeeID) {
        try {
            // Remove attendee from conference
            conferenceService.removeAttendeeFromConference(conferenceID, attendeeID);

            // Remove attendee from all sessions in the conference
            List<SessionDTO> sessions = sessionService.listSessionsByConference(conferenceID);
            for (SessionDTO session : sessions) {
                sessionService.removeAttendeeFromSession(attendeeID, session.getSessionID());
            }

            System.out.println("Attendee with ID " + attendeeID + " removed from all conference sessions.");
        } catch (Exception e) {
            throw new RepositoryException("Error removing attendee from conference.", e);
        }
    }

    public void sendConferenceUpdate(int conferenceID, String updateMessage){
        try {
            List<Integer> attendeeIDs = conferenceService.getConferenceAttendees(conferenceID);

            for (int attendeeID : attendeeIDs) {
                System.out.println("Update sent to attendee ID " + attendeeID + ": " + updateMessage);
            }
        } catch (Exception e) {
            throw new RepositoryException("Error sending conference update.", e);
        }
    }

    public void assignSpeakerToSession(int sessionID, int speakerID) {
        try {
            speakerService.addSessionToSpeaker(sessionID, speakerID);
        } catch (Exception e) {
            throw new RepositoryException("Error assigning speaker to session.", e);
        }
    }

    public void markAttendance(int sessionID, int attendeeID) {
        try {
            sessionService.registerAttendance(attendeeID, sessionID);
        } catch (Exception e) {
            throw new RepositoryException("Error marking attendance.", e);
        }
    }

}

