package repository;

import domain.Report;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReportRepository extends BaseRepository<Report> {

    @Override
    protected Integer getId(Report report){
        return report.getReportID();
    }

    public ReportRepository(String filePath) {
        super(filePath, Report.class);
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

}
