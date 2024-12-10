package service;

import domain.Conference;
import domain.Session;
import dto.FeedbackDTO;
import dto.SessionDTO;
import exception.RepositoryException;
import repository.SessionRepository;
import utility.IDGenerator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SessionService {
    private final SessionRepository sessionRepository;
    private final ConferenceService conferenceService;
    private final FeedbackService feedbackService;
    private SpeakerService speakerService;

    public SessionService(SessionRepository sessionRepository, ConferenceService conferenceService, FeedbackService feedbackService) {
        this.sessionRepository = sessionRepository;
        this.conferenceService = conferenceService;
        this.feedbackService = feedbackService;
    }

    public void setSpeakerService(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    public SessionDTO createSession(String name, LocalDateTime dateTime, String room, int capacity, int speakerID, String description, int conferenceID) {
        try{
            int sessionID = IDGenerator.generateId("Session");

            Session session = new Session(sessionID, name, dateTime, room, capacity, speakerID, description, conferenceID);

            sessionRepository.save(session);

            conferenceService.addSessionToConference(conferenceID, sessionID);

            speakerService.addSessionToSpeaker(speakerID, sessionID);

            return mapToDTO(session);
        } catch (IOException e){
            throw new RepositoryException("Error creating session.", e);
        }
    }

    public void updateSession(int sessionID, String newName, LocalDateTime newDate, String newRoom, int newCapacity, String newDescription) {
        try {
            Session session = sessionRepository.findById(sessionID);
            if (session == null) {
                throw new IllegalArgumentException("Session not found.");
            }


            // Update session details
            session.setName(newName);
            session.setDateTime(newDate);
            session.setRoom(newRoom);
            session.setCapacity(newCapacity);
            session.setDescription(newDescription);


            sessionRepository.save(session);
        } catch (IOException e) {
            throw new RepositoryException("Error updating conference.", e);
        }
    }

    public void deleteSession(int sessionID, int conferenceID) {
        try {
            sessionRepository.delete(sessionID);

            conferenceService.removeSessionFromConference(conferenceID, sessionID);
        } catch (IOException e){
            throw new RepositoryException("Error deleting session.", e);
        }
    }

    public SessionDTO viewSessionDetails(int sessionID){
        try{
            Session session = sessionRepository.findById(sessionID);
            if (session == null){
                throw new IllegalArgumentException("Session not found.");
            }
            return mapToDTO(session);
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving session details.", e);
        }
    }

    public int getSessionIDByName(String sessionName) {
        try {
            // Retrieve all sessions from the repository
            List<SessionDTO> sessions = sessionRepository.findAll().stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

            // Find the session by name
            for (SessionDTO session : sessions) {
                if (session.getName().equalsIgnoreCase(sessionName)) {
                    return session.getSessionID();
                }
            }

            throw new IllegalArgumentException("Session with name " + sessionName + " not found.");
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving sessions.", e);
        }
    }


    public List<SessionDTO> listSessionsByConference(int conferenceID){
        try{
            return sessionRepository.findByConferenceID(conferenceID).stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (IOException e){
            throw new RepositoryException("Error retrieving sessions.", e);
        }
    }

    public void addFeedback(int sessionID, int feedbackID) {
        try {
            // Retrieve the session
            Session session = sessionRepository.findById(sessionID);

            if (session == null) {
                throw new IllegalArgumentException("Session not found: " + sessionID);
            }

            // Add feedback ID to the session
            session.addFeedback(feedbackID);

            // Save the updated session
            sessionRepository.save(session);

            System.out.println("Feedback ID " + feedbackID + " added to session ID " + sessionID);
        } catch (IOException e) {
            throw new RepositoryException("Error adding feedback to session.", e);
        }
    }


    public List<FeedbackDTO> viewSessionFeedback(int sessionID){
        try {
            return feedbackService.getSessionFeedbacks(sessionID);
        } catch (Exception e){
            throw new RepositoryException("Error retrieving session feedback.", e);
        }
    }


    public boolean doesSessionExist(int sessionID) {
        try {
            return sessionRepository.findById(sessionID) != null;
        } catch (IOException e) {
            throw new RepositoryException("Error checking session existence.", e);
        }
    }

    public void registerAttendance(int attendeeID, int sessionID){
        try{
            Session session = sessionRepository.findById(sessionID);

            if (session == null){
                throw new IllegalArgumentException("Session not found");
            }
            if (session.getAvailableSeats() == 0){
                throw new IllegalArgumentException("Session is fully booked");
            }

            if (!session.getSignedUpAttendees().contains(attendeeID) ){
                throw new IllegalArgumentException("Attendee is not signed up for the session");
            }

            //Register attendee attendance
            session.registerAttendee(attendeeID);
            sessionRepository.save(session);
        } catch (IOException e) {
            throw new RepositoryException("Error registering attendance.", e);
        }
    }

    public List<SessionDTO> getSessionsAttendedByAttendee(int attendeeID) {
        try {
            return sessionRepository.findAll().stream()
                    .filter(session -> session.getAttendedAttendees().contains(attendeeID))
                    .map(this::mapToDTO) // Map each Session to a SessionDTO
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving attended sessions.", e);
        }
    }


    public void checkScheduleConflicts(List<Integer> attendeeSessionIDs, int newSessionID) {
        try {
            // Retrieve the new session details
            Session newSession = sessionRepository.findById(newSessionID);
            if (newSession == null) {
                throw new IllegalArgumentException("Session not found: " + newSessionID);
            }

            // Loop through the attendee's current sessions to check for conflicts
            for (int existingSessionID : attendeeSessionIDs) {
                Session existingSession = sessionRepository.findById(existingSessionID);

                if (existingSession == null) {
                    continue; // Skip invalid session IDs
                }

                // Check if the sessions overlap (date and time must conflict)
                if (newSession.getDateTime().equals(existingSession.getDateTime()) && newSession.getSessionID() != existingSession.getSessionID()) {
                    throw new IllegalArgumentException(
                            "Schedule conflict: Session '" + newSession.getName() +
                                    "' conflicts with '" + existingSession.getName() + "'."
                    );
                }
            }
        } catch (IOException e) {
            throw new RepositoryException("Error checking for schedule conflicts.", e);
        }
    }

    public void addAttendeeToSession(int attendeeID, int sessionID) {
        try {
            // Retrieve the session details
            Session session = sessionRepository.findById(sessionID);
            if (session == null) {
                throw new IllegalArgumentException("Session not found: " + sessionID);
            }

            // Check if the session has available capacity
            if (session.getAvailableSeats() <= 0) {
                throw new IllegalArgumentException("Session is full: " + session.getName());
            }

            // Check if the attendee is already registered for the session
            if (session.isSignedUp(attendeeID)) {
                throw new IllegalArgumentException("Attendee is already signed up for session: " + session.getName());
            }

            // Add the attendee to the session
            session.addToSignedUp(attendeeID);

            // Update the session in the repository
            sessionRepository.save(session);
        } catch (IOException e) {
            throw new RepositoryException("Error adding attendee to session.", e);
        }
    }

    public void removeAttendeeFromSession(int attendeeID, int sessionID) {
        try {
            // Retrieve the session details
            Session session = sessionRepository.findById(sessionID);
            if (session == null) {
                throw new IllegalArgumentException("Session not found: " + sessionID);
            }

            // Check if the attendee is already registered for the session
            if (!session.isSignedUp(attendeeID)) {
                throw new IllegalArgumentException("Attendee is not signed up for session: " + session.getName());
            }

            // Add the attendee to the session
            session.removeFromSignedUp(attendeeID);

            if(session.hasAttended(attendeeID)){
                session.getAttendedAttendees().remove(Integer.valueOf(attendeeID));
            }

            // Update the session in the repository
            sessionRepository.save(session);
        } catch (IOException e) {
            throw new RepositoryException("Error adding attendee to session.", e);
        }
    }

    public List<Integer> getSignedUpAttendees(int sessionID){
        try {
            // Retrieve the session details
            Session session = sessionRepository.findById(sessionID);
            if (session == null) {
                throw new IllegalArgumentException("Session not found: " + sessionID);
            }

            return session.getSignedUpAttendees();
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving session attendees.", e);
        }
    }

    public List<Integer> getAttendedAttendees(int sessionID){
        try {
            // Retrieve the session details
            Session session = sessionRepository.findById(sessionID);
            if (session == null) {
                throw new IllegalArgumentException("Session not found: " + sessionID);
            }

            return session.getAttendedAttendees();
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving session attendees.", e);
        }
    }

    private SessionDTO mapToDTO(Session session){
        return new SessionDTO(session.getSessionID(),
                session.getName(),
                session.getDateTime(),
                session.getRoom(),
                session.getCapacity(),
                session.getSignedUpAttendees(),
                session.getAttendedAttendees(),
                session.getSpeakerID(),
                session.getDescription(),
                session.getConferenceID());
    }
}
