package com.consultoriomedico.service.implementation;

import com.consultoriomedico.persistence.entity.Cita;
import com.consultoriomedico.persistence.entity.Consultorio;
import com.consultoriomedico.persistence.entity.Doctor;
import com.consultoriomedico.persistence.repository.CitaRepository;

import com.consultoriomedico.service.interfaces.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {
    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    private static final int MAX_CITAS_POR_DIA = 8;
    private static final int MIN_HORAS_ENTRE_CITAS = 2;

    @Override
    public Cita crearCita(Cita cita) {

        disponibilidadConsultorio(cita);
        disponibilidadPaciente(cita);
        disponibilidadDoctor(cita);


        return citaRepository.save(cita) ;
    }

    private void disponibilidadDoctor(Cita cita) {


        Doctor doctor = cita.getDoctor();
        LocalDateTime horaCita = cita.getHora();


        long citasDelDia = citaRepository.countByDoctorAndHoraBetween(
                doctor,
                horaCita.toLocalDate().atStartOfDay(),
                horaCita.toLocalDate().atTime(23, 59, 59));

        if (citasDelDia >= MAX_CITAS_POR_DIA){
            throw new IllegalStateException(
                    String.format("El Dr. %s %s ya tiene %d citas programadas para este día (máximo %d permitidas)",
                            doctor.getNombre(),
                            doctor.getApellidoPaterno(),
                            citasDelDia,
                            MAX_CITAS_POR_DIA));
        }
        List<Cita> citasDisponibles = citaRepository
                .findByDoctorAndHora(cita.getDoctor(), cita.getHora());

        if (!citasDisponibles.isEmpty()) {
            throw new IllegalStateException(
                    String.format("El Dr. %s %s ya tiene una cita programada a las %s",
                            doctor.getNombre(),
                            doctor.getApellidoPaterno(),
                            cita.getHora()));
        }
    }
    private void disponibilidadPaciente(Cita cita) {
        LocalDateTime horaCita = cita.getHora();
        String nombrePaciente = cita.getNombrePaciente();

        List<Cita> citasDelDia = citaRepository
                .findByNombrePacienteAndHoraBetween(
                        nombrePaciente,
                        horaCita.toLocalDate().atStartOfDay(),
                        horaCita.toLocalDate().atTime(23, 59, 59));

        citasDelDia.forEach(citaExistente -> {
            LocalDateTime horaExistente = citaExistente.getHora();

            if (horaCita.equals(horaExistente)) {
                throw new IllegalStateException(
                        String.format("El paciente %s ya tiene una cita programada a las %s",
                                nombrePaciente, horaExistente));
            }

            long horasDiferencia = Math.abs(horaCita.until(horaExistente, ChronoUnit.HOURS));
            if (horasDiferencia < MIN_HORAS_ENTRE_CITAS) {
                throw new IllegalStateException(
                        String.format("El paciente %s necesita %d horas entre citas. Cita existente a las %s",
                                nombrePaciente, MIN_HORAS_ENTRE_CITAS, horaExistente));
            }
        });
    }
    private void disponibilidadConsultorio(Cita cita) {
        List<Cita> citasMismoHorario = citaRepository
                .findByConsultorioAndHora(cita.getConsultorio(), cita.getHora());

        if (!citasMismoHorario.isEmpty()) {
            Consultorio consultorio = cita.getConsultorio();
            throw new IllegalStateException(
                    String.format("El consultorio %d ya tiene una cita programada a las %s",
                            consultorio.getId(),
                            cita.getHora()));
        }
    }
}
