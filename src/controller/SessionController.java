package controller;

import dto.FeedbackDTO;
import dto.SessionDTO;
import service.FeedbackService;
import service.SessionService;

import java.util.List;

public class SessionController {
    private final SessionService sessionService;
    private final FeedbackService feedbackService;

    public SessionController(SessionService sessionService, FeedbackService feedbackService) {
        this.sessionService = sessionService;
        this.feedbackService = feedbackService;
    }


    public SessionDTO viewSessionDetails(int sessionID) {
        try {
            return sessionService.viewSessionDetails(sessionID);
        } catch (Exception e) {
            System.err.println("Error viewing session details: " + e.getMessage());
            return null;
        }
    }


    public List<SessionDTO> listSessionsByConference(int conferenceID) {
        try {
            return sessionService.listSessionsByConference(conferenceID);
        } catch (Exception e) {
            System.err.println("Error listing sessions by conference: " + e.getMessage());
            return null;
        }
    }


    public List<FeedbackDTO> viewSessionFeedback(int sessionID) {
        try {
            return feedbackService.getSessionFeedbacks(sessionID);
        } catch (Exception e) {
            System.err.println("Error viewing session feedback: " + e.getMessage());
            return null;
        }
    }


    public double viewSessionAverageRating(int sessionID) {
        try {
            return feedbackService.getAverageRating(sessionID);
        } catch (Exception e) {
            System.err.println("Error viewing session average rating: " + e.getMessage());
            return 0.0;
        }
    }
}

