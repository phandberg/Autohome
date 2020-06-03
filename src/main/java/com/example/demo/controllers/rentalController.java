package com.example.demo.controllers;

import com.example.demo.models.Customers;
import com.example.demo.models.Motorhomes;
import com.example.demo.models.Rentals;
import com.example.demo.repositories.RentalRepository;
import com.example.demo.util.dateConverter;
import com.example.demo.util.sqlDateConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class rentalController {
    private RentalRepository rentalRepository;
    dateConverter dateconverter = new dateConverter();
    public rentalController(){rentalRepository = new RentalRepository();}


@GetMapping("/")
    public String index(Model model){

return "rentals/index";

}


    @GetMapping("/rentaloverview")
    public String rentalOverview(Model model){
    model.addAttribute("rentals", rentalRepository.listRentals());
    return "rentals/rentalOverview";
    }

    @GetMapping("/carsearch")
    public String carSearch(){
        return "rentals/searchform";
    }

    @PostMapping("/searchresult")
    public String searchResult(int maxSeats, int pricePerDay, Model model, java.sql.Date pickupDate, java.sql.Date endDate){
    rentalRepository.searchForRental(maxSeats, pricePerDay, pickupDate, endDate);

    model.addAttribute("results", rentalRepository.searchForRental(maxSeats, pricePerDay, pickupDate, endDate));
    return "rentals/searchresultpage";
    }

    @GetMapping("/createrental")
    public String createRental(@RequestParam int motorhomeId, Model model){
    Motorhomes motorhomeObject =rentalRepository.readMotorhome(motorhomeId);
    model.addAttribute("motorhome", motorhomeObject);

        return "rentals/createrental";
    }

    @PostMapping("/createdrental")
    public String createdRental(@ModelAttribute Rentals rentalFromPost, Customers customerFromPost){
        rentalRepository.createRental(rentalFromPost, customerFromPost);
        return"redirect:/rentaloverview";
    }


/*
@GetMapping("/motorhomeoverview")
    public String motorhomeOverview(Model model){
model.addAttribute("motorhomes", rentalRepository.listMotorhomes());

return "rentals/motorhomeOverview";
    }
 */



}
