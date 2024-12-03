package dto;

import enums.Rating;

public class FeedbackDTO {
    private int feedbackID;
    private int attendeeID;
    private int sessionID;
    private String comments;
    private Rating rating;
    private boolean isAnonymous;

    public FeedbackDTO(int feedbackID, int attendeeID, int sessionID, String comments, Rating rating, boolean isAnonymous) {
        this.feedbackID = feedbackID;
        this.attendeeID = attendeeID;
        this.sessionID = sessionID;
        this.comments = comments;
        this.rating = rating;
        this.isAnonymous = isAnonymous;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getAttendeeID() {
        return attendeeID;
    }

    public void setAttendeeID(int attendeeID) {
        this.attendeeID = attendeeID;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
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

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "feedbackID=" + feedbackID +
                ", sessionID=" + sessionID +
                ", comments='" + comments + '\'' +
                ", rating=" + rating +
                '}';
    }
}
