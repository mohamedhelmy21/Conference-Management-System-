package repository;

import domain.Session;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SessionRepository extends BaseRepository<Session> {

    public SessionRepository(String filePath) {
        super(filePath, Session.class);
    }

    @Override
    protected Integer getId(Session session){
        return session.getSessionID();
    }

    public List<Session> findBySpeakerID(int speakerID) throws IOException {
        return findAll().stream().filter(session -> session.getSpeakerID() == speakerID).collect(Collectors.toList());
    }

    public List<Session> findByDate(LocalDateTime date) throws IOException {
        return findAll().stream()
                .filter(session -> session.getDateTime().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
    }


}

