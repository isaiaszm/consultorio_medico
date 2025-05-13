package com.consultoriomedico.service.interfaces;

import com.consultoriomedico.persistence.entity.Cita;
import com.consultoriomedico.persistence.entity.Consultorio;
import com.consultoriomedico.persistence.entity.Doctor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CitaService {


    Cita crearCita(Cita cita);

    List<Cita> listarCitas(LocalDate fecha, Long consultorio, Long doctor);

    void eliminarCita(Long id);

    Cita editarCita(Cita cita);

    Cita buscarCitaPorId(Long id);
}
