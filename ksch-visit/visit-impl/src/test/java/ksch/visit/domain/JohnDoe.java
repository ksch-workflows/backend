package ksch.visit.domain;

import ksch.patientmanagement.Gender;
import ksch.patientmanagement.Patient;

import java.util.UUID;

public class JohnDoe implements Patient {

    private static final UUID ID = UUID.randomUUID();

    @Override
    public UUID getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "John Doe";
    }

    @Override
    public Integer getAge() {
        return 25;
    }

    @Override
    public Gender getGender() {
        return Gender.MALE;
    }

    @Override
    public String getPhoneNumber() {
        return "0123456789";
    }

    @Override
    public String getResidentialAddress() {
        return "Guesthouse";
    }

    @Override
    public String getPatientCategory() {
        return "GENERAL";
    }
}
