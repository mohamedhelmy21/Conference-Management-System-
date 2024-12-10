package service;

import domain.Report;
import dto.ConferenceDTO;
import dto.FeedbackDTO;
import dto.ReportDTO;
import dto.SessionDTO;
import enums.ReportType;
import exception.RepositoryException;
import repository.ReportRepository;
import utility.IDGenerator;

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
    public ReportDTO generateSessionFeedbackReport(int sessionID, String author) {
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

            Report report = new Report(reportID, ReportType.FEEDBACK, content, author);
            report.setAverageRating((int) averageRating);
            reportRepository.save(report);

            return mapToDTO(report);
        } catch (IOException e) {
            throw new RepositoryException("Error generating session feedback report.", e);
        }
    }

    public ReportDTO generateConferenceAttendanceReport(int conferenceID, String author) {
        try {
            ConferenceDTO conference = conferenceService.getConferenceDetails(conferenceID);
            if (conference == null) {
                throw new IllegalArgumentException("Conference not found with ID: " + conferenceID);
            }

            List<Integer> attendeeIDs = conference.getAttendees();
            List<String> attendeeNames = attendeeIDs.stream()
                    .map(attendeeService::getAttendeeName)
                    .collect(Collectors.toList());

            int reportID = IDGenerator.generateId("Report");
            String content = "Attendance Report for " + conference.getName() + ":\n" +
                    String.join("\n", attendeeNames);

            Report report = new Report(reportID, ReportType.ATTENDANCE, content, author);
            reportRepository.save(report);

            return mapToDTO(report);
        } catch (IOException e) {
            throw new RepositoryException("Error generating conference attendance report.", e);
        }
    }

    public ReportDTO generateSessionAttendanceReport(int sessionID, String author) {
        try {
            SessionDTO session = sessionService.viewSessionDetails(sessionID);
            if (session == null) {
                throw new IllegalArgumentException("Session not found with ID: " + sessionID);
            }

            List<Integer> attendeeIDs = sessionService.getAttendedAttendees(sessionID);
            List<String> attendeeNames = attendeeIDs.stream()
                    .map(attendeeService::getAttendeeName)
                    .collect(Collectors.toList());

            int reportID = IDGenerator.generateId("Report");
            String content = "Session Attendance Report for " + session.getName() + ":\n" +
                    String.join("\n", attendeeNames);

            Report report = new Report(reportID, ReportType.ATTENDANCE, content, author);
            reportRepository.save(report);

            return mapToDTO(report);
        } catch (IOException e) {
            throw new RepositoryException("Error generating session attendance report.", e);
        }
    }


    // Retrieve Report by ID
    public ReportDTO getReportById(int reportID) {
        try {
            Report report = reportRepository.findById(reportID);
            if (report == null) {
                throw new IllegalArgumentException("Report not found with ID: " + reportID);
            }
            return mapToDTO(report);
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving report.", e);
        }
    }

    // List All Reports
    public List<ReportDTO> listAllReports() {
        try {
            List<Report> reports = reportRepository.findAll();
            return reports.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RepositoryException("Error retrieving all reports.", e);
        }
    }

    // Map Report to ReportDTO
    private ReportDTO mapToDTO(Report report) {
        return new ReportDTO(
                report.getReportID(),
                report.getReportType(),
                report.getContent(),
                report.getAuthor(),
                report.getGeneratedDate(),
                report.getReportType() == ReportType.ATTENDANCE ? report.getAverageRating() : 0);
    }


}
