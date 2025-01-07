package com.abernathy.mediscreen.service;


import com.abernathy.mediscreen.domain.Patient;
import com.abernathy.mediscreen.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Mock
    private PatientRepository repository;

    private PatientService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PatientService(repository);
    }

    @Test
    void getAllPatients() {
        List<Patient> patients = Arrays.asList(
                Patient.builder().id(1L).given("Test").family("None").dob("1966-12-31").sex("F").address("1 Brookside St").phone("100-222-3333").build()
        );
        when(repository.findAll()).thenReturn(patients);

        List<Patient> result = service.getAllPatients();

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getGiven());
        verify(repository, times(1)).findAll();
    }

    @Test
    void savePatient() {
        Patient patient = Patient.builder().id(1L).given("Test").family("None").dob("1966-12-31").sex("F").address("1 Brookside St").phone("100-222-3333").build();
        when(repository.save(patient)).thenReturn(patient);

        Patient result = service.savePatient(patient);

        assertEquals("Test", result.getGiven());
        verify(repository, times(1)).save(patient);
    }
}
