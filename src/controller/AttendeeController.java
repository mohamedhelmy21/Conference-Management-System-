package controller;

import domain.Attendee;
import dto.*;
import enums.Rating;
import exception.RepositoryException;
import service.*;

import java.io.IOException;
import java.util.List;

public class AttendeeController {
    private final AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    // Display available sessions
    public List<SessionDTO> getAvailableSessions(int conferenceID) {
        return attendeeService.getAvailableSessions(conferenceID); // Service handles session retrieval
    }

    public List<ConferenceDTO> getAvailableConferences(){
        return attendeeService.getAvailableConferences();
    }

    public List<Integer> getRegisteredConferences(int attendeeID) {
        return attendeeService.getRegisteredConferences(attendeeID);
    }

    // Add session to schedule by session ID
    public boolean addSessionToSchedule(int attendeeID, int sessionID) {
        try {
            attendeeService.addSessionToSchedule(attendeeID, sessionID);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding session to schedule: " + e.getMessage());
            return false;
        }
    }

    // Remove session from schedule by session ID
    public boolean removeSessionFromSchedule(int attendeeID, int sessionID) {
        try {
            attendeeService.removeSessionFromSchedule(attendeeID, sessionID);
            return true;
        } catch (Exception e) {
            System.err.println("Error removing session from schedule: " + e.getMessage());
            return false;
        }
    }

    // View attendee's schedule
    public ScheduleDTO viewSchedule(int attendeeID) {
        return attendeeService.viewSchedule(attendeeID);
    }

    public boolean registerForConference(int attendeeID, int conferenceID) {
        try {
            // Check if attendee is already registered for a conference
            int currentConferenceID = attendeeService.getAttendeeConferenceID(attendeeID);
            if (currentConferenceID != -1) {
                throw new IllegalArgumentException("Attendee is already registered for a conference with ID: " + currentConferenceID);
            }

            // Register the attendee for the conference
            attendeeService.registerAttendeeToConference(attendeeID, conferenceID);
            return true;
        } catch (Exception e) {
            System.err.println("Error registering for conference: " + e.getMessage());
            return false;
        }
    }

    public int getAttendeeConferenceID(int attendeeID) {
        return attendeeService.getAttendeeConferenceID(attendeeID);
    }


    public int getSessionIDByName(String sessionName) {
        return attendeeService.getSessionIDByName(sessionName);
    }

    public List<SessionDTO> getSessionsAttendedByAttendee(int attendeeID){
        return attendeeService.getSessionsAttendedByAttendee(attendeeID);
    }

    // Submit feedback for a session
    public boolean submitFeedback(int attendeeID, int sessionID, String comments, Rating rating, boolean isAnonymous) {
        try {
            if (hasSubmittedFeedback(attendeeID, sessionID)) {
                throw new IllegalArgumentException("You have already submitted feedback for this session.");
            }

            attendeeService.submitFeedback(attendeeID, sessionID, comments, rating, isAnonymous);
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Error submitting feedback: " + e.getMessage());
            return false;
        }
    }

    public boolean hasSubmittedFeedback(int attendeeID, int sessionID) {
        try {
            // Check if the attendee has already submitted feedback for the session
            return attendeeService.hasSubmittedFeedback(attendeeID, sessionID);
        } catch (Exception e) {
            System.err.println("Error checking existing feedback: " + e.getMessage());
            return false;
        }
    }

    // View session details
    public SessionDTO viewSessionDetails(int sessionID) {
        return attendeeService.viewSessionDetails(sessionID);
    }

    // View speaker bio
    public String viewSpeakerBio(int speakerID) {
        return attendeeService.viewSpeakerBio(speakerID);
    }

    public String viewSpeakerName(int speakerID) {
        return attendeeService.getSpeakerName(speakerID);
    }


    // View conference details
    public ConferenceDTO viewConferenceDetails(int conferenceID) {
        return attendeeService.viewConferenceDetails(conferenceID);
    }

    // Retrieve certificate details
    public CertificateDTO getCertificateDetails(int attendeeID) {
        try {
            // Retrieve certificate ID from attendee
            int certificateID = attendeeService.getAttendeeCertificateID(attendeeID);

            // Retrieve certificate details
            return attendeeService.getCertificate(certificateID);
        } catch (Exception e) {
            System.err.println("Error retrieving certificate details: " + e.getMessage());
            return null;
        }
    }
}