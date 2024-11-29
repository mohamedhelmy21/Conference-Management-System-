package domain;

import java.util.*;
import java.time.LocalDateTime;

public class Certificate {
    private int certificateID;
    private int attendeeID;
    private LocalDateTime issueDate;
    private List<Session> sessionsAttended;
    private String certificateNumber;

    public Certificate(int certificateID, int attendeeID, List<Session> sessionsAttended) {
        this.certificateID = certificateID;
        this.attendeeID = attendeeID;
        this.issueDate = LocalDateTime.now();
        this.sessionsAttended = sessionsAttended;
        this.certificateNumber = generateCertificateNumber();
    }

    private String generateCertificateNumber() {
        return "CERT-" + attendeeID + "-" + System.currentTimeMillis();
    }

    private boolean validateCertificate(){
        return !sessionsAttended.isEmpty();
    }

    public void downloadCertificate() {
        System.out.println("Certificate downloaded: " + certificateNumber);
    }

    public int getCertificateID() {
        return certificateID;
    }

    public int getAttendeeID() {
        return attendeeID;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public List<Session> getSessionsAttended() {
        return sessionsAttended;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }
}
