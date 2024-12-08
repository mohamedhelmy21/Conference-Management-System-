package service;

import domain.Feedback;
import dto.FeedbackDTO;
import enums.Rating;
import exception.RepositoryException;
import repository.FeedbackRepository;
import utility.IDGenerator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private SessionService sessionService;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Setter to inject SessionService after initialization
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public FeedbackDTO submitFeedback(int attendeeID, int sessionID, String comments, Rating rating, boolean isAnonymous) {
        try {
            if (!sessionService.doesSessionExist(sessionID)){
                throw new IllegalArgumentException("Session not found");
            }

            int feedbackID = IDGenerator.generateId("Feedback");

            Feedback feedback = new Feedback(feedbackID, attendeeID, sessionID, comments, rating, isAnonymous);

            feedbackRepository.save(feedback);

            sessionService.addFeedback(sessionID, feedbackID);

            return mapToDTO(feedback);

        } catch (IOException e) {
            throw new RepositoryException("Error saving feedback.", e);
        }
    }

    public FeedbackDTO getFeedback(int feedbackID) {
        try {
            Feedback feedback = feedbackRepository.findById(feedbackID);
            if (feedback == null) {
                throw new IllegalArgumentException("Feedback not found for ID: " + feedbackID);
            }
            return mapToDTO(feedback);
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving feedback.", e);
        }
    }

    public boolean isFeedbackForSession(int feedbackID, int sessionID) {
        FeedbackDTO feedback = getFeedback(feedbackID);
        return feedback != null && feedback.getSessionID() == sessionID;
    }

    public List<FeedbackDTO> getSessionFeedbacks(int sessionID) {
        try{
            if (!sessionService.doesSessionExist(sessionID)){
                throw new IllegalArgumentException("Session not found");
            }

            List<Feedback> feedbackList = feedbackRepository.findBySessionID(sessionID);

            return feedbackList.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving feedback for session.", e);
        }
    }

    public double getAverageRating(int sessionID) {
        try {
            // Retrieve feedback for the session
            List<Feedback> feedbacks = feedbackRepository.findBySessionID(sessionID);

            // If no feedback, return 0
            if (feedbacks.isEmpty()) {
                return 0;
            }

            // Calculate average rating
            return feedbacks.stream()
                    .mapToDouble(feedback -> feedback.getRating().getValue()) // Enum ordinal + 1 gives numeric rating
                    .average()
                    .orElse(0);
        } catch (IOException e) {
            throw new RepositoryException("Error calculating average rating for session ID: " + sessionID, e);
        }
    }

    private FeedbackDTO mapToDTO(Feedback feedback) {
        return new FeedbackDTO(
                feedback.getFeedbackID(),
                feedback.getAttendeeID(),
                feedback.getSessionID(),
                feedback.getComments(),
                feedback.getRating(),
                feedback.isAnonymous()
        );
    }

}
