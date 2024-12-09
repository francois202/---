package com.medsystem.service;

import java.util.List;

import com.medsystem.model.Patient;
import com.medsystem.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {

    private PatientRepository repo;

    public List<Patient> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public void save(Patient patient) {
        repo.save(patient);
    }

    public Patient get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
