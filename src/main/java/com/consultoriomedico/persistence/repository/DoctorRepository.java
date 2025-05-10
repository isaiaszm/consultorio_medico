package com.consultoriomedico.persistence.repository;

import com.consultoriomedico.persistence.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findById(long id);
}
