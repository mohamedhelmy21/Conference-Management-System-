package models;

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

    //generate certificate method is lacking

    public void downloadCertificate() {
        System.out.println("Certificate downloaded: " + certificateNumber);
    }


}
