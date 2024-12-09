package com.medsystem.repository;

import java.util.List;

import com.medsystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE CONCAT(p.firstName, '', p.lastName, '', p.info) LIKE %?1%")
    List<Patient> search(String keyword);
}
