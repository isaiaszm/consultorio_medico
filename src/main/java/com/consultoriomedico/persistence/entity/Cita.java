package com.consultoriomedico.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "citas")
@Getter
@Setter
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultorio_id", nullable = false)
    private Consultorio consultorio;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private LocalDateTime hora;

    private String nombrePaciente;

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return Objects.equals(id, cita.id) && Objects.equals(consultorio, cita.consultorio) && Objects.equals(doctor, cita.doctor) && Objects.equals(hora, cita.hora) && Objects.equals(nombrePaciente, cita.nombrePaciente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, consultorio, doctor, hora, nombrePaciente);
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", consultorio=" + consultorio +
                ", doctor=" + doctor +
                ", hora=" + hora +
                ", nombrePaciente='" + nombrePaciente + '\'' +
                '}';
    }
}
