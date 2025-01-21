package com.abernathy.mediscreen.service;

import com.abernathy.mediscreen.domain.Patient;
import com.abernathy.mediscreen.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private PatientRepository patientRepository;


    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    public Patient savePatient(Patient patient) {
        return repository.save(patient);
    }

    public void deletePatient(Long id) {
        repository.deleteById(id);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Optional<Patient> existingPatient = repository.findById(id);
        if (existingPatient.isPresent()) {
            Patient patient = existingPatient.get();
            patient.setFamily(updatedPatient.getFamily());
            patient.setGiven(updatedPatient.getGiven());
            patient.setDob(updatedPatient.getDob());
            patient.setSex(updatedPatient.getSex());
            patient.setAddress(updatedPatient.getAddress());
            patient.setPhone(updatedPatient.getPhone());
            return repository.save(patient);
        }
        return null;
    }
}
