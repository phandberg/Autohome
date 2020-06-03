package com.example.demo.controllers;


import com.example.demo.models.Accessories;
import com.example.demo.models.RentalPayment;
import com.example.demo.models.Rentals;
import com.example.demo.repositories.PaymentRepository;
import com.example.demo.repositories.RentalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class paymentController {

    private PaymentRepository paymentRepository;

    public paymentController() {
        paymentRepository = new PaymentRepository();
    }

    private RentalRepository rentalRepository;


    @GetMapping("/individualpriceoverview")
    public String getRentalPaymentByParameter(@RequestParam int rentalId, Model model) {
        RentalPayment tempRentalPayment = paymentRepository.read(rentalId);
        model.addAttribute("payment", tempRentalPayment);

        return "payment/individualPriceOverview";


    }

    @GetMapping("/kilometerprice")
    public String kilometerPrice(@RequestParam int rentalId, Model model) {
        model.addAttribute("rentalid", rentalId);
        return "payment/kilometerprice";
    }

    @PostMapping("/kilometerpricecalculated")
    public String kilometersCalculated(@ModelAttribute RentalPayment paymentFromPost, @RequestParam int rentalId) throws SQLException {
        paymentRepository.calculateKilometerprice(paymentFromPost);
        // paymentRepository.calculateDropoffPrice(paymentFromPost);
        return "redirect:/rentaloverview";
    }

    @GetMapping("/dropoffprice")
    public String dropoffPriceCalculator(@RequestParam int rentalId, Model model) {
        model.addAttribute("rentalid", rentalId);
        return "/payment/dropoffprice";
    }

    @PostMapping("/dropoffcalculated")
    public String dropoffCalculated(@ModelAttribute RentalPayment paymentFromPost, @RequestParam int rentalId) {
        paymentRepository.dropoffPrice(paymentFromPost);
        return "redirect:/rentaloverview";
    }

    @GetMapping("/fuelprice")
    public String fuelPrice(@RequestParam int rentalId, Model model) {
        model.addAttribute("rentalid", rentalId);
        return "payment/fuelprice";
    }

    @PostMapping("/fuelpricecalculated")
    public String fuelPriceCalculated(@ModelAttribute RentalPayment paymentFromPost, @RequestParam int rentalId) {
        paymentRepository.fuelprice(paymentFromPost, rentalId);
        return "redirect:/rentaloverview";

    }

    @GetMapping("/cancelrental")
    public String cancelRental(@RequestParam int rentalId, Model model) {
//       Rentals rental = rentalRepository.readRentals(rentalId);
        // model.addAttribute("pickupdate", rental);
        model.addAttribute("rentalid", rentalId);
        return "payment/cancellation";
    }

    @PostMapping("/cancelled")
    public String cancelled(@RequestParam int rentalId, Rentals rentals) throws SQLException {
        paymentRepository.cancellationprice(rentals, rentalId);
        return "redirect:/rentaloverview";
    }

    @GetMapping("/accessoriesprice")
    public String accessoriesPrice(@RequestParam int rentalId, Model model) {
        model.addAttribute("rentalid", rentalId);

        return "payment/accessoriesprice";
    }

    @PostMapping("/accessoriespricecalculated")
    public String accessoriescalculated(@ModelAttribute Accessories accessoriesfromPost) {
        paymentRepository.Accessoriescalculated(accessoriesfromPost);
        // paymentRepository.calculateDropoffPrice(paymentFromPost);
        return "redirect:/rentaloverview";

    }

    @GetMapping("/updatepayment")
    public String updatePayment(@RequestParam int rentalId, Model model) {
        RentalPayment payment = paymentRepository.read(rentalId);
        model.addAttribute("payment", payment);
        return "payment/updatePayment";
    }

    @PostMapping("/updatedrentalpayment")
    public String updatedPayment(@ModelAttribute RentalPayment rentalPaymentfromPost) {
        paymentRepository.updateRentalPayment(rentalPaymentfromPost);
        return "redirect:/rentaloverview";
    }

    @GetMapping("/deleterentalandpayment")
    public String deleteRentalAndPayment(@RequestParam int rentalId) {
        paymentRepository.deleteRentalAndPayment(rentalId);
        return "redirect:/rentaloverview";

    }

}

