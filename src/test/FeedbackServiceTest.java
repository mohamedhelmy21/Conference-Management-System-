package test;

import enums.Rating;
import repository.FeedbackRepository;
import repository.SessionRepository;
import service.FeedbackService;
import service.SessionService;


import java.util.List;

public class FeedbackServiceTest {
    public static void main(String[] args) {
        // Initialize repositories
        FeedbackRepository feedbackRepository = FeedbackRepository.getInstance("data/feedback.json");
        SessionRepository sessionRepository = SessionRepository.getInstance("data/sessions.json");

        // Initialize services

        FeedbackService feedbackService = new FeedbackService(feedbackRepository);
        SessionService sessionService = new SessionService(sessionRepository, null, null); // Replace nulls with actual dependencies if needed
        feedbackService.setSessionService(sessionService);

        try {
             // Test submitting feedback
            System.out.println("Submitting feedback...");
            var feedbackDTO1 = feedbackService.submitFeedback(
                    1, // Attendee ID
                    1,   // Session ID
                    "Amazing session! Learned a lot about AI.",
                    Rating.FIVE,
                    false // Not anonymous
            );
            System.out.println("Feedback submitted: " + feedbackDTO1);

            var feedbackDTO2 = feedbackService.submitFeedback(
                    102, // Another attendee
                    1,   // Same session ID
                    "Very informative, but could use more examples.",
                    Rating.FOUR,
                    true // Anonymous feedback
            );
            System.out.println("Feedback submitted: " + feedbackDTO2);


            // 2. Test retrieving session feedback
            System.out.println("\nRetrieving feedback for session ID 1...");
            List<dto.FeedbackDTO> feedbackList = feedbackService.getSessionFeedbacks(1);
            System.out.println("Feedback for session ID 1:");
            for (var feedback : feedbackList) {
                System.out.println(feedback);
            }

            // 3. Test calculating average rating
            System.out.println("\nCalculating average rating for session ID 1...");
            double averageRating = feedbackService.getAverageRating(1);
            System.out.println("Average rating: " + averageRating);

        } catch (Exception e) {
            e.printStackTrace(); // Print the full stack trace for debugging
        }
    }
}

