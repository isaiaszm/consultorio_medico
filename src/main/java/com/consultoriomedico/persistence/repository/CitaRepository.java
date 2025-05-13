package com.consultoriomedico.persistence.repository;

import com.consultoriomedico.persistence.entity.Cita;
import com.consultoriomedico.persistence.entity.Consultorio;
import com.consultoriomedico.persistence.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Query("SELECT c FROM Cita c WHERE " +
            "(:fecha IS NULL OR  CAST(c.hora AS DATE) = :fecha) AND " +
            "(:consultorioId IS NULL OR c.consultorio.id = :consultorioId) AND " +
            "(:doctorId IS NULL OR c.doctor.id = :doctorId)")
    List<Cita> listarCitas(LocalDate fecha, Long consultorioId, Long doctorId);

    List<Cita> findByNombrePacienteAndHoraBetween(String nombrePaciente, LocalDateTime horaAfter, LocalDateTime horaBefore);

    List<Cita> findByDoctorAndHora(Doctor doctor, LocalDateTime hora);

    List<Cita> findByConsultorioAndHora(Consultorio consultorio, LocalDateTime hora);

    long countByDoctorAndHoraBetween(Doctor doctor, LocalDateTime horaAfter, LocalDateTime horaBefore);
}
