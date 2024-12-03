package dto;

import domain.Schedule;


public class AttendeeDTO {
    private int userID;
    private String name;
    private String email;
    private Schedule schedule;

    public AttendeeDTO(int userID, String name, String email, Schedule schedule) {
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
