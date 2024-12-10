package service;

import dto.ConferenceDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CertificateGeneratorTask {
    private final ConferenceService conferenceService;
    private final CertificateService certificateService;

    public CertificateGeneratorTask(ConferenceService conferenceService, CertificateService certificateService) {
        this.conferenceService = conferenceService;
        this.certificateService = certificateService;
    }

    public void start() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            List<ConferenceDTO> conferences = conferenceService.getAllConferences();
            for (ConferenceDTO conference : conferences) {
                if (conference.getEndDate().isBefore(LocalDateTime.now())) {
                    certificateService.generateCertificatesForConference(conference.getConferenceID());
                }
            }
        }, 0, 1, TimeUnit.MINUTES); // Runs every hour
    }
}

