package service;

import domain.Speaker;
import domain.User;
import dto.FeedbackDTO;
import dto.SpeakerDTO;
import dto.SessionDTO;
import enums.Role;
import exception.RepositoryException;
import repository.UserRepository;
import utility.IDGenerator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SpeakerService {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final FeedbackService feedbackService;

    public SpeakerService(UserRepository userRepository, SessionService sessionService, FeedbackService feedbackService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.feedbackService = feedbackService;
    }

    public SpeakerDTO createSpeaker(String name, String email, String password, String bio, String expertise, String organization) {
        try {
            // Validate if the speaker already exists
            if (userRepository.findByEmail(email) != null) {
                throw new IllegalArgumentException("A speaker with this email already exists");
            }

            // Generate a unique speaker ID
            int speakerID = IDGenerator.generateId("User");

            // Create a new Speaker object
            Speaker speaker = new Speaker(speakerID, name, email, password, Role.SPEAKER, bio, expertise, organization);

            // Save the speaker to the repository
            userRepository.save(speaker);

            // Map the speaker to a UserDTO and return
            return mapToDTO(speaker);
        } catch (IOException e) {
            throw new RepositoryException("Error creating speaker.", e);
        }
    }

    public String getSpeakerName(int speakerID) {
        try {
            // Retrieve the speaker from the repository
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null || speaker.getRole() != Role.SPEAKER) {
                throw new IllegalArgumentException("Speaker not found with ID: " + speakerID);
            }

            // Return the speaker's name
            return speaker.getName();
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving speaker name.", e);
        }
    }



    public SpeakerDTO getSpeakerProfile(int speakerID){
        try {
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null || !speaker.getRole().equals(enums.Role.SPEAKER)) {
                throw new IllegalArgumentException("Speaker not found or invalid role");
            }
            return mapToDTO(speaker);
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving speaker profile.", e);
        }
    }

    public String getSpeakerBio(int speakerID) {
        try {
            // Retrieve the speaker from the repository
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null || !speaker.getRole().equals(enums.Role.SPEAKER)) {
                throw new IllegalArgumentException("Speaker not found or invalid role");
            }

            // Return the bio of the speaker
            return speaker.getBio();
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving speaker bio.", e);
        }
    }

    public void updateSpeakerBio(int speakerID, String newBio){
        try {
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null || !speaker.getRole().equals(enums.Role.SPEAKER)) {
                throw new IllegalArgumentException("Speaker not found or invalid role");
            }
            speaker.updateBio(newBio);
            userRepository.save(speaker);
        } catch (IOException e) {
            throw new RepositoryException("Error updating speaker bio.", e);
        }
    }

    public void updateSpeakerExpertise(int speakerID, String newExpertise){
        try {
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null || !speaker.getRole().equals(enums.Role.SPEAKER)) {
                throw new IllegalArgumentException("Speaker not found or invalid role");
            }
            speaker.updateExpertise(newExpertise);
            userRepository.save(speaker);
        } catch (IOException e) {
            throw new RepositoryException("Error updating speaker expertise.", e);
        }
    }

    public void updateSpeakerOrganization(int speakerID, String newOrganization){
        try {
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null || !speaker.getRole().equals(enums.Role.SPEAKER)) {
                throw new IllegalArgumentException("Speaker not found or invalid role");
            }
            speaker.updateOrganization(newOrganization);
            userRepository.save(speaker);
        } catch (IOException e) {
            throw new RepositoryException("Error updating speaker organization.", e);
        }
    }



    public void addSessionToSpeaker(int speakerID, int sessionID) {
        try {
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null || !speaker.getRole().equals(enums.Role.SPEAKER)) {
                throw new IllegalArgumentException("Speaker not found or invalid role");
            }

            // Check if the session is already assigned
            if (speaker.getSessionsIDs().contains(sessionID)) {
                throw new IllegalArgumentException("Session already assigned to this speaker.");
            }

            // Add the session ID to the speaker's session list
            speaker.getSessionsIDs().add(sessionID);

            // Save the updated speaker
            userRepository.save(speaker);
        } catch (IOException e) {
            throw new RepositoryException("Error adding session to speaker.", e);
        }
    }

    public void removeSessionFromSpeaker(int speakerID, int sessionID){
        try {
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null || !speaker.getRole().equals(enums.Role.SPEAKER)) {
                throw new IllegalArgumentException("Speaker not found or invalid role");
            }

            if (!speaker.getSessionsIDs().contains(sessionID)) {
                throw new IllegalArgumentException("Session is not assigned to this speaker.");
            }


            speaker.getSessionsIDs().remove(sessionID);

            // Save the updated speaker
            userRepository.save(speaker);
        } catch (IOException e) {
            throw new RepositoryException("Error adding session to speaker.", e);
        }
    }


    public List<SessionDTO> getSpeakerSessions(int speakerID) {
        try {
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null || !speaker.getRole().equals(enums.Role.SPEAKER)) {
                throw new IllegalArgumentException("Speaker not found or invalid role");
            }

            // Use the speaker's session IDs to retrieve details
            List<Integer> sessionIDs = speaker.getSessionsIDs();
            return sessionIDs.stream()
                    .map(sessionService::viewSessionDetails) // Delegate to SessionService
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RepositoryException("Error retrieving speaker sessions.", e);
        }
    }

    public List<FeedbackDTO> getSessionFeedback(int sessionID) {
        try {
            // Use FeedbackService to fetch feedback for the session
            return feedbackService.getSessionFeedbacks(sessionID); // Assumes this method exists in FeedbackService
        } catch (Exception e) {
            throw new RepositoryException("Error retrieving feedback for session.", e);
        }
    }

    // Retrieve average rating for a session
    public double getSessionAverageRating(int sessionID) {
        try {
            // Use FeedbackService to fetch average rating for the session
            return feedbackService.getAverageRating(sessionID); // Assumes this method exists in FeedbackService
        } catch (Exception e) {
            throw new RepositoryException("Error retrieving average rating for session.", e);
        }
    }

    public List<SpeakerDTO> listAllSpeakers() {
        try {
            return userRepository.findAll().stream()
                    .filter(user -> user.getRole() == Role.SPEAKER) // Ensure it's a speaker
                    .map(this::mapToDTO) // Explicit lambda for mapping
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RepositoryException("Error listing all speakers.", e);
        }
    }

    public void deleteSpeaker(int speakerID) {
        try {
            // Fetch the speaker
            Speaker speaker = (Speaker) userRepository.findById(speakerID);
            if (speaker == null) {
                throw new IllegalArgumentException("Speaker not found with ID: " + speakerID);
            }

            // Remove all associated sessions
            List<Integer> sessionIDs = speaker.getSessionsIDs();
            for (int sessionID : sessionIDs) {
                SessionDTO session = sessionService.viewSessionDetails(sessionID);
                sessionService.deleteSession(sessionID, session.getConferenceID()); // Assuming deleteSession(sessionID) exists in SessionService
            }

            // Remove the speaker from the repository
            userRepository.delete(speakerID);

            System.out.println("Speaker with ID " + speakerID + " and all associated sessions have been deleted.");
        } catch (IOException e) {
            throw new RepositoryException("Error deleting speaker and associated sessions.", e);
        }
    }



    private SpeakerDTO mapToDTO(User user) {
        if (!(user instanceof Speaker)) {
            throw new IllegalArgumentException("User is not a Speaker.");
        }
        Speaker speaker = (Speaker) user;
        return new SpeakerDTO(
                speaker.getUserID(),
                speaker.getName(),
                speaker.getEmail(),
                speaker.getBio(),
                speaker.getExpertise(),
                speaker.getOrganization(),
                speaker.getSessionsIDs()
        );
    }
}
