package test;

import domain.Report;
import repository.ReportRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ReportRepoTest {
    public static void main(String[] args) {
        try {
            // Initialize repository
            String filePath = "data/reports.json"; // Adjust path as necessary
            ReportRepository reportRepository = ReportRepository.getInstance(filePath);

            // Test 1: Add reports
            System.out.println("=== Test 1: Add Reports ===");
            Report report1 = new Report(501, "Summary of GAF-AI 2025", "Conference Manager", 5);
            Report report2 = new Report(502, "Session Engagement Analysis", "Data Analyst", 4);
            reportRepository.save(report1);
            reportRepository.save(report2);

            // Test 2: Retrieve all reports
            System.out.println("\n=== Test 2: Retrieve All Reports ===");
            List<Report> allReports = reportRepository.findAll();
            for (Report report : allReports) {
                System.out.println(report);
            }

            // Test 3: Find report by ID
            System.out.println("\n=== Test 3: Find Report by ID ===");
            Report foundReport = reportRepository.findById(501);
            System.out.println(foundReport != null ? foundReport : "Report not found!");

            // Test 4: Find reports by author
            System.out.println("\n=== Test 4: Find Reports by Author ===");
            List<Report> reportsByAuthor = reportRepository.findByAuthor("Conference Manager");
            for (Report report : reportsByAuthor) {
                System.out.println(report);
            }

            // Test 5: Find reports by date range
            System.out.println("\n=== Test 5: Find Reports by Date Range ===");
            LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 0, 0);
            LocalDateTime endDate = LocalDateTime.of(2024, 12, 1, 0, 0);
            List<Report> reportsInRange = reportRepository.findByDateRange(startDate, endDate);
            for (Report report : reportsInRange) {
                System.out.println(report);
            }

            // Test 6: Delete report
            System.out.println("\n=== Test 6: Delete Report ===");
            reportRepository.delete(502);
            System.out.println("Reports after deletion:");
            for (Report report : reportRepository.findAll()) {
                System.out.println(report);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

