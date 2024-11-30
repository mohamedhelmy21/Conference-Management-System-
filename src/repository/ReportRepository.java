package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Report;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportRepository {
    private final String filePath;

    public ReportRepository(String filePath) {
        this.filePath = filePath;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    public List<Report> findAll() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Report.class);
        return objectMapper.readValue(file, listType);
    }

    public Report findById(int reportId) throws IOException {
        return findAll().stream().filter(report -> report.getReportID() == reportId).findFirst().orElse(null);
    }

    public void save(Report report) throws IOException {
        List<Report> reports = findAll();
        reports.removeIf(existingReport -> existingReport.getReportID() == report.getReportID());
        reports.add(report);

        writeAll(reports);
    }

    public void delete(int reportId) throws IOException {
        List<Report> reports = findAll();
        reports.removeIf(report -> report.getReportID() == reportId);
        writeAll(reports);
    }

    public List<Report> findByAuthor(String author) throws IOException {
        return findAll().stream().filter(report -> report.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
    }

    public List<Report> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        return findAll().stream().filter(report ->
                        !report.getGeneratedDate().isBefore(startDate) &&
                                !report.getGeneratedDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    private void writeAll(List<Report> reports) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), reports);
    }
}
