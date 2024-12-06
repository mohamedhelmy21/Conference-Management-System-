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

    // Register attendee to a conference
    public boolean registerForConference(int attendeeID, int conferenceID) {
        try {
            attendeeService.registerAttendeeToConference(attendeeID, conferenceID);
            return true;
        } catch (Exception e) {
            System.err.println("Error registering for conference: " + e.getMessage());
            return false;
        }
    }

    // Submit feedback for a session
    public boolean submitFeedback(int attendeeID, int sessionID, String comments, Rating rating, boolean isAnonymous) {
        try {
            attendeeService.submitFeedback(attendeeID, sessionID, comments, rating, isAnonymous);
            return true;
        } catch (Exception e) {
            System.err.println("Error submitting feedback: " + e.getMessage());
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