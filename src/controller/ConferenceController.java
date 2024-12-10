package controller;

import dto.ConferenceDTO;
import exception.RepositoryException;
import service.ConferenceService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ConferenceController {
    private final ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }


    public List<ConferenceDTO> listAllConferences() {
        try {
            List<ConferenceDTO> conferences = conferenceService.getAllConferences();
            if (conferences.isEmpty()) {
                System.out.println("No conferences found.");
            } else {
                System.out.println("Available Conferences:");
                conferences.forEach(conf -> System.out.println("ID: " + conf.getConferenceID() + ", Name: " + conf.getName()));
            }
            return conferences;
        } catch (Exception e) {
            System.err.println("Error retrieving conferences: " + e.getMessage());
            return null;
        }
    }

    public void updateConference(int conferenceID, String newName, String newDescription, LocalDateTime newStartDate, LocalDateTime newEndDate){
        ConferenceDTO conference = conferenceService.getConferenceDetails(conferenceID);
        if (conference == null) {
            System.out.println("Conference not found with ID: " + conferenceID);
        }

        conferenceService.updateConference(conferenceID, newName, newDescription, newStartDate, newEndDate);
    }

    public void deleteConference(int conferenceID) {
        conferenceService.deleteConference(conferenceID);
    }


    public ConferenceDTO viewConferenceDetails(int conferenceID) {
        try {
            ConferenceDTO conference = conferenceService.getConferenceDetails(conferenceID);
            if (conference == null) {
                System.out.println("Conference not found with ID: " + conferenceID);
                return null;
            }
            System.out.println("Conference Details:");
            System.out.println("ID: " + conference.getConferenceID());
            System.out.println("Name: " + conference.getName());
            System.out.println("Description: " + conference.getDescription());
            System.out.println("Start Date: " + conference.getStartDate());
            System.out.println("End Date: " + conference.getEndDate());
            return conference;
        } catch (Exception e) {
            System.err.println("Error retrieving conference details: " + e.getMessage());
            return null;
        }
    }

    public List<Integer> getConferenceAttendees(int conferenceID){
        try {
            ConferenceDTO conference = conferenceService.getConferenceDetails(conferenceID);
            if (conference == null) {
                System.out.println("Conference not found with ID: " + conferenceID);
                return null;
            }
            return conferenceService.getConferenceAttendees(conferenceID);
        } catch (Exception e) {
            System.err.println("Error retrieving conference attendees: " + e.getMessage());
            return null;
        }
    }
}

