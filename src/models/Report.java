package models;

import java.time.LocalDateTime;

public class Report {
    private int reportID;
    private String content;
    private LocalDateTime generatedDate;
    private String author;

    public Report(int reportID, String content, String author) {
        this.reportID = reportID;
        this.content = content;
        this.generatedDate = LocalDateTime.now();
        this.author = author;
    }

    public void generateReport() {
        // Logic for report generation
    }

    public void exportReport(String format) {
        System.out.println("Report exported in format: " + format);
    }
}
