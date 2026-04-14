package hh.backend.dryingroombookingsystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.backend.dryingroombookingsystem.domain.ApartmentRepository;
import hh.backend.dryingroombookingsystem.domain.Resident;
import hh.backend.dryingroombookingsystem.domain.ResidentRepository;
import jakarta.validation.Valid;

@Controller
public class ResidentController {

    private final ResidentRepository residentRepository;
    private final ApartmentRepository apartmentRepository;

    public ResidentController(ResidentRepository residentRepository, ApartmentRepository apartmentRepository) {
        this.residentRepository = residentRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @GetMapping("/resident/list")
    public String listResidents(Model model) {
        model.addAttribute("residents", residentRepository.findAll());
        return "residentlist";
    }

    @GetMapping("/resident/view/{id}")
    public String viewResident(@PathVariable Long id, Model model) {
        residentRepository.findById(id).ifPresent(r -> model.addAttribute("resident", r));
        return "resident";
    }

    @GetMapping("/resident/add")
    public String addResident(Model model) {
        model.addAttribute("resident", new Resident());
        model.addAttribute("apartments", apartmentRepository.findAll());
        return "addresident";
    }

    @GetMapping("/resident/edit/{id}")
    public String editResident(@PathVariable Long id, Model model) {
        residentRepository.findById(id).ifPresent(r -> model.addAttribute("resident", r));
        model.addAttribute("apartments", apartmentRepository.findAll());
        return "editresident";
    }

    @PostMapping("/resident/save")
    public String saveResident(@Valid @ModelAttribute Resident resident, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("apartments", apartmentRepository.findAll());
            return resident.getId() == null ? "addresident" : "editresident";
        }
        residentRepository.save(resident);
        return "redirect:/resident/list";
    }
}
