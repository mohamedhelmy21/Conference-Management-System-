package dto;

import java.util.List;

public class AttendeeDTO {
    private int userID;
    private String name;
    private String email;
    private List<Integer> schedule;

    public AttendeeDTO(int userID, String name, String email, List<Integer> schedule) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.schedule = schedule;
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

    public List<Integer> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Integer> schedule) {
        this.schedule = schedule;
    }
}
