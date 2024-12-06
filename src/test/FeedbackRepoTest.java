package test;
import domain.Feedback;
import enums.Rating;
import repository.FeedbackRepository;

import java.io.IOException;
import java.util.List;

public class FeedbackRepoTest {
    public static void main(String[] args) {
        try {
            // Initialize repository
            String filePath = "data/feedback.json"; // Adjust path as necessary
            FeedbackRepository feedbackRepository = FeedbackRepository.getInstance(filePath);

            // Test 1: Add feedbacks
            System.out.println("=== Test 1: Add Feedbacks ===");
            Feedback feedback1 = new Feedback(
                    301, 1, 201, "Great session on LLMs!", Rating.FOUR, false
            );
            Feedback feedback2 = new Feedback(
                    302, 2, 202, "Informative but could use better visuals.", Rating.THREE, true
            );
            feedbackRepository.save(feedback1);
            feedbackRepository.save(feedback2);

            // Test 2: Retrieve all feedbacks
            System.out.println("\n=== Test 2: Retrieve All Feedbacks ===");
            List<Feedback> allFeedbacks = feedbackRepository.findAll();
            for (Feedback feedback : allFeedbacks) {
                System.out.println(feedback);
            }

            // Test 3: Find feedback by ID
            System.out.println("\n=== Test 3: Find Feedback by ID ===");
            Feedback foundFeedback = feedbackRepository.findById(301);
            System.out.println(foundFeedback != null ? foundFeedback : "Feedback not found!");

            // Test 4: Update feedback
            System.out.println("\n=== Test 4: Update Feedback ===");
            feedback1.setComments("Excellent session on LLMs!");
            feedback1.setRating(Rating.FIVE);
            feedbackRepository.save(feedback1);
            System.out.println("Updated Feedback: " + feedbackRepository.findById(301));

            // Test 5: Find feedbacks by session ID
            System.out.println("\n=== Test 5: Find Feedbacks by Session ID ===");
            List<Feedback> sessionFeedbacks = feedbackRepository.findBySessionID(201);
            System.out.println("Feedbacks for Session ID 201:");
            for (Feedback feedback : sessionFeedbacks) {
                System.out.println(feedback);
            }

            // Test 6: Find feedbacks by attendee ID
            System.out.println("\n=== Test 6: Find Feedbacks by Attendee ID ===");
            List<Feedback> attendeeFeedbacks = feedbackRepository.findByAttendeeID(2);
            System.out.println("Feedbacks from Attendee ID 2:");
            for (Feedback feedback : attendeeFeedbacks) {
                System.out.println(feedback);
            }

            // Test 7: Delete feedback
            System.out.println("\n=== Test 7: Delete Feedback ===");
            feedbackRepository.delete(302);
            System.out.println("Feedbacks after deletion:");
            for (Feedback feedback : feedbackRepository.findAll()) {
                System.out.println(feedback);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
