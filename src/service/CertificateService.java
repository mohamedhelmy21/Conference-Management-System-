package service;

import domain.Certificate;
import dto.CertificateDTO;
import dto.SessionDTO;
import exception.RepositoryException;
import repository.CertificateRepository;
import utility.IDGenerator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


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

            // Get all sessions attended by the attendee via SessionService
            List<SessionDTO> attendedSessions = sessionService.getSessionsAttendedByAttendee(attendeeID);

            // Ensure the attendee has attended at least one session
            if (attendedSessions.isEmpty()) {
                throw new IllegalArgumentException("No attended sessions found for attendee: " + attendeeID);
            }

            List<String> sessionNames = attendedSessions.stream()
                    .map(SessionDTO::getName)
                    .collect(Collectors.toList());

            List<Integer> sessionIDs = attendedSessions.stream()
                    .map(SessionDTO::getSessionID)
                    .collect(Collectors.toList());

            int certificateID = IDGenerator.generateId("Certificate");
            String certificateText = generateCertificateText(attendeeID, sessionIDs);

            Certificate certificate = new Certificate(certificateID, attendeeID, sessionIDs, certificateText);
            certificateRepository.save(certificate);

            attendeeService.assignCertificateToAttendee(attendeeID, certificateID);


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
        certificateText.append("Attendee: ").append(attendeeService.getAttendeeName(attendeeID)).append("\n");
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
