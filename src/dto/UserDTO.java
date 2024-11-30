package dto;

import enums.Role;

public class UserDTO {
    private int userID;
    private String name;
    private String email;
    private Role role;

    public UserDTO(int userID, String name, String email, Role role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
