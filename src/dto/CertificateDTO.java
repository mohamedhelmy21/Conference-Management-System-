package dto;

import java.time.LocalDateTime;
import java.util.List;

public class CertificateDTO {
    private int certificateID;
    private String attendeeName;
    private LocalDateTime issueDate;
    private List<String> sessionsAttended;

    public CertificateDTO(int certificateID, String attendeeName, LocalDateTime issueDate, List<String> sessionsAttended) {
        this.certificateID = certificateID;
        this.attendeeName = attendeeName;
        this.issueDate = issueDate;
        this.sessionsAttended = sessionsAttended;
    }

    public int getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(int certificateID) {
        this.certificateID = certificateID;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public List<String> getSessionsAttended() {
        return sessionsAttended;
    }

    public void setSessionsAttended(List<String> sessionsAttended) {
        this.sessionsAttended = sessionsAttended;
    }
}
