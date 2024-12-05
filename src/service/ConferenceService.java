package service;

import dto.ConferenceDTO;

import utility.IDGenerator;
import domain.Conference;
import repository.ConferenceRepository;
import exception.RepositoryException;

import java.io.IOException;
import java.time.LocalDateTime;

public class ConferenceService {
    private final ConferenceRepository conferenceRepository;

    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public Conference createConference(String name, String description, LocalDateTime startDate, LocalDateTime endDate, int managerID) {
        try {

            int conferenceID = IDGenerator.generateId("Conference");

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

    public void addSessionToConference(int conferenceID, int sessionID) {
        try{
            Conference conference = conferenceRepository.findById(conferenceID);
            if (conference == null) {
                throw new IllegalArgumentException("Conference not found.");
            }

            if (conference.getSessionsIDs().contains(sessionID)) {
                throw new IllegalArgumentException("Session already exists.");
            }

            conference.addSession(sessionID);
            conferenceRepository.save(conference);
        } catch (IOException e) {
            throw new RepositoryException("Error adding session to conference.", e);
        }
    }

    public void removeSessionToConference(int conferenceID, int sessionID) {
        try{
            Conference conference = conferenceRepository.findById(conferenceID);
            if (conference == null) {
                throw new IllegalArgumentException("Conference not found.");
            }

            if (!(conference.getSessionsIDs().contains(sessionID))) {
                throw new IllegalArgumentException("Session is not in conference.");
            }

            conference.removeSession(sessionID);
            conferenceRepository.save(conference);
        } catch (IOException e) {
            throw new RepositoryException("Error removing session from conference.", e);
        }
    }

    public void addAttendeeToConference(int conferenceID, int attendeeID) {
        try {
            // Retrieve the conference
            Conference conference = conferenceRepository.findById(conferenceID);
            if (conference == null) {
                throw new IllegalArgumentException("Conference not found");
            }

            // Add the attendee ID to the conference
            conference.addAttendee(attendeeID);

            // Save the updated conference
            conferenceRepository.save(conference);
        } catch (IOException e) {
            throw new RepositoryException("Error adding attendee to conference.", e);
        }
    }

    public void deleteConference(int conferenceID) {
        try{
            conferenceRepository.delete(conferenceID);
        } catch (IOException e) {
            throw new RepositoryException("Error deleting conference.", e);
        }
    }

    public ConferenceDTO getConferenceDetails(int conferenceID) {
        try {
            Conference conference = conferenceRepository.findById(conferenceID);
            if (conference == null) {
                throw new IllegalArgumentException("Conference not found.");
            }
            return mapToDTO(conference);
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

    private ConferenceDTO mapToDTO(Conference conference) {
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
