package domain;

import java.time.LocalDateTime;

public class Report {
    private int reportID;
    private String content;
    private LocalDateTime generatedDate;
    private String author;
    private int averageRating;

    public Report() {
        //default constructor
    }

    public Report(int reportID, String content, String author) {
        this.reportID = reportID;
        this.content = content;
        this.generatedDate = generatedDate;
        this.author = author;
    }

    public Report(int reportID, String content, String author, int averageRating) {
        this.reportID = reportID;
        this.content = content;
        this.generatedDate = LocalDateTime.now();
        this.author = author;
        this.averageRating = averageRating;
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
}
