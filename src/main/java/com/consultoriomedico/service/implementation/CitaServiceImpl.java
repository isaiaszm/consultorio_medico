package com.consultoriomedico.service.implementation;

import com.consultoriomedico.persistence.entity.Cita;
import com.consultoriomedico.persistence.entity.Consultorio;
import com.consultoriomedico.persistence.entity.Doctor;
import com.consultoriomedico.persistence.repository.CitaRepository;

import com.consultoriomedico.persistence.repository.ConsultorioRespository;
import com.consultoriomedico.persistence.repository.DoctorRepository;
import com.consultoriomedico.service.exception.APIException;
import com.consultoriomedico.service.interfaces.CitaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {
    private final CitaRepository citaRepository;
    private final DoctorRepository doctorRepository;
    private final ConsultorioRespository consultorioRespository;


    public CitaServiceImpl(CitaRepository citaRepository, DoctorRepository doctorRepository, ConsultorioRespository consultorioRespository) {
        this.citaRepository = citaRepository;
        this.doctorRepository = doctorRepository;
        this.consultorioRespository = consultorioRespository;
    }

    private static final int MAX_CITAS_POR_DIA = 8;
    private static final int MIN_HORAS_ENTRE_CITAS = 2;


    @Override
    public List<Cita> listarCitas(LocalDate fecha, Long consultorio, Long doctor) {

        if (fecha != null || consultorio != null || doctor != null) {
            return citaRepository.listarCitas(fecha,consultorio,doctor);

        }

        return citaRepository.findAll();
    }

    @Override
    public void eliminarCita(Long id) {

        citaRepository.deleteById(id);
    }

    @Override
    public Cita editarCita(Cita cita) {


            disponibilidadConsultorio(cita);
            disponibilidadPaciente(cita);
            disponibilidadDoctor(cita);

        return citaRepository.save(cita);
    }

    @Override
    public Cita buscarCitaPorId(Long id) {

        return citaRepository.findById(id).orElse(null);
    }

    @Override
    public Cita crearCita(Cita cita) {



        disponibilidadConsultorio(cita);
        disponibilidadPaciente(cita);
        disponibilidadDoctor(cita);


        return citaRepository.save(cita) ;
    }



    private void disponibilidadDoctor(Cita cita) {


        Doctor doctor = doctorRepository.findById(cita.getDoctor().getId());
        LocalDateTime horaCita = cita.getHora();


        long citasDelDia = citaRepository.countByDoctorAndHoraBetween(
                doctor,
                horaCita.toLocalDate().atStartOfDay(),
                horaCita.toLocalDate().atTime(23, 59, 59));

        if (citasDelDia >= MAX_CITAS_POR_DIA){
            throw new APIException(
                    String.format("El Dr. %s %s ya tiene %d citas programadas para este día (máximo %d permitidas)",
                            doctor.getNombre(),
                            doctor.getApellidoPaterno(),
                            citasDelDia,
                            MAX_CITAS_POR_DIA));
        }
        List<Cita> citasDisponibles = citaRepository
                .findByDoctorAndHora(cita.getDoctor(), cita.getHora());

        if (!citasDisponibles.isEmpty()) {
            throw new APIException(
                    String.format("El Dr. %s %s ya tiene una cita programada para el %s a las %s",
                            doctor.getNombre(),
                            doctor.getApellidoPaterno(),
                            cita.getHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            cita.getHora().format(DateTimeFormatter.ofPattern("HH:mm"))
                    ));
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
                throw new APIException(
                        String.format("El paciente %s ya tiene una cita programada para el %s a las %s",
                                nombrePaciente,
                                horaExistente.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                horaExistente.format(DateTimeFormatter.ofPattern("HH:mm"))
                                ));
            }

            long horasDiferencia = Math.abs(horaCita.until(horaExistente, ChronoUnit.HOURS));
            if (horasDiferencia < MIN_HORAS_ENTRE_CITAS) {
                throw new APIException(
                        String.format("El paciente %s necesita %d horas entre citas. Cita existente para el %s a las %s",
                                nombrePaciente, MIN_HORAS_ENTRE_CITAS,
                                horaExistente.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                horaExistente.format(DateTimeFormatter.ofPattern("HH:mm"))
                        ));
            }
        });
    }
    private void disponibilidadConsultorio(Cita cita) {
        List<Cita> citasMismoHorario = citaRepository
                .findByConsultorioAndHora(cita.getConsultorio(), cita.getHora());

        if (!citasMismoHorario.isEmpty()) {
            Consultorio consultorio = consultorioRespository.findById(cita.getConsultorio().getId());
            throw new APIException(
                    String.format("El consultorio %d ya tiene una cita programada para el %s a las %s",
                            consultorio.getNoConsultorio(),
                            cita.getHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            cita.getHora().format(DateTimeFormatter.ofPattern("HH:mm"))));
        }
    }
}
