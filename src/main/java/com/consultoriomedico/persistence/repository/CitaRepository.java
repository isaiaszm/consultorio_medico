package com.consultoriomedico.persistence.repository;

import com.consultoriomedico.persistence.entity.Cita;
import com.consultoriomedico.persistence.entity.Consultorio;
import com.consultoriomedico.persistence.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByNombrePacienteAndHoraBetween(String nombrePaciente, LocalDateTime horaAfter, LocalDateTime horaBefore);

    List<Cita> findByDoctorAndHora(Doctor doctor, LocalDateTime hora);

    List<Cita> findByConsultorioAndHora(Consultorio consultorio, LocalDateTime hora);

    long countByDoctorAndHoraBetween(Doctor doctor, LocalDateTime horaAfter, LocalDateTime horaBefore);
}
