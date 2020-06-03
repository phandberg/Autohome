package com.example.demo.controllers;


import com.example.demo.models.Employees;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.PreparedStatement;

@Controller
public class employeeController {

private EmployeeRepository employeeRepository;
public employeeController(){employeeRepository = new EmployeeRepository();}

@GetMapping("/employees")
    public String employees(Model model){
    model.addAttribute("employeelist", employeeRepository.listEmployees());


    return "employees/employees";
}

@GetMapping("/createemployee")
    public String createemployee(){
    return "employees/createemployees";
}

@PostMapping("/createdemployee")
    public String createdemployee(@ModelAttribute Employees employeeFromPost){
    employeeRepository.createEmployee(employeeFromPost);
    return"redirect:/employees";
}

@GetMapping("/deleteemployee")
public String deleteemployee(@RequestParam int employeeId){
employeeRepository.delete(employeeId);
return "redirect:/employees";
}

    @GetMapping("/updateemployee")
    public String updateemployee(@RequestParam int employeeId, Model model) {
        Employees emp = employeeRepository.read(employeeId);
        model.addAttribute("ansat", emp);
        return "employees/updateemployee";
    }

    @PostMapping("/updatedemployee")
    public String updatedemployee(@ModelAttribute Employees employeeFromPost) {
        employeeRepository.update(employeeFromPost);
        return "redirect:/employees";
    }

    @GetMapping("/employee")
    public String getEmployeeByParameter(@RequestParam int employeeId, Model model) {
        Employees emp = employeeRepository.read(employeeId);
        model.addAttribute("ansat", emp);
        return "employees/singleemployee";
    }


}
