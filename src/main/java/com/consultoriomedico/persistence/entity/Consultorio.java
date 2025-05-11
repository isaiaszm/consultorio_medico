package com.consultoriomedico.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "consultorios")
@Getter
@Setter
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int noConsultorio;
    private int piso;

    @OneToMany(mappedBy = "consultorio", cascade = CascadeType.ALL)
    private List<Cita> citas;

    public int getNoConsultorio() {
        return noConsultorio;
    }

    public void setNoConsultorio(int noConsultorio) {
        this.noConsultorio = noConsultorio;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
