package repository;

import domain.Session;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SessionRepository extends BaseRepository<Session> {

    private static SessionRepository instance;

    private SessionRepository(String filePath) {
        super(filePath, Session.class);
    }

    public static SessionRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new SessionRepository(filePath);
        }
        return instance;
    }

    @Override
    protected Integer getId(Session session){
        return session.getSessionID();
    }

    @Override
    public List<Session> findAll() throws IOException {
        List<Session> sessions = super.findAll();

        // Ensure all sessions have properly initialized observers
        for (Session session : sessions) {
            if (session.getObservers() == null) {
                session.setObservers(new ArrayList<>());
            }
        }

        return sessions;
    }

    public List<Session> findBySpeakerID(int speakerID) throws IOException {
        return findAll().stream().filter(session -> session.getSpeakerID() == speakerID).collect(Collectors.toList());
    }

    public List<Session> findByDate(LocalDateTime date) throws IOException {
        return findAll().stream()
                .filter(session -> session.getDateTime().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
    }

    public List<Session> findByConferenceID(int conferenceID) throws IOException {
        return findAll().stream().filter(session -> session.getConferenceID() == conferenceID).collect(Collectors.toList());
    }


}

