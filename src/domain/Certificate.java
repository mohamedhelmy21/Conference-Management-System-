package domain;

import java.util.*;
import java.time.LocalDateTime;

public class Certificate {
    private int certificateID;
    private int attendeeID;
    private LocalDateTime issueDate;
    private List<Session> sessionsAttended;
    private String certificateNumber;

    public Certificate() {
        //default constructor
    }

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

    @Override
    public String toString() {
        StringBuilder certificateText = new StringBuilder();
        certificateText.append("Certificate of Attendance\n")
                .append("---------------------------\n")
                .append("Certificate ID: ").append(certificateID).append("\n")
                .append("Certificate Number: ").append(certificateNumber).append("\n")
                .append("Attendee ID: ").append(attendeeID).append("\n")
                .append("Issued Date: ").append(issueDate).append("\n")
                .append("Sessions Attended:\n");

        for (Session session : sessionsAttended) {
            certificateText.append("- ").append(session.getName())
                    .append(" (").append(session.getDateTime()).append(")\n");
        }

        certificateText.append("\nThank you for participating in our conference!");

        return certificateText.toString();
    }
}
