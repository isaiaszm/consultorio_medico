package com.consultoriomedico.presentation.controller;

import com.consultoriomedico.persistence.repository.ConsultorioRespository;
import com.consultoriomedico.persistence.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citas")
//@RequiredArgsConstructor
public class CitaViewController {

    private final DoctorRepository doctorRepository;
    private final ConsultorioRespository consultorioRespository;

    public CitaViewController(DoctorRepository doctorRepository, ConsultorioRespository consultorioRespository) {
        this.doctorRepository = doctorRepository;
        this.consultorioRespository = consultorioRespository;
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("doctores", doctorRepository.findAll());
        model.addAttribute("consultorios", consultorioRespository.findAll());
        return "registro";
    }
}