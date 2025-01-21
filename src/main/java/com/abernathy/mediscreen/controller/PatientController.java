package com.abernathy.mediscreen.controller;

import com.abernathy.mediscreen.domain.Patient;
import com.abernathy.mediscreen.repository.PatientRepository;
import com.abernathy.mediscreen.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@Slf4j
public class PatientController {
    private final PatientService service;
    private final PatientRepository repository;

    @Autowired
    public PatientController(PatientService service, PatientRepository repository) {
        this.service = service;
        this.repository = repository;

    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return service.getAllPatients();
    }

    @PostMapping("add")
    public Patient createPatient(
            @RequestParam("family") String family,
            @RequestParam("given") String given,
            @RequestParam("dob") String dob,
            @RequestParam("sex") String sex,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone) {

        log.error("Creating Patient: family={}, given={}, dob={}, sex={}, address={}, phone={}",
                family, given, dob, sex, address, phone);


        Patient patient = Patient.builder()
                .family(family)
                .given(given)
                .dob(dob)
                .sex(sex)
                .address(address)
                .phone(phone)
                .build();

        return service.savePatient(patient);
    }

    //finish adding the patient
    //modify the FirstName & LastName as GivenName and Family Name.
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        Patient patient = service.updatePatient(id, updatedPatient);
        return (patient != null) ? ResponseEntity.ok(patient) : ResponseEntity.notFound().build();
    }
}
