package com.security.IncidentReports.service;

import com.security.IncidentReports.Model.Incident;
import com.security.IncidentReports.exception.ResourceNotFoundException;
import com.security.IncidentReports.repository.IncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private IncidentService incidentService;

    private Incident incident;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        incident = new Incident();
        incident.setId(1L);
        incident.setTitle("Incident Report");
        incident.setDescription("This is a test incident report.");
        incident.setSeverity("High");
        incident.setIncidentDate(LocalDate.now().minusDays(5));
        incident.setStatus("Open");
        incident.setNotes("Initial note");
    }

    @Test
    public void testCreateIncident_Success() {

        when(incidentRepository.findByTitle(incident.getTitle())).thenReturn(Optional.empty());
        when(incidentRepository.save(any(Incident.class))).thenReturn(incident);


        Incident createdIncident = incidentService.createIncident(incident);


        assertNotNull(createdIncident);
        assertEquals("Incident Report", createdIncident.getTitle());
    }

    @Test
    public void testCreateIncident_TitleNotUnique_ThrowsException() {

        when(incidentRepository.findByTitle(incident.getTitle())).thenReturn(Optional.of(incident));


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            incidentService.createIncident(incident);
        });
        assertEquals("Incident title must be unique.", exception.getMessage());
    }

    @Test
    public void testUpdateIncident_Success() {
        // Arrange
        Incident updatedIncident = new Incident();
        updatedIncident.setTitle("Updated Incident Report");
        updatedIncident.setDescription("Updated description");
        updatedIncident.setSeverity("Medium");
        updatedIncident.setIncidentDate(LocalDate.now().minusDays(3));
        updatedIncident.setStatus("Closed");
        updatedIncident.setNotes("Additional note");


        when(incidentRepository.findById(incident.getId())).thenReturn(Optional.of(incident));
        when(incidentRepository.save(any(Incident.class))).thenReturn(updatedIncident);


        Incident result = incidentService.updateIncident(incident.getId(), updatedIncident);


        assertNotNull(result);
        assertEquals("Medium", result.getSeverity());
        assertEquals("Closed", result.getStatus());
        assertEquals("Updated Incident Report", result.getTitle()); // Check the updated title
    }

    @Test
    public void testUpdateIncident_InvalidSeverity_ThrowsException() {

        Incident updatedIncident = new Incident();
        updatedIncident.setTitle("Valid Title");
        updatedIncident.setSeverity("InvalidSeverity");

        when(incidentRepository.findById(incident.getId())).thenReturn(Optional.of(incident));


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            incidentService.updateIncident(incident.getId(), updatedIncident);
        });
        assertEquals("Invalid severity level. It must be one of: Low, Medium, High.", exception.getMessage());
    }

    @Test
    public void testGetIncidentById_NotFound_ThrowsException() {
        // Arrange
        when(incidentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            incidentService.getIncidentById(1L);
        });
    }
}
