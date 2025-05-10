package com.consultoriomedico.presentation.controller;

import com.consultoriomedico.persistence.entity.Cita;
import com.consultoriomedico.persistence.repository.ConsultorioRespository;
import com.consultoriomedico.persistence.repository.DoctorRepository;
import com.consultoriomedico.service.exception.APIException;
import com.consultoriomedico.service.interfaces.CitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//@RequiredArgsConstructor
public class CitaViewController {

    private final CitaService citaService;
    private final DoctorRepository doctorRepository;
    private final ConsultorioRespository consultorioRespository;

    public CitaViewController(CitaService citaService, DoctorRepository doctorRepository, ConsultorioRespository consultorioRespository) {
        this.citaService = citaService;
        this.doctorRepository = doctorRepository;
        this.consultorioRespository = consultorioRespository;
    }

    @GetMapping("/")
    public String mostrarFormularioCreacion(Model model) {
        Cita cita = new Cita();
        model.addAttribute("cita", cita);
        model.addAttribute("doctores", doctorRepository.findAll());
        model.addAttribute("consultorios", consultorioRespository.findAll());
        return "index";
    }

    @PostMapping("/crearCita")
    public String crearCita(@ModelAttribute("cita") Cita citaCreada, Model model, RedirectAttributes redirectAttributes) {

        try {
            citaService.crearCita(citaCreada);
            redirectAttributes.addFlashAttribute("success", "Cita creada exitosamente!");
            return "redirect:/";
        } catch (APIException e) {
            model.addAttribute("doctores", doctorRepository.findAll());
            model.addAttribute("consultorios", consultorioRespository.findAll());
            System.out.println(e.getMessage());

            model.addAttribute("error", e.getMessage());
            return "index";
        }



    }
}