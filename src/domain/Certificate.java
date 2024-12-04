package domain;

import java.util.*;
import java.time.LocalDateTime;

public class Certificate {
    private int certificateID;
    private int attendeeID;
    private LocalDateTime issueDate;
    private List<Integer> sessionsAttended;
    private String certificateText;

    public Certificate() {
        //default constructor
    }

    public Certificate(int certificateID, int attendeeID, List<Integer> sessionsAttended, String certificateText) {
        this.certificateID = certificateID;
        this.attendeeID = attendeeID;
        this.issueDate = LocalDateTime.now();
        this.sessionsAttended = sessionsAttended;
        this.certificateText = certificateText;
    }


    private boolean validateCertificate(){
        return !sessionsAttended.isEmpty();
    }

    public void downloadCertificate() {
        System.out.println("Certificate downloaded: " + certificateText);
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

    public List<Integer> getSessionsAttended() {
        return sessionsAttended;
    }

    public String getCertificateText() {
        return certificateText;
    }

}
