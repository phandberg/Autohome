package com.example.demo.controllers;

import com.example.demo.models.Customers;
import com.example.demo.repositories.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class CustomerController{

    private CustomerRepository customerRepository;

    public CustomerController(){customerRepository = new CustomerRepository();}

    @GetMapping("/customers")
    public String customers(Model model){
        model.addAttribute("customerlist", customerRepository.customersList());
        return "/customers/customers";
    }

    @GetMapping("/createcustomer")
    public String createCustomer(){return "/customers/createcustomer";}

    @PostMapping("/createdcustomer")
    public String createdCustomer(@ModelAttribute Customers customerFromPost){
        customerRepository.createCustomer(customerFromPost);
        return "redirect:/customers";
    }

    @GetMapping("/deletecustomer")
    public String deleteCustomer(@RequestParam int customerId){
        customerRepository.deleteCustomer(customerId);
        return "redirect:/customers";
    }

    @GetMapping("/updatecustomer")
    public String updateCustomer(@RequestParam int customerId, Model model){
        Customers customer = customerRepository.read(customerId);
        model.addAttribute("kunde",customer);
        return "/customers/updatecustomer";
    }

    @PostMapping("/updatedcustomer")
    public String updatedCustomer(@ModelAttribute Customers customerFromPost) {
        customerRepository.updateCustomer(customerFromPost);
        return "redirect:/customers";
    }

    @GetMapping("/customer")
    public String getCustomerByParameter(@RequestParam int customerId, Model model) {
        Customers customer = customerRepository.read(customerId);
        model.addAttribute("kunde", customer);
        return "/customers/singlecustomer";
    }

}


