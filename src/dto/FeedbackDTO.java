package dto;

import enums.Rating;

public class FeedbackDTO {
    private int feedbackID;
    private String comments;
    private Rating rating;
    private String sessionName;
    private boolean isAnonymous;

    public FeedbackDTO(int feedbackID, String comments, Rating rating, String sessionName, boolean isAnonymous) {
        this.feedbackID = feedbackID;
        this.comments = comments;
        this.rating = rating;
        this.sessionName = sessionName;
        this.isAnonymous = isAnonymous;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
}
