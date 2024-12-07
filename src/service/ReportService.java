package service;

import domain.Report;
import dto.ConferenceDTO;
import dto.FeedbackDTO;
import dto.SessionDTO;
import repository.ReportRepository;
import utility.IDGenerator;
import exception.RepositoryException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {
    private final ReportRepository reportRepository;
    private final FeedbackService feedbackService;
    private final ConferenceService conferenceService;
    private final SessionService sessionService;
    private final AttendeeService attendeeService;

    public ReportService(ReportRepository reportRepository, FeedbackService feedbackService, ConferenceService conferenceService, SessionService sessionService, AttendeeService attendeeService) {
        this.reportRepository = reportRepository;
        this.feedbackService = feedbackService;
        this.conferenceService = conferenceService;
        this.sessionService = sessionService;
        this.attendeeService = attendeeService;
    }

    // Generate Feedback Report for a Session
    public Report generateSessionFeedbackReport(int sessionID, String author) {
        try {
            SessionDTO session = sessionService.viewSessionDetails(sessionID);
            List<FeedbackDTO> feedbackList = feedbackService.getSessionFeedbacks(sessionID);

            if (session == null || feedbackList.isEmpty()) {
                throw new IllegalArgumentException("No feedback available for session ID: " + sessionID);
            }

            // Aggregate feedback
            String feedbackContent = feedbackList.stream()
                    .map(feedback -> "Comment: " + feedback.getComments() + " | Rating: " + feedback.getRating())
                    .collect(Collectors.joining("\n"));

            double averageRating = feedbackList.stream()
                    .mapToDouble(feedback -> feedback.getRating().getValue())
                    .average()
                    .orElse(0);

            // Create report
            int reportID = IDGenerator.generateId("Report");
            String content = "Session Feedback Report for " + session.getName() + ":\n" + feedbackContent;
            Report report = new Report(reportID, content, author, (int) averageRating);

            // Save report
            reportRepository.save(report);
            return report;
        } catch (IOException e) {
            throw new RepositoryException("Error generating session feedback report.", e);
        }
    }

    // Generate Attendance Report for a Conference
    public Report generateConferenceAttendanceReport(int conferenceID, String author) {
        try {
            ConferenceDTO conference = conferenceService.getConferenceDetails(conferenceID);
            if (conference == null) {
                throw new IllegalArgumentException("Conference not found with ID: " + conferenceID);
            }

            List<Integer> attendeeIDs = conference.getAttendees();
            List<String> attendeeNames = attendeeIDs.stream()
                    .map(attendeeService::getAttendeeName)
                    .collect(Collectors.toList());

            // Create report
            int reportID = IDGenerator.generateId("Report");
            String content = "Attendance Report for " + conference.getName() + ":\n" +
                    String.join("\n", attendeeNames);

            Report report = new Report(reportID, content, author);

            // Save report
            reportRepository.save(report);
            return report;
        } catch (IOException e) {
            throw new RepositoryException("Error generating conference attendance report.", e);
        }
    }

    // Retrieve Report by ID
    public Report getReportById(int reportID) {
        try {
            Report report = reportRepository.findById(reportID);
            if (report == null) {
                throw new IllegalArgumentException("Report not found with ID: " + reportID);
            }
            return report;
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving report.", e);
        }
    }

    // List All Reports
    public List<Report> listAllReports() {
        try {
            return reportRepository.findAll();
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving all reports.", e);
        }
    }
}

