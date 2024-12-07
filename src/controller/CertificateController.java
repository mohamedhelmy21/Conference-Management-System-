package controller;

import dto.CertificateDTO;
import service.CertificateService;
import service.ConferenceService;
import service.AttendeeService;

import java.util.List;

public class CertificateController {

    private final CertificateService certificateService;
    private final ConferenceService conferenceService;
    private final AttendeeService attendeeService;

    public CertificateController(CertificateService certificateService, ConferenceService conferenceService, AttendeeService attendeeService) {
        this.certificateService = certificateService;
        this.conferenceService = conferenceService;
        this.attendeeService = attendeeService;
    }

    public CertificateDTO generateCertificate(int attendeeID) {
        try {
            CertificateDTO certificate = certificateService.generateCertificate(attendeeID);
            System.out.println("Certificate generated successfully for attendee ID: " + attendeeID);
            return certificate;
        } catch (Exception e) {
            System.err.println("Error generating certificate: " + e.getMessage());
            return null;
        }
    }

    public CertificateDTO getCertificateDetails(int certificateID) {
        try {
            return certificateService.getCertificate(certificateID);
        } catch (Exception e) {
            System.err.println("Error retrieving certificate details: " + e.getMessage());
            return null;
        }
    }

    public boolean generateCertificatesForConference(int conferenceID) {
        try {
            // Retrieve the list of attendees for the conference
            List<Integer> attendeeIDs = conferenceService.getConferenceAttendees(conferenceID);

            // Generate certificates for each attendee
            for (int attendeeID : attendeeIDs) {
                CertificateDTO certificate = certificateService.generateCertificate(attendeeID);
                if (certificate != null) {
                    System.out.println("Certificate generated for attendee ID: " + attendeeID);
                } else {
                    System.err.println("Failed to generate certificate for attendee ID: " + attendeeID);
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error generating certificates for conference: " + e.getMessage());
            return false;
        }
    }
}

