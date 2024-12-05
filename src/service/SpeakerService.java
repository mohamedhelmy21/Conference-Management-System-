package service;

import domain.Speaker;
import dto.SpeakerDTO;
import dto.SessionDTO;
import exception.RepositoryException;
import repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SpeakerService {
    private final UserRepository userRepository;
    private final SessionService sessionService;

    public SpeakerService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
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
