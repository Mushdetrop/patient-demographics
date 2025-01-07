
package com.abernathy.mediscreen.controller;

import com.abernathy.mediscreen.domain.Patient;
import com.abernathy.mediscreen.repository.PatientRepository;
import com.abernathy.mediscreen.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    void createPatient() throws Exception {
        // Prepare mock patient object
        Patient patient = Patient.builder()
                .family("TestNone")
                .given("Test")
                .dob("1966-12-31")
                .sex("F")
                .address("1 Brookside St")
                .phone("100-222-3333")
                .build();

        // Mock the service method
        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

        // Perform POST request and verify response
        mockMvc.perform(post("/patient/add")
                        .param("family", "TestNone")
                        .param("given", "Test")
                        .param("dob", "1966-12-31")
                        .param("sex", "F")
                        .param("address", "1 Brookside St")
                        .param("phone", "100-222-3333"))
                .andExpect(status().isOk());
    }
}
