package dto;

import domain.Schedule;
import java.util.List;

public class AttendeeDTO {
    private int userID;
    private String name;
    private String email;
    private Schedule schedule;
    private List<Integer> feedbacks;
    private Integer certificateID;

    public AttendeeDTO(int userID, String name, String email, Schedule schedule, List<Integer> feedbacks, Integer certificateID) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.schedule = schedule;
        this.feedbacks = feedbacks;
        this.certificateID = certificateID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Integer> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Integer> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Integer getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(Integer certificateID) {
        this.certificateID = certificateID;
    }
}
