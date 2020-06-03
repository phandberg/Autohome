package com.example.demo.controllers;


import com.example.demo.models.Motorhomes;
import com.example.demo.repositories.MotorhomeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class motorhomeController {

    private MotorhomeRepository motorhomeRepository;
    public motorhomeController() {motorhomeRepository = new MotorhomeRepository(); }


    @GetMapping("/motorhomeoverview")
    public String motorhomeOverview(Model model) {
        model.addAttribute("motorhomes", motorhomeRepository.listMotorhomes());

        return "motorhomes/motorhomeOverview";
    }

    @GetMapping("/createmotorhome")
    public String createmotorhome() {
        return "motorhomes/createmotorhome";
    }

    @PostMapping("/createdmotorhome")
    public String createdmlotorhome(@ModelAttribute Motorhomes motorhomeFromPost) {
        motorhomeRepository.createMotorhomeModel(motorhomeFromPost);
        return "motorhomes/createmotorhome";
    }

    @GetMapping("/addtofleet")
    public String addtofleet(){
        return "motorhomes/addtofleet";
    }

    @PostMapping("/addedtofleet")
    public String addedtofleet(@ModelAttribute Motorhomes motorhome){
        motorhomeRepository.addToFleet(motorhome);
        return "redirect:/motorhomeoverview";
    }

    @GetMapping("/deletemotorhome")
    public String deletemotorhome(@RequestParam int motorhomeId) {
        motorhomeRepository.delete(motorhomeId);
        return "redirect:/motorhomeoverview";
    }

    @GetMapping("/updatemotorhome")
    public String updatemotorhome(@RequestParam int motorhomeId, Model model) {
        Motorhomes mot = motorhomeRepository.read(motorhomeId);
        model.addAttribute("motor", mot);
        return "motorhomes/updatemotorhome";
    }

    @PostMapping("/updatedmotorhome")
    public String updatedmotorhome(@ModelAttribute Motorhomes motorhomeFromPost) {
        motorhomeRepository.update(motorhomeFromPost);
        return "redirect:/motorhomeoverview";
    }

    @GetMapping("/motorhome")
    public String getMotorhomeByParameter(@RequestParam int motorhomeId, Model model) {
        Motorhomes mot = motorhomeRepository.read(motorhomeId);
        model.addAttribute("motor", mot);
        return "motorhomes/singlemotorhome";
    }

}