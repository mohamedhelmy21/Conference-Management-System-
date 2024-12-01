package service;

import dto.ConferenceDTO;

import domain.Conference;
import domain.Attendee;
import repository.ConferenceRepository;
import repository.UserRepository;
import exception.RepositoryException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ConferenceService {
    private ConferenceRepository conferenceRepository;
    private UserRepository userRepository;

    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
    }

    public Conference createConference(String name, String description, LocalDateTime startDate, LocalDateTime endDate, int managerID) {
        try {

            int conferenceID = generateConferenceID();

            validateConferenceDetails(name, startDate, endDate);

            Conference newConference = new Conference(
                    conferenceID,
                    name,
                    description,
                    startDate,
                    endDate
            );

            newConference.assignManager(managerID);

            conferenceRepository.save(newConference);

            return newConference;
        } catch (IOException e){
            throw new RepositoryException("Error creating conference", e);
        }
    }

    public void updateConference(int conferenceID, String newName, String newDescription, LocalDateTime newStartDate, LocalDateTime newEndDate) {
        try {
            Conference conference = conferenceRepository.findById(conferenceID);
            if (conference == null) {
                throw new IllegalArgumentException("Conference not found.");
            }

            validateConferenceDetails(newName, newStartDate, newEndDate);

            conference.setName(newName);
            conference.setDescription(newDescription);
            conference.setStartDate(newStartDate);
            conference.setEndDate(newEndDate);

            conferenceRepository.save(conference);
        } catch (IOException e) {
            throw new RepositoryException("Error updating conference.", e);
        }
    }

    public void deleteConference(int conferenceID) {
        try{
            conferenceRepository.delete(conferenceID);
        } catch (IOException e) {
            throw new RepositoryException("Error deleting conference.", e);
        }
    }

    private int generateConferenceID() {
        try {
            List<Conference> allConferences = conferenceRepository.findAll();
            if (allConferences.isEmpty()) {
                return 101; // Start ID from 101 as per the scenario
            }
            return allConferences.stream()
                    .mapToInt(Conference::getConferenceID)
                    .max()
                    .orElse(100) + 1;
        } catch (IOException e) {
            throw new RepositoryException("Error generating conference ID.", e);
        }
    }

    public ConferenceDTO findConferenceByID(int conferenceID) {
        try {
            Conference conference = conferenceRepository.findById(conferenceID);
            if (conference == null) {
                throw new IllegalArgumentException("Conference not found.");
            }
            return convertToDTO(conference);
        } catch (IOException e) {
            throw new RepositoryException("Error finding conference by ID.", e);
        }
    }

    private void validateConferenceDetails(String name, LocalDateTime startDate, LocalDateTime endDate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Conference name cannot be empty.");
        }
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid conference dates provided.");
        }
    }

    private ConferenceDTO convertToDTO(Conference conference) {
        return new ConferenceDTO(conference.getConferenceID(),
          conference.getName(),
          conference.getDescription(),
          conference.getStartDate(),
          conference.getEndDate(),
          conference.getSessionsIDs(),
          conference.getAttendeesIDs()
        );
    }
}
