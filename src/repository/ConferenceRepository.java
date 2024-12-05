package repository;


import domain.Conference;



import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConferenceRepository extends BaseRepository<Conference> {
    private static ConferenceRepository instance;

    private ConferenceRepository(String filePath) {
        super(filePath, Conference.class);
    }

    public static ConferenceRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ConferenceRepository(filePath);
        }
        return instance;
    }

    @Override
    protected Integer getId(Conference conference) {
        return conference.getConferenceID();
    }

    public List<Conference> findByManagerID(int managerID) throws IOException {
        return findAll().stream()
                .filter(conference -> conference.getManagersIDs().contains(managerID))
                .collect(Collectors.toList());
    }
}
