
package service;

import domain.Attendee;
import domain.Schedule;
import domain.User;
import dto.*;
import exception.RepositoryException;
import repository.UserRepository;
import enums.Rating;

import java.io.IOException;
import java.util.List;


public class AttendeeService {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final CertificateService certificateService;
    private final FeedbackService feedbackService;
    private final ConferenceService conferenceService;
    private final SpeakerService speakerService;

    public AttendeeService(UserRepository userRepository, SessionService sessionService, CertificateService certificateService, FeedbackService feedbackService, ConferenceService conferenceService, SpeakerService speakerService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.certificateService = certificateService;
        this.feedbackService = feedbackService;
        this.conferenceService = conferenceService;
        this.speakerService = speakerService;
    }

    public boolean doesAttendeeExist(int attendeeID) {
        try {
            return userRepository.findById(attendeeID) != null;
        } catch (IOException e) {
            throw new RepositoryException("Error checking attendee existence.", e);
        }
    }

    private Attendee getAttendeeById(int attendeeID) throws IOException {
        // Retrieve the user from the repository
        User user = userRepository.findById(attendeeID);

        // Check if the user exists and is an attendee
        if (user == null || !(user instanceof Attendee)) {
            throw new IllegalArgumentException("Attendee not found with ID: " + attendeeID);
        }

        // Cast and return the user as an Attendee
        return (Attendee) user;
    }

    public String getAttendeeName(int attendeeID) {
        try {
            // Retrieve the attendee object
            Attendee attendee = getAttendeeById(attendeeID);

            // Return the attendee's name
            return attendee.getName();
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving attendee name.", e);
        }
    }


    public void addSessionToSchedule(int attendeeID, int sessionID){
        try {
            Attendee attendee = (Attendee) userRepository.findById(attendeeID);

            if (attendee == null) {
                throw new IllegalArgumentException("Attendee not found");
            }

            //Delegate conflict checks to session service
            sessionService.checkScheduleConflicts(attendee.getSchedule().getSessionsIDs(), sessionID);

            //Add attendee to the session
            sessionService.addAttendeeToSession(attendeeID, sessionID);

            //add to schedule
            attendee.getSchedule().addSession(sessionID);
            userRepository.save(attendee);
        } catch (IOException e) {
            throw new RepositoryException("Error adding session to schedule.", e);
        }
    }

    public void removeSessionFromSchedule(int attendeeID, int sessionID){
        try{
            Attendee attendee = (Attendee) userRepository.findById(attendeeID);
            if (attendee == null) {
                throw new IllegalArgumentException("Attendee not found");
            }

            //Remove attendee from session
            sessionService.removeAttendeeFromSession(attendeeID, sessionID);

            //Remove session from schedule
            attendee.getSchedule().removeSession(sessionID);
            userRepository.save(attendee);
        } catch(IOException e){
            throw new RepositoryException("Error removing session from schedule.", e);
        }
    }

    public void registerAttendance(int attendeeID, int sessionID){
        sessionService.registerAttendance(attendeeID, sessionID);
    }

    public ScheduleDTO viewSchedule(int attendeeID){
        try{
            Attendee attendee = (Attendee) userRepository.findById(attendeeID);
            if (attendee == null) {
                throw new IllegalArgumentException("Attendee not found");
            }
            Schedule schedule = attendee.getSchedule();
            return new ScheduleDTO(schedule.getScheduleID() , attendeeID, schedule.getSessionsIDs());
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving schedule.", e);
        }
    }

    public List<Integer> getAttendeeScheduleSessions(int attendeeID){
        try{
            Attendee attendee = (Attendee) userRepository.findById(attendeeID);
            if (attendee == null) {
                throw new IllegalArgumentException("Attendee not found");
            }
            Schedule schedule = attendee.getSchedule();
            return schedule.getSessionsIDs();
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving sessions.", e);
        }
    }

    public void registerAttendeeToConference(int attendeeID, int conferenceID) {
        try {
            // Ensure the attendee exists
            Attendee attendee = (Attendee) userRepository.findById(attendeeID);
            if (attendee == null) {
                throw new IllegalArgumentException("Attendee not found");
            }

            // Delegate to ConferenceService
            conferenceService.addAttendeeToConference(conferenceID, attendeeID);
        } catch (IOException e) {
            throw new RepositoryException("Error registering attendee to conference.", e);
        }
    }

    public void assignCertificateToAttendee(int attendeeID, int certificateID) {
        try {
            Attendee attendee = getAttendeeById(attendeeID);
            attendee.addCertificate(certificateID);
            userRepository.save(attendee); // Persist changes to the repository
        } catch (IOException e) {
            throw new RepositoryException("Error assigning certificate to attendee.", e);
        }
    }


    public CertificateDTO getCertificate(int certificateID){
            return certificateService.getCertificate(certificateID);
    }

    public void submitFeedback(int attendeeID, int sessionID, String comments, Rating rating, boolean isAnonymous) {
        try {
            // Submit feedback through FeedbackService
            FeedbackDTO feedbackDTO = feedbackService.submitFeedback(attendeeID, sessionID, comments, rating, isAnonymous);

            // Retrieve the attendee
            Attendee attendee = (Attendee) userRepository.findById(attendeeID);
            if (attendee == null) {
                throw new IllegalArgumentException("Attendee not found");
            }

            // Add feedback ID to the attendee's feedbacks list
            attendee.getFeedbacks().add(feedbackDTO.getFeedbackID());

            // Save the updated attendee to persist changes
            userRepository.save(attendee);
        } catch (IOException e) {
            throw new RepositoryException("Error submitting feedback.", e);
        }
    }


    // View session details
    public SessionDTO viewSessionDetails(int sessionID) {
        return sessionService.viewSessionDetails(sessionID);
    }

    // View conference details
    public ConferenceDTO viewConferenceDetails(int conferenceID) {
        return conferenceService.getConferenceDetails(conferenceID);
    }

    //View speaker bios
    public String viewSpeakerBio(int speakerID) {
        return speakerService.getSpeakerBio(speakerID);
    }

    // Map Attendee to DTO
    private AttendeeDTO mapToDTO(Attendee attendee) {
        return new AttendeeDTO(
                attendee.getUserID(),
                attendee.getName(),
                attendee.getEmail(),
                attendee.getSchedule(),
                attendee.getFeedbacks(),
                attendee.getCertificate()
        );
    }
}
