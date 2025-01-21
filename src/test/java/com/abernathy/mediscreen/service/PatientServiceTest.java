package com.abernathy.mediscreen.service;


import com.abernathy.mediscreen.domain.Patient;
import com.abernathy.mediscreen.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
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

    @Test
    void updatePatient_existingPatient_shouldUpdateAndReturnPatient() {
        Patient existingPatient = Patient.builder().id(1L).given("John").family("Doe").dob("1990-01-01").sex("M").address("123 Main St").phone("123-456-7890").build();
        Patient updatedPatient = Patient.builder().id(1L).given("Johnny").family("Doe").dob("1990-01-01").sex("M").address("789 Pine St").phone("555-555-5555").build();

        when(repository.findById(1L)).thenReturn(Optional.of(existingPatient));
        when(repository.save(any(Patient.class))).thenReturn(updatedPatient);

        Patient result = service.updatePatient(1L, updatedPatient);

        assertNotNull(result);
        assertEquals("Johnny", result.getGiven());
        assertEquals("789 Pine St", result.getAddress());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Patient.class));
    }

    @Test
    void updatePatient_nonExistingPatient_shouldReturnNull() {
        Patient updatedPatient = Patient.builder().id(2L).given("Johnny").family("Doe").dob("1990-01-01").sex("M").address("789 Pine St").phone("555-555-5555").build();

        when(repository.findById(2L)).thenReturn(Optional.empty());

        Patient result = service.updatePatient(2L, updatedPatient);

        assertNull(result);
        verify(repository, times(1)).findById(2L);
        verify(repository, never()).save(any(Patient.class));
    }

    @Test
    void deletePatient_existingPatient_shouldDeletePatient() {
        Long patientId = 1L;
        doNothing().when(repository).deleteById(patientId);

        service.deletePatient(patientId);

        verify(repository, times(1)).deleteById(patientId);
    }

    @Test
    void deletePatient_nonExistingPatient_shouldDoNothing() {
        Long patientId = 999L;
        doNothing().when(repository).deleteById(patientId);

        service.deletePatient(patientId);

        verify(repository, times(1)).deleteById(patientId);
    }
}
