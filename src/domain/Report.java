package domain;

import java.time.LocalDateTime;
import enums.ReportType;

public class Report {
    private int reportID;
    private ReportType reportType;
    private String content;
    private LocalDateTime generatedDate;
    private String author;
    private int averageRating;

    public Report() {
        //default constructor
    }


    public Report(int reportID, ReportType reportType, String content, String author) {
        this.reportID = reportID;
        this.reportType = reportType;
        this.content = content;
        this.generatedDate = LocalDateTime.now();
        this.author = author;
    }

    public int getReportID() {
        return reportID;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public String getAuthor() {
        return author;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public ReportType getReportType() {
        return reportType;
    }
}
