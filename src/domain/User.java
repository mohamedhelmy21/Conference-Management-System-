package domain;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import enums.Role;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Use the "type" field for polymorphism
        include = JsonTypeInfo.As.EXISTING_PROPERTY, // Include the "type" field in JSON
        property = "role", // Use the "role" property as the discriminator
        visible = true // Ensure the "role" field is available during deserialization
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Attendee.class, name = "ATTENDEE"),
        @JsonSubTypes.Type(value = Speaker.class, name = "SPEAKER"),
        @JsonSubTypes.Type(value = ConferenceManager.class, name = "MANAGER")
})

public class User {
    private int userID;
    private String name;
    private String email;
    private String password;
    private Role role;

    public User() {
        //default constructor
    }

    public User(int userID, String name, String email, String password, Role role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public boolean updateProfile(String name, String email, String password) {
        this.name=name;
        this.email=email;
        this.password=password;
        return true;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
