package com.consultoriomedico.persistence.repository;

import com.consultoriomedico.persistence.entity.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultorioRespository extends JpaRepository<Consultorio, Integer> {
    Consultorio findById(long id);
}
