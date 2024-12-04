package dto;

import java.time.LocalDateTime;
import java.util.List;

public class CertificateDTO {
    private int certificateID;
    private int attendeeID;
    private LocalDateTime issueDate;
    private List<Integer> sessionsAttended;
    private String certificateText;

    public CertificateDTO(int certificateID, int attendeeID, LocalDateTime issueDate, List<Integer> sessionsAttended, String certificateText) {
        this.certificateID = certificateID;
        this.attendeeID = attendeeID;
        this.issueDate = issueDate;
        this.sessionsAttended = sessionsAttended;
        this.certificateText = certificateText;
    }

    public int getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(int certificateID) {
        this.certificateID = certificateID;
    }

    public int getAttendeeID() {
        return attendeeID;
    }

    public void setAttendeeID(int attendeeID) {
        this.attendeeID = attendeeID;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public List<Integer> getSessionsAttended() {
        return sessionsAttended;
    }

    public void setSessionsAttended(List<Integer> sessionsAttended) {
        this.sessionsAttended = sessionsAttended;
    }

    public String getCertificateText() {
        return certificateText;
    }

    public void setCertificateText(String certificateText) {
        this.certificateText = certificateText;
    }
}
