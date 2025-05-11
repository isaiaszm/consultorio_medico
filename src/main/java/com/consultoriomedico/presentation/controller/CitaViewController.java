package com.consultoriomedico.presentation.controller;

import com.consultoriomedico.persistence.entity.Cita;
import com.consultoriomedico.persistence.repository.ConsultorioRespository;
import com.consultoriomedico.persistence.repository.DoctorRepository;
import com.consultoriomedico.service.exception.APIException;
import com.consultoriomedico.service.interfaces.CitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    public String listarCitas(Model model) {
        model.addAttribute("citas", citaService.listarCitas());
        return "index";
    }

    @GetMapping("/cita/nueva")
    public String mostrarFormularioCreacion(Model model) {
        Cita cita = new Cita();
        model.addAttribute("cita", cita);
        model.addAttribute("doctores", doctorRepository.findAll());
        model.addAttribute("consultorios", consultorioRespository.findAll());
        return "registro_cita";
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

            model.addAttribute("error", e.getMessage());
            return "registro_cita";
        }


    }

    @PostMapping(value = "/guardar")
    public String guardarCita(@ModelAttribute("cita") Cita citaActualizada, Model model,
                              RedirectAttributes redirectAttributes) {

        try {
            citaService.editarCita(citaActualizada);

            redirectAttributes.addFlashAttribute("success", "Cita guardada exitosamente!");
            return "redirect:/";
        } catch (Exception e) {

            model.addAttribute("doctores", doctorRepository.findAll());
            model.addAttribute("consultorios", consultorioRespository.findAll());
            model.addAttribute("cita", citaActualizada);

            model.addAttribute("error", e.getMessage());
            return "editar_cita";

        }


    }

    @RequestMapping("/cita/editar/{id}")
    public ModelAndView editarCita(@PathVariable(name = "id") Long id, Model model) {
        ModelAndView modelo = new ModelAndView("editar_cita");

        Cita cita = citaService.buscarCitaPorId(id);
        model.addAttribute("doctores", doctorRepository.findAll());
        model.addAttribute("consultorios", consultorioRespository.findAll());
        modelo.addObject("cita", cita);

        return modelo;

    }

    @RequestMapping("/cita/eliminar/{id}")
    public String eliminarCita(@PathVariable(name = "id") Long id) {

        citaService.eliminarCita(id);

        return "redirect:/";

    }
}