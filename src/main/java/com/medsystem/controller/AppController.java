package com.medsystem.controller;

import java.util.List;

import com.medsystem.model.Patient;
import com.medsystem.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@AllArgsConstructor
public class AppController {

    private final PatientService service;

    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Patient> listPatients = service.listAll(keyword);
        model.addAttribute("listPatients", listPatients);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewPatientForm(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "new_patient";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savePatient(@ModelAttribute("patient") @Valid Patient patient,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "new_patient";

        service.save(patient);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public String showEditPatientForm(@PathVariable(name="id") Long id, Model model) {
        Patient patient = service.get(id);
        model.addAttribute("patient", patient);
        return "edit_patient";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updatePatient(@ModelAttribute("patient") @Valid Patient patient,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_patient";
        }

        service.save(patient);
        return "redirect:/";
    }

    @RequestMapping("/delete/{id}")
    public String deletePatient(@PathVariable(name="id") Long id) {
        service.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
