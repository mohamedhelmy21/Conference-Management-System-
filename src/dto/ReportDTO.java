package dto;

import java.time.LocalDateTime;

public class ReportDTO {
    private int reportID;
    private String content;
    private String author;
    private LocalDateTime generatedDate;
    private int averageRating;

    public ReportDTO(int reportID, String content, String author, LocalDateTime generatedDate, int averageRating) {
        this.reportID = reportID;
        this.content = content;
        this.author = author;
        this.generatedDate = generatedDate;
        this.averageRating = averageRating;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }
}
