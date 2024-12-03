package service;

import domain.Session;
import dto.FeedbackDTO;
import dto.SessionDTO;
import exception.RepositoryException;
import repository.SessionRepository;
import utility.IDGenerator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SessionService {
    private final SessionRepository sessionRepository;
    private final ConferenceService conferenceService;

    public SessionService(SessionRepository sessionRepository, ConferenceService conferenceService) {
        this.sessionRepository = sessionRepository;
        this.conferenceService = conferenceService;
    }

    public SessionDTO createSession(String name, LocalDateTime dateTime, String room, int capacity, int speakerID, String description, int conferenceID) {
        try{
            int sessionID = IDGenerator.generateId("Session");

            Session session = new Session(sessionID, name, dateTime, room, capacity, speakerID, description, conferenceID);

            sessionRepository.save(session);

            conferenceService.addSessionToConference(conferenceID, sessionID);

            return mapToDTO(session);
        } catch (IOException e){
            throw new RepositoryException("Error creating session.", e);
        }
    }

    public void deleteSession(int sessionID, int conferenceID) {
        try {
            sessionRepository.delete(sessionID);

            conferenceService.removeSessionToConference(conferenceID, sessionID);
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

    public List<SessionDTO> listSessionsByConference(int conferenceID){
        try{
            return sessionRepository.findByConferenceID(conferenceID).stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (IOException e){
            throw new RepositoryException("Error retrieving sessions.", e);
        }
    }

    public List<FeedbackDTO> viewSessionFeedback(int sessionID){
        try {
            return feedbackService.listFeedbackForSession(sessionID);
        } catch (Exception e){
            throw new RepositoryException("Error retrieving session feedback.", e);
        }
    }

    private SessionDTO mapToDTO(Session session){
        return new SessionDTO(session.getSessionID(),
                session.getName(),
                session.getDateTime(),
                session.getRoom(),
                session.getCapacity(),
                session.getSpeakerID(),
                session.getDescription());
    }
}
