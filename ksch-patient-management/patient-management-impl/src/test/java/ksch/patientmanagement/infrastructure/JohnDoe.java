package ksch.patientmanagement.infrastructure;

import ksch.patientmanagement.Gender;
import ksch.patientmanagement.Patient;

import java.util.UUID;

import static ksch.patientmanagement.Gender.MALE;

public class JohnDoe implements Patient {

    private final UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getPatientNumber() {
        return "2021-1005";
    }

    @Override
    public String getName() {
        return "John Doe";
    }

    @Override
    public Integer getAge() {
        return 19;
    }

    @Override
    public Gender getGender() {
        return MALE;
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public String getResidentialAddress() {
        return "Guesthouse";
    }

    @Override
    public String getPatientCategory() {
        return null;
    }
}
