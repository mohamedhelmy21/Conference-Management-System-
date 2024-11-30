package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Certificate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CertificateRepository {
    private final String filePath;

    public CertificateRepository(String filePath) {
        this.filePath = filePath;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    public List<Certificate> findAll() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Certificate.class);
        return objectMapper.readValue(file, listType);
    }

    public int generateCertificateID() throws IOException {
        List<Certificate> certificates = findAll();
        if (certificates.isEmpty()) {
            return 1;
        }
        return certificates.stream()
                .mapToInt(Certificate::getCertificateID)
                .max()
                .orElse(0) + 1;
    }

    public Certificate findById(int certificateId) throws IOException {
        return findAll().stream().filter(certificate -> certificate.getCertificateID() == certificateId).findFirst().orElse(null);
    }

    public void save(Certificate certificate) throws IOException {
        List<Certificate> certificates = findAll();
        certificates.removeIf(existingCertificate -> existingCertificate.getCertificateID() == certificate.getCertificateID());
        certificates.add(certificate);

        writeAll(certificates);
    }

    public void delete(int certificateId) throws IOException {
        List<Certificate> certificates = findAll();
        certificates.removeIf(certificate -> certificate.getCertificateID() == certificateId);
        writeAll(certificates);
    }

    public List<Certificate> findByAttendeeID(int attendeeID) throws IOException {
        return findAll().stream().filter(certificate -> certificate.getAttendeeID() == attendeeID).collect(Collectors.toList());
    }

    private void writeAll(List<Certificate> certificates) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), certificates);
    }
}

