package test;

import domain.Certificate;
import domain.Session;
import repository.CertificateRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CertificateRepoTest {
    public static void main(String[] args) {
        try {
            // Initialize repository
            String filePath = "data/certificates.json"; // Adjust path as necessary
            CertificateRepository certificateRepository = new CertificateRepository(filePath);

            // Test 1: Add certificates
            System.out.println("=== Test 1: Add Certificates ===");
            List<Session> sessionsAttended = new ArrayList<>();
            sessionsAttended.add(new Session(201, "Introduction to LLMs", LocalDateTime.of(2025, 5, 10, 10, 0), "Room A", 100, 1, "Learn the basics of LLMs"));

            Certificate certificate1 = new Certificate(401, 1, sessionsAttended);
            certificateRepository.save(certificate1);

            // Test 2: Retrieve all certificates
            System.out.println("\n=== Test 2: Retrieve All Certificates ===");
            List<Certificate> allCertificates = certificateRepository.findAll();
            for (Certificate certificate : allCertificates) {
                System.out.println(certificate);
            }

            // Test 3: Find certificate by ID
            System.out.println("\n=== Test 3: Find Certificate by ID ===");
            Certificate foundCertificate = certificateRepository.findById(401);
            System.out.println(foundCertificate != null ? foundCertificate : "Certificate not found!");

            // Test 4: Find certificates by attendee ID
            System.out.println("\n=== Test 4: Find Certificates by Attendee ID ===");
            List<Certificate> attendeeCertificates = certificateRepository.findByAttendeeID(1);
            for (Certificate certificate : attendeeCertificates) {
                System.out.println(certificate);
            }

            // Test 5: Delete certificate
            System.out.println("\n=== Test 5: Delete Certificate ===");
            certificateRepository.delete(401);
            System.out.println("Certificates after deletion:");
            for (Certificate certificate : certificateRepository.findAll()) {
                System.out.println(certificate);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

