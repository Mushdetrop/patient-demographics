
package com.abernathy.mediscreen.controller;

import com.abernathy.mediscreen.domain.Patient;
import com.abernathy.mediscreen.repository.PatientRepository;
import com.abernathy.mediscreen.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPatient_shouldReturnPatientInfo() throws Exception {
        // ✅ Mock Patient Data
        Patient patient = Patient.builder()
                .id(1L)
                .family("TestNone")
                .given("Test")
                .dob("1966-12-31")
                .sex("F")
                .address("1 Brookside St")
                .phone("100-222-3333")
                .build();

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
        //check for response and it contains patient information.
    }

    // PatientControllerTest.java (for Update)
    @Test
    void updatePatient_shouldReturnUpdatedInfo() throws Exception {
        Patient updatedPatient = Patient.builder()
                .id(1L)
                .family("UpdatedFamily")
                .given("UpdatedGiven")
                .dob("1970-01-01")
                .sex("M")
                .address("123 New St")
                .phone("999-888-7777")
                .build();

        when(patientService.updatePatient(eq(1L), any(Patient.class))).thenReturn(updatedPatient);

        mockMvc.perform(put("/patient/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("UpdatedFamily"))
                .andExpect(jsonPath("$.given").value("UpdatedGiven"))
                .andExpect(jsonPath("$.dob").value("1970-01-01"))
                .andExpect(jsonPath("$.sex").value("M"))
                .andExpect(jsonPath("$.address").value("123 New St"))
                .andExpect(jsonPath("$.phone").value("999-888-7777"));
    }

}
