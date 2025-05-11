package com.consultoriomedico.service.interfaces;

import com.consultoriomedico.persistence.entity.Cita;
import com.consultoriomedico.persistence.entity.Doctor;

import java.util.List;

public interface CitaService {


    Cita crearCita(Cita cita);

    List<Cita> listarCitas();

    void eliminarCita(Long id);

    Cita editarCita(Cita cita);

    Cita buscarCitaPorId(Long id);
}
