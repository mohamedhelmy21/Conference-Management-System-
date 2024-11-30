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

public class CertificateRepository extends BaseRepository<Certificate> {

    public CertificateRepository(String filePath) {
        super(filePath, Certificate.class);
    }

    @Override
    protected Integer getId(Certificate certificate){
        return certificate.getCertificateID();
    }

    public List<Certificate> findByAttendeeID(int attendeeID) throws IOException {
        return findAll().stream().filter(certificate -> certificate.getAttendeeID() == attendeeID).collect(Collectors.toList());
    }

}

