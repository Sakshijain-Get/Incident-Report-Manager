package com.security.IncidentReports.service;

import com.security.IncidentReports.Model.Incident;
import com.security.IncidentReports.exception.ResourceNotFoundException;
import com.security.IncidentReports.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public List<Incident> getAllIncidents(Optional<String> severity, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        List<Incident> incidents = incidentRepository.findAll();

        severity.ifPresent(this::validateSeverity);

        if (severity.isPresent()) {
            incidents = incidents.stream()
                    .filter(incident -> incident.getSeverity().equalsIgnoreCase(severity.get()))
                    .toList();
        }

        if (startDate.isPresent() && endDate.isPresent()) {
            incidents = incidents.stream()
                    .filter(incident -> !incident.getIncidentDate().isBefore(startDate.get()) &&
                            !incident.getIncidentDate().isAfter(endDate.get()))
                    .toList();
        }

        return incidents;
    }

    public Incident createIncident(Incident incident) {
        validateIncident(incident);
        if (incidentRepository.findByTitle(incident.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Incident title must be unique.");
        }
        return incidentRepository.save(incident);
    }

    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found"));
    }



    public Incident updateIncident(Long id, Incident incidentDetails) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found"));
        validateIncident(incidentDetails);

        incident.setDescription(incidentDetails.getDescription());
        incident.setSeverity(incidentDetails.getSeverity());
        incident.setIncidentDate(incidentDetails.getIncidentDate());
        incident.setStatus(incidentDetails.getStatus());
        incident.setNotes(incidentDetails.getNotes());

        return incidentRepository.save(incident);
    }
    public void validateIncident(Incident incident)
    {
        if (incident.getTitle().length() < 10) {
            throw new IllegalArgumentException("Incident title must be at least 10 characters long.");
        }
        if (!List.of("Low", "Medium", "High").contains(incident.getSeverity())) {
            throw new IllegalArgumentException("Invalid severity level. It must be one of: Low, Medium, High.");
        }
        LocalDate today = LocalDate.now();
        if (incident.getIncidentDate().isAfter(today)) {
            throw new IllegalArgumentException("Incident date cannot be in the future.");
        }
        if (incident.getIncidentDate().isBefore(today.minusDays(30))) {
            throw new IllegalArgumentException("Incident date cannot be older than 30 days.");
        }
    }
    private void validateSeverity(String severity) {
        if (!List.of("Low", "Medium", "High").contains(severity)) {
            throw new IllegalArgumentException("Invalid severity level: " + severity + ". Allowed values are 'Low', 'Medium', or 'High'.");
        }
    }

}
