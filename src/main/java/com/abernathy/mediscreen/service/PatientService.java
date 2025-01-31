package com.abernathy.mediscreen.service;

import com.abernathy.mediscreen.domain.Patient;
import com.abernathy.mediscreen.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public Patient getPatientById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        return repository.findById(id).map(existingPatient -> {
            existingPatient.setGiven(updatedPatient.getGiven());
            existingPatient.setFamily(updatedPatient.getFamily());
            existingPatient.setDob(updatedPatient.getDob());
            existingPatient.setSex(updatedPatient.getSex());
            existingPatient.setAddress(updatedPatient.getAddress());
            existingPatient.setPhone(updatedPatient.getPhone());
            return repository.save(existingPatient);
        }).orElse(null);
    }
}
