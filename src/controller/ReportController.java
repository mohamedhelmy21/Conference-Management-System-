package controller;

import domain.Report;
import dto.ConferenceDTO;
import dto.SessionDTO;
import service.ReportService;
import java.util.List;

public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Generate a feedback report for a session
    public Report generateSessionFeedbackReport(int sessionID, String author) {
        try {
            Report report = reportService.generateSessionFeedbackReport(sessionID, author);
            System.out.println("Feedback report generated successfully for session ID: " + sessionID);
            return report;
        } catch (Exception e) {
            System.err.println("Error generating session feedback report: " + e.getMessage());
            return null;
        }
    }

    // Generate an attendance report for a conference
    public Report generateConferenceAttendanceReport(int conferenceID, String author) {
        try {
            Report report = reportService.generateConferenceAttendanceReport(conferenceID, author);
            System.out.println("Attendance report generated successfully for conference ID: " + conferenceID);
            return report;
        } catch (Exception e) {
            System.err.println("Error generating conference attendance report: " + e.getMessage());
            return null;
        }
    }

    // Retrieve a report by its ID
    public Report viewReportDetails(int reportID) {
        try {
            Report report = reportService.getReportById(reportID);
            System.out.println("Report retrieved successfully: " + report.getContent());
            return report;
        } catch (Exception e) {
            System.err.println("Error retrieving report: " + e.getMessage());
            return null;
        }
    }

    // List all reports
    public List<Report> listAllReports() {
        try {
            List<Report> reports = reportService.listAllReports();
            System.out.println("Retrieved all reports successfully.");
            return reports;
        } catch (Exception e) {
            System.err.println("Error retrieving all reports: " + e.getMessage());
            return null;
        }
    }

}
