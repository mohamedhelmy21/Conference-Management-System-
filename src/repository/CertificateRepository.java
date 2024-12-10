package repository;

import domain.Certificate;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CertificateRepository extends BaseRepository<Certificate> {

    private static CertificateRepository instance;

    private CertificateRepository(String filePath) {
        super(filePath, Certificate.class);
    }

    public static CertificateRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new CertificateRepository(filePath);
        }
        return instance;
    }

    @Override
    protected Integer getId(Certificate certificate){
        return certificate.getCertificateID();
    }

    public Certificate findByAttendeeID(int attendeeID) throws IOException {
        List<Certificate> certificates = findAll();
        for (Certificate certificate : certificates) {
            if (certificate.getAttendeeID() == attendeeID) {
                return certificate;
            }
        }
        return null; // No existing certificate found
    }

}

