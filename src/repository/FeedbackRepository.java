package repository;


import domain.Feedback;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackRepository extends BaseRepository<Feedback>{

    public FeedbackRepository(String filePath) {
        super(filePath, Feedback.class);
    }

    @Override
    protected Integer getId(Feedback feedback){
        return feedback.getFeedbackID();
    }

    public List<Feedback> findBySessionID(int sessionID) throws IOException {
        return findAll().stream().filter(feedback -> feedback.getSessionID() == sessionID).collect(Collectors.toList());
    }

    public List<Feedback> findByAttendeeID(int attendeeID) throws IOException {
        return findAll().stream().filter(feedback -> feedback.getAttendeeID() == attendeeID).collect(Collectors.toList());
    }

}

