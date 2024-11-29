package domain;

import enums.Rating;

import java.time.LocalDateTime;

public class Feedback {
    private int feedbackID;
    private int attendeeID;
    private int sessionID;
    private String comments;
    private Rating rating;
    private LocalDateTime submissionTime;
    private boolean isAnonymous;

    public Feedback(int feedbackID, int attendeeID, int sessionID, String comments, Rating rating, boolean isAnonymous) {
        this.feedbackID = feedbackID;
        this.attendeeID = attendeeID;
        this.sessionID = sessionID;
        this.comments = comments;
        this.rating = rating;
        this.submissionTime = LocalDateTime.now();
        this.isAnonymous = isAnonymous;
    }

    public void updateFeedback(String comments, Rating rating){
        this.comments = comments;
        this.rating = rating;
        this.submissionTime = LocalDateTime.now();
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

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackID=" + feedbackID +
                //consider the anonymity and the attendee ID display
                ", attendeeID=" + attendeeID +
                ", sessionID=" + sessionID +
                ", comments='" + comments + '\'' +
                ", rating=" + rating +
                ", submissionTime=" + submissionTime +
                ", isAnonymous=" + isAnonymous +
                '}';
    }
}
