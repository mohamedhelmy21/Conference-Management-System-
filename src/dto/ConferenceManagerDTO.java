package dto;

import java.util.List;

public class ConferenceManagerDTO {
    private int managerID;
    private String name;
    private List<String> managedConferences;

    public ConferenceManagerDTO(int managerID, String name, List<String> managedConferences) {
        this.managerID = managerID;
        this.name = name;
        this.managedConferences = managedConferences;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getManagedConferences() {
        return managedConferences;
    }

    public void setManagedConferences(List<String> managedConferences) {
        this.managedConferences = managedConferences;
    }
}
