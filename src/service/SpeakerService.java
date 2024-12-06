package service;

import domain.Speaker;
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

    private SpeakerDTO mapToDTO(Speaker speaker) {
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
