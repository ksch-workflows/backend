package ksch.patientmanagement.infrastructure;

import ksch.patientmanagement.Gender;
import ksch.patientmanagement.Patient;

import java.util.UUID;

import static ksch.patientmanagement.Gender.FEMALE;

public class JaneDoe implements Patient {

    private final UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getPatientNumber() {
        return "2021-1006";
    }

    @Override
    public String getName() {
        return "Jane Doe";
    }

    @Override
    public Integer getAge() {
        return 23;
    }

    @Override
    public Gender getGender() {
        return FEMALE;
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
