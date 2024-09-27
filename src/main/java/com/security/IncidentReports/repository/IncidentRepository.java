package com.security.IncidentReports.repository;


import com.security.IncidentReports.Model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findBySeverity(String severity);
    Optional<Incident> findByTitle(String title);
}
