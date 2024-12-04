package service;

import domain.Certificate;
import dto.CertificateDTO;
import exception.RepositoryException;
import repository.CertificateRepository;
import utility.IDGenerator;

import java.io.IOException;
import java.util.List;


public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final SessionService sessionService;
    private AttendeeService attendeeService;

    public CertificateService(CertificateRepository certificateRepository, SessionService sessionService) {
        this.certificateRepository = certificateRepository;
        this.sessionService = sessionService;
    }

    public void setAttendeeService(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    public CertificateDTO generateCertificate(int attendeeID){
        try {
            if (!attendeeService.doesAttendeeExist(attendeeID)) {
                throw new IllegalArgumentException("Attendee does not exist");
            }

            List<Integer> scheduledSessions = attendeeService.getAttendeeScheduleSessions(attendeeID);

            List<Integer> attendedSession = sessionService.getSessionsAttendedByAttendee(attendeeID);

            if (!attendedSession.equals(scheduledSessions)) {
                throw new IllegalArgumentException("Attendee has not attended all sessions.");
            }

            int certificateID = IDGenerator.generateId("Certificate");
            String certificateText = generateCertificateText(attendeeID, scheduledSessions);

            Certificate certificate = new Certificate(certificateID, attendeeID, attendedSession, certificateText);
            certificateRepository.save(certificate);

            return mapToDTO(certificate);
        }catch (IOException e){
            throw new RepositoryException("Error generating certificate", e);
        }
    }

    public CertificateDTO getCertificate(int certificateID) {
        try {
            Certificate certificate = certificateRepository.findById(certificateID);

            if (certificate == null) {
                throw new IllegalArgumentException("Certificate not found: " + certificateID);
            }

            return mapToDTO(certificate);

        } catch (IOException e) {
            throw new RepositoryException("Error retrieving certificate.", e);
        }
    }

    private String generateCertificateText(int attendeeID, List<Integer> scheduledSessions) {
        StringBuilder certificateText = new StringBuilder();
        certificateText.append("Certificate of Attendance\n");
        certificateText.append("Attendee ID: ").append(attendeeID).append("\n");
        certificateText.append("Congratulations! You have successfully attended the following sessions:\n");

        for (int sessionID : scheduledSessions) {
            var sessionDTO = sessionService.viewSessionDetails(sessionID); // Fetch session details
            certificateText.append("- ").append(sessionDTO.getName()).append(" on ")
                    .append(sessionDTO.getDateTime()).append(" in ").append(sessionDTO.getRoom()).append("\n");
        }

        certificateText.append("Thank you for participating in the conference!");
        return certificateText.toString();
    }

    private CertificateDTO mapToDTO(Certificate certificate) {
        return new CertificateDTO(
                certificate.getCertificateID(),
                certificate.getAttendeeID(),
                certificate.getIssueDate(),
                certificate.getSessionsAttended(),
                certificate.getCertificateText()
        );
    }

}
